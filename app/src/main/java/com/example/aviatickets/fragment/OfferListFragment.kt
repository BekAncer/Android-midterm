package com.example.aviatickets.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.aviatickets.R
import com.example.aviatickets.adapter.OfferListAdapter
import com.example.aviatickets.databinding.FragmentOfferListBinding
import com.example.aviatickets.model.service.NetworkManager

class OfferListFragment : Fragment() {

    companion object {
        fun newInstance() = OfferListFragment()
    }

    private var _binding: FragmentOfferListBinding? = null
    private val binding
        get() = _binding!!

    private val adapter: OfferListAdapter by lazy {
        OfferListAdapter()
    }

    private var currentSortType: SortType = SortType.NONE

    private enum class SortType {
        NONE,
        BY_PRICE,
        BY_DURATION
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOfferListBinding.inflate(layoutInflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        adapter.setItems(NetworkManager.getCombinedOfferList())
    }

    private fun setupUI() {
        with(binding) {
            offerList.adapter = adapter

            sortRadioGroup.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.sort_by_price -> {
                        sortListByPrice()
                    }

                    R.id.sort_by_duration -> {
                        sortListByDuration()
                    }
                }
            }
        }
    }

    private fun sortListByPrice() {
        currentSortType = SortType.BY_PRICE
        val sortedList = NetworkManager.getCombinedOfferList().sortedBy { it.price }
        adapter.submitList(sortedList)
    }

    private fun sortListByDuration() {
        currentSortType = SortType.BY_DURATION
        val sortedList = NetworkManager.getCombinedOfferList().sortedBy { it.flight.duration }
        adapter.submitList(sortedList)
    }

}
