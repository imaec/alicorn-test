package com.imaec.alicorntest.ui.newchat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class NewChatViewModel @Inject constructor() : ViewModel() {

    private val _connectedPeopleList = MutableLiveData<List<String>>()
    val connectedPeopleList: LiveData<List<String>> get() = _connectedPeopleList

    val receiver = MutableLiveData<String>()

    fun fetchData() {
        viewModelScope.launch {
            _connectedPeopleList.value = listOf("", "", "", "", "", "")
        }
    }

    fun onClickPeople(item: String) {
    }
}
