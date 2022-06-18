package com.reece.goodbois.ui

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.reece.goodbois.R
import com.reece.goodbois.adpater.SearchAdapter
import com.reece.goodbois.databinding.FragmentDoggieSearchBinding
import com.reece.goodbois.paging.SearchPagingAdapter
import com.reece.goodbois.util.Constants.BREED_ID
import com.reece.goodbois.viewmodel.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class DoggieSearchFragment : Fragment() {

    private lateinit var binding: FragmentDoggieSearchBinding
    private lateinit var searchAdapter: SearchAdapter
    private lateinit var baseViewModel: BaseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentDoggieSearchBinding.inflate(inflater, container, false)
        baseViewModel = ViewModelProvider(this)[BaseViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        binding.searchResultRecycler.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = searchAdapter
            setHasFixedSize(true)
        }

        //Observes the images variable in viewModel which is updated when searchImage() function is invoked
//        baseViewModel.images.observe(viewLifecycleOwner){
//            searchAdapter.submitData(lifecycle, it)
//        }

        binding.textInputSearch.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(
                textView: TextView?, actionId: Int, event: KeyEvent?
            ): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    textView?.hideKeyboard()
                    searchByName(textView?.text.toString())
                    //baseViewModel.searchImages(textView?.text.toString())
                    return true
                }
                return false
            }
        })


        CoroutineScope(IO).launch {
            val response = baseViewModel.getAllBreeds()
            if (response.isSuccessful) {
                response.body()?.let {
                    withContext(Main) {
                        searchAdapter.updateList(it)
                    }
                } ?: kotlin.run {

                }
            } else {
                Log.e("RESPONSE", "EMPTY")
            }
        }
    }

    private fun initAdapter() {
        searchAdapter = SearchAdapter {
            val bundle = Bundle()
            bundle.putInt(BREED_ID, it.id)
            findNavController().navigate(
                R.id.action_doggieSearchFragment_to_doggieDetailFragment, bundle
            )
        }
    }

    private fun searchByName(keyword: String) {
        CoroutineScope(IO).launch {
            val response = baseViewModel.getBreedByName(keyword)
            if (response.isSuccessful) {
                response.body()?.let {
                    withContext(Main) {
                        searchAdapter.updateList(it)
                    }
                }
            }
        }
    }


    private fun View.hideKeyboard() {
        val inputMethodManager =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(this.windowToken, 0)
    }
}