package com.example.spotlight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Intent
import android.net.Uri
import com.example.spotlight.databinding.BottomSheetPlaceDetailBinding
import com.example.spotlight.model.Place
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class PlaceDetailBottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomSheetPlaceDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetPlaceDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val place = arguments?.getParcelable<Place>(ARG_PLACE) ?: return

        binding.imgPlace.setImageResource(place.imageRes)
        binding.tvName.text = place.name
        binding.tvCategory.text = place.category.name
        binding.tvLocation.text = place.location
        binding.tvRating.text = "⭐ ${place.rating}"

        binding.btnMaps.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(place.mapUrl))
            startActivity(intent)
        }

        binding.btnFavorite.setOnClickListener {
            Snackbar.make(view, "Ditambahkan ke favorit", Snackbar.LENGTH_SHORT).show()
        }

        binding.btnDelete.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Konfirmasi")
                .setMessage("Yakin mau hapus tempat ini?")
                .setPositiveButton("Ya") { dialog, _ ->
                    dismiss()
                }
                .setNegativeButton("Tidak") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_PLACE = "arg_place"

        fun newInstance(place: Place): PlaceDetailBottomSheet {
            return PlaceDetailBottomSheet().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PLACE, place)
                }
            }
        }
    }
}
