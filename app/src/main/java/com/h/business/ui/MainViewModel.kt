package com.h.business.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.h.business.data.Rest
import com.h.business.repo.Repository

class MainViewModel() : ViewModel() {

    private var offset = 0
    val repository: Repository

    init {
        repository = Repository()
    }

    fun getListRest(): MutableLiveData<List<Rest>> {
        return repository.getRests()
    }

    fun getB(latitude: Double, longitude: Double) {
        if (!repository.isExhausted()) {
            repository.getB(latitude, longitude, offset)
            offset += 10
        }
    }


}