package com.reece.goodbois.ui

import android.app.Activity
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.reece.goodbois.R
import com.reece.goodbois.adpater.SearchAdapter
import com.reece.goodbois.databinding.FragmentDoggieSearchBinding
import com.reece.goodbois.util.Constants.BREED_ID
import com.reece.goodbois.util.isNetworkAvailable
import com.reece.goodbois.viewmodel.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

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

        binding.textInputSearch.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(
                textView: TextView?, actionId: Int, event: KeyEvent?
            ): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    textView?.hideKeyboard()
                    loadDoggieFeed(textView?.text.toString())
                    return true
                }
                return false
            }
        })

        baseViewModel.allBreeds.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = View.GONE
            searchAdapter.updateList(it)
        }

        loadDoggieFeed()
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

    private fun loadDoggieFeed(keyword: String = "") {
        binding.progressBar.visibility = View.VISIBLE
        searchAdapter.updateList(listOf())
        if (isNetworkAvailable(requireContext())) {
            CoroutineScope(IO).launch {
                if (keyword.isEmpty())
                    baseViewModel.getAllBreeds()
                else
                    baseViewModel.getBreedByName(keyword)
            }
        } else {
            Toast.makeText(requireContext(), getText(R.string.no_internet), Toast.LENGTH_LONG)
                .show()
        }
    }


    private fun View.hideKeyboard() {
        val inputMethodManager =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(this.windowToken, 0)
    }
}