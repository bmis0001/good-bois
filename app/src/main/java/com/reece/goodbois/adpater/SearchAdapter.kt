package com.reece.goodbois.adpater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.reece.goodbois.R
import com.reece.goodbois.databinding.ItemBreedBinding
import com.reece.goodbois.model.Breed
import com.squareup.picasso.Picasso

class SearchAdapter(private val onItemClick: (breed: Breed) -> Unit) :
    RecyclerView.Adapter<SearchAdapter.BreedViewHolder>() {

    private val dogBreeds = mutableListOf<Breed>()

    internal fun updateList(allBreeds: List<Breed>) {
        dogBreeds.clear()
        dogBreeds.addAll(allBreeds)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BreedViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemBreedBinding.inflate(inflater, viewGroup, false)
        return BreedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BreedViewHolder, position: Int) {
        holder.bind(dogBreeds[position])
    }

    override fun getItemCount(): Int = dogBreeds.size

    inner class BreedViewHolder(private val binding: ItemBreedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        internal fun bind(breed: Breed) {
            breed.image?.let {
                Picasso.get().load(it.url).into(binding.breedImage)
            } ?: run {
                Picasso.get().load(breed.getImageUrl()).error(R.drawable.ic_broken_image)
                    .into(binding.breedImage)
            }

            binding.breedName.text = breed.name
            binding.breedCard.setOnClickListener { onItemClick(breed) }
        }
    }
}