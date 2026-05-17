package com.example.spotlight.fragment

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spotlight.PlaceDetailBottomSheet
import com.example.spotlight.R
import com.example.spotlight.adapter.PlaceAdapter
import com.example.spotlight.databinding.FragmentHomeBinding
import com.example.spotlight.datasource.DataSource
import com.example.spotlight.model.PlaceCategory
import com.google.android.material.chip.Chip

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PlaceAdapter

    private var currentCategory: String? = null
    private var currentSort: String = "rating_desc"
    private var currentQuery: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupChips()
        setupSearch()
        setupSort()

        loadData()
    }

    private fun setupChips() {
        val categories = listOf("Semua" to null) +
            PlaceCategory.entries.map { it.name to it.name }

        val primary = ContextCompat.getColor(requireContext(), R.color.purple_500)
        val white = ContextCompat.getColor(requireContext(), R.color.white)
        val secondaryText = ContextCompat.getColor(requireContext(), R.color.text_secondary)

        val chipBgColor = ColorStateList(
            arrayOf(intArrayOf(android.R.attr.state_checked), intArrayOf()),
            intArrayOf(primary, white)
        )
        val chipTextColor = ColorStateList(
            arrayOf(intArrayOf(android.R.attr.state_checked), intArrayOf()),
            intArrayOf(white, secondaryText)
        )

        categories.forEach { (label, category) ->
            val chip = Chip(requireContext()).apply {
                text = label
                isClickable = true
                isCheckable = true
                chipBackgroundColor = chipBgColor
                setTextColor(chipTextColor)
                setOnClickListener {
                    currentCategory = category
                    loadData()
                }
            }
            binding.chipGroupCategory.addView(chip)
        }

        binding.chipGroupCategory.getChildAt(0)?.performClick()
    }

    private fun setupRecyclerView() {
        adapter = PlaceAdapter(emptyList()) { place ->
            PlaceDetailBottomSheet.newInstance(place)
                .show(childFragmentManager, "place_detail")
        }
        binding.rvPlaces.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPlaces.adapter = adapter
    }

    private fun setupSearch() {
        binding.searchEditText.addTextChangedListener { text ->
            currentQuery = text?.toString() ?: ""
            loadData()
        }
    }

    private fun setupSort() {
        binding.btnSort.setOnClickListener { view ->
            PopupMenu(requireContext(), view).apply {
                menu.add(0, 0, 0, "Rating Tertinggi")
                menu.add(0, 1, 1, "Rating Terendah")
                menu.add(0, 2, 2, "Nama A-Z")
                menu.add(0, 3, 3, "Nama Z-A")
                setOnMenuItemClickListener { item ->
                    val (sortBy, _) = when (item.itemId) {
                        0 -> "rating_desc" to "Rating ▾"
                        1 -> "rating_asc" to "Rating ▴"
                        2 -> "name_asc" to "Nama A-Z"
                        3 -> "name_desc" to "Nama Z-A"
                        else -> "rating_desc" to "Rating ▾"
                    }
                    currentSort = sortBy
                    loadData()
                    true
                }
                show()
            }
        }
    }

    private fun loadData() {
        val places = DataSource.getPlaces(currentCategory, currentSort)
        val filtered = if (currentQuery.isBlank()) places
        else places.filter { it.name.contains(currentQuery, ignoreCase = true) }
        adapter.updateData(filtered)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
