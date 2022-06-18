package com.reece.goodbois.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.reece.goodbois.databinding.ItemBreedBinding
import com.reece.goodbois.model.Breed
import com.squareup.picasso.Picasso

internal class SearchPagingAdapter(private val onItemClick: (breed: Breed) -> Unit) :
    PagingDataAdapter<Breed, SearchPagingAdapter.BreedViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BreedViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemBreedBinding.inflate(inflater, viewGroup, false)
        return BreedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BreedViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class BreedViewHolder(private val binding: ItemBreedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        internal fun bind(breed: Breed?) {
            breed?.let {
                Picasso.get().load(breed.image?.url).into(binding.breedImage)
                binding.breedName.text = breed.name
                binding.breedCard.setOnClickListener { onItemClick(breed) }
            }
        }
    }

    /*
    This class is used with the RecyclerAdapter to check if the items inflated are same or not.
    */
    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Breed>() {
            override fun areItemsTheSame(oldItem: Breed, newItem: Breed): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Breed, newItem: Breed): Boolean {
                return oldItem == newItem
            }

        }
    }
}