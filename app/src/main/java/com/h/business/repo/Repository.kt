package com.h.business.repo

import GetBQuery
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.h.business.data.Rest
import com.h.business.network.NetworkService

class Repository {

    private val TAG = "Repository"
    private val rests: MutableLiveData<List<Rest>> = MutableLiveData()
    private val list: ArrayList<Rest> = mutableListOf<Rest>() as ArrayList<Rest>
    private var isExhausted = false


    fun isExhausted(): Boolean {
        return isExhausted
    }


    fun getRests(): MutableLiveData<List<Rest>> {
        return rests
    }

    fun getB(latitude: Double, longitude: Double, offset: Int) {
        Log.d(TAG, "getB")
        val token = "yYSQaxs0B2IDJlJ10OTVJwIBoTCPtIrElar0KOMxtGOItCoqvzhipGu35nEvdMQyh2t_W7QtYaiIKX0segCk-6obsJTCgwx6sZsfx--WZeYksCq2qUJcglvrKtaCX3Yx"
        val client = NetworkService.getInstance()
            ?.getApolloClientWithTokenInterceptor(token)

        //40.730610 -73.935242

        //val query = GetBQuery(Input.fromNullable(latitude), Input.fromNullable(longitude), Input.fromNullable(offset))
        val query = GetBQuery(Input.fromNullable(40.730610), Input.fromNullable(-73.935242), Input.fromNullable(offset))

        client
            ?.query(query)
            ?.enqueue(object: ApolloCall.Callback<GetBQuery.Data>() {
                override fun onFailure(e: ApolloException) {
                    Log.d(TAG, "onFailure")
                }

                override fun onResponse(response: Response<GetBQuery.Data>) {
                    if (!response.hasErrors()) {
                        val test = response.data?.search()?.business()
                        if (test?.isNotEmpty() ?: false) {
                            test?.forEach {
                                list.add(
                                    Rest(
                                        it.name() ?: "def",
                                        it.distance() ?: 0.0,
                                        it.rating() ?: 0.0,
                                        it.photos() ?: listOf()

                                    )
                                )
                            }
                            rests.postValue(list)
                        } else {
                            isExhausted = true
                        }
                    }
                }

            })
    }
}