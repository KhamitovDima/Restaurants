package com.h.business.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.h.business.R
import com.h.business.adapters.RecyclerAdapter
import com.h.business.data.Rest
import kotlinx.android.synthetic.main.fragment_list.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView

private const val ARG_PARAM1 = "longitude"
private const val ARG_PARAM2 = "latitude"

class ListFragment() : Fragment(R.layout.fragment_list) {
    private val TAG = "ListFragment"
    private lateinit var adapter: RecyclerAdapter
    private lateinit var rests: List<Rest>
    private var latitude = 0.0
    private var longitude = 0.0
    private var offset = 0

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            latitude = it.getDouble(ARG_PARAM1)
            longitude = it.getDouble(ARG_PARAM2)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "onViewCreated")

        recycler_view.layoutManager = LinearLayoutManager(context)
        adapter = RecyclerAdapter()
        recycler_view.adapter = adapter
        observeData()
        viewModel.getB(latitude, longitude)

        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recycler_view.canScrollVertically(1)) {
                    Log.d(TAG, "onScrollStateChanged")
                    viewModel.getB(latitude, longitude)
                }
            }
        })


    }


    fun observeData() {
        viewModel.getListRest().observe(viewLifecycleOwner, Observer {
            if (it!= null) {
                rests = it
                Log.d(TAG, "$it")
                adapter.setRestsInAdapter(rests)
            }
        })
    }




    companion object {

        fun newInstance(longitude: Double, latitude: Double) =
            ListFragment().apply {
                arguments = Bundle().apply {
                    putDouble(ARG_PARAM1, longitude)
                    putDouble(ARG_PARAM2, latitude)

                }
            }
    }
}