package com.example.spotlight.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.spotlight.databinding.ItemPlaceBinding
import com.example.spotlight.databinding.ItemPlaceSkeletonBinding
import com.example.spotlight.model.Place
import com.example.spotlight.utils.FavoriteManager


class PlaceAdapter(
    private var places: List<Place>,
    private val onItemClick: (Place) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var isSkeleton = false
    private var skeletonCount = 0

    companion object {
        private const val TYPE_SKELETON = 0
        private const val TYPE_PLACE = 1
    }

    inner class PlaceViewHolder(private val binding: ItemPlaceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(place: Place) {
            val (name, category, location, rating, imageRes) = place
            binding.tvName.text = name
            binding.tvCategory.text = category.name
            binding.tvLocation.text = location
            binding.tvRating.text = "⭐ $rating"
            binding.imgPlace.setImageResource(imageRes)

            val isFav = FavoriteManager.isFavorite(binding.root.context, place.name)
            binding.imgFavorite.visibility = if (isFav) android.view.View.VISIBLE else android.view.View.GONE

            binding.root.setOnClickListener {
                onItemClick(place)
            }
        }
    }

    inner class SkeletonViewHolder(binding: ItemPlaceSkeletonBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun getItemViewType(position: Int): Int {
        return if (isSkeleton) TYPE_SKELETON else TYPE_PLACE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_SKELETON -> {
                val binding = ItemPlaceSkeletonBinding.inflate(inflater, parent, false)
                SkeletonViewHolder(binding)
            }
            else -> {
                val binding = ItemPlaceBinding.inflate(inflater, parent, false)
                PlaceViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PlaceViewHolder) {
            holder.bind(places[position])
        }
    }

    override fun getItemCount(): Int {
        return if (isSkeleton) skeletonCount else places.size
    }

    fun showSkeleton(count: Int) {
        isSkeleton = true
        skeletonCount = count
        notifyDataSetChanged()
    }

    fun updateData(newPlaces: List<Place>) {
        isSkeleton = false
        places = newPlaces
        notifyDataSetChanged()
    }

    fun currentList(): List<Place> = places
}
