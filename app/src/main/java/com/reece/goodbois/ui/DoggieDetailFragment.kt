package com.reece.goodbois.ui

import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.reece.goodbois.R
import com.reece.goodbois.databinding.FragmentDoggieDetailBinding
import com.reece.goodbois.model.Breed
import com.reece.goodbois.util.Constants.BREED_ID
import com.reece.goodbois.viewmodel.BaseViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class DoggieDetailFragment : Fragment() {

    private lateinit var binding: FragmentDoggieDetailBinding
    private lateinit var baseViewModel: BaseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentDoggieDetailBinding.inflate(inflater, container, false)
        baseViewModel = ViewModelProvider(this)[BaseViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val breedId = arguments?.getInt(BREED_ID, 0)

        CoroutineScope(IO).launch {
            breedId?.let {
                val result = baseViewModel.getBreedDetail(breedId)
                if (result.isSuccessful) {
                    val response = result.body()
                    response?.let {
                        if (it.isNotEmpty()) {
                            val data = it.first()
                            val breed = data.breeds.first()
                            withContext(Main) {
                                Picasso.get().load(data.url).into(binding.breedImage)
                                setBreedDetails(breed)
                            }
                        } else {
                            dataFetchFail()
                        }
                    } ?: run { dataFetchFail() }
                } else {
                    dataFetchFail()
                }
            } ?: run { dataFetchFail() }
        }
    }

    private fun setBreedDetails(breed: Breed) {
        binding.breedName.text = breed.name

        val ageSpan = SpannableString(getString(R.string.dog_age, breed.lifeSpan))
        ageSpan.setSpan(StyleSpan(Typeface.BOLD), 0, 4, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
        binding.breedAge.text = ageSpan

        if (!breed.origin.isNullOrBlank()) {
            val originSpan = SpannableString(getString(R.string.dog_origin, breed.origin))
            originSpan.setSpan(StyleSpan(Typeface.BOLD), 0, 7, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
            binding.breedOrigin.text = originSpan
        } else { binding.breedOrigin.visibility = View.GONE }

        if (!breed.temperament.isNullOrBlank()) {
            val tempSpan = SpannableString(getString(R.string.dog_temperament, breed.temperament))
            tempSpan.setSpan(StyleSpan(Typeface.BOLD), 0, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            binding.breedTemperament.text = tempSpan
        } else { binding.breedTemperament.visibility = View.GONE }

        if (!breed.bredFor.isNullOrBlank()) {
            val bredForSpan = SpannableString(getString(R.string.dog_bred_for, breed.bredFor))
            bredForSpan.setSpan(StyleSpan(Typeface.BOLD), 0, 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            binding.breedBredFor.text = bredForSpan
        } else { binding.breedBredFor.visibility = View.GONE }
    }

    private fun dataFetchFail() {
        CoroutineScope(Main).launch {
            Picasso.get().load(R.drawable.ic_broken_image)
                .error(R.drawable.ic_broken_image).into(binding.breedImage)
            binding.breedName.text = getString(R.string.no_data_available)
        }
    }
}