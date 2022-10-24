package com.imaec.alicorntest.ui.newchat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imaec.alicorntest.model.ConnectedPeopleVo
import com.imaec.alicorntest.model.ConnectedPeopleVo.Companion.dtoToVo
import com.imaec.domain.successOr
import com.imaec.domain.usecase.GetChatIdParams
import com.imaec.domain.usecase.GetChatIdUseCase
import com.imaec.domain.usecase.GetMemberInfoUseCase
import com.imaec.domain.usecase.SearchConnectedPeopleParams
import com.imaec.domain.usecase.SearchConnectedPeopleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class NewChatViewModel @Inject constructor(
    memberInfoUseCase: GetMemberInfoUseCase,
    private val searchConnectedPeopleUseCase: SearchConnectedPeopleUseCase,
    private val getChatIdUseCase: GetChatIdUseCase
) : ViewModel() {

    private val _state = MutableLiveData<NewChatState>()
    val state: LiveData<NewChatState> get() = _state

    private val _connectedPeopleList = MutableLiveData<List<ConnectedPeopleVo>>()
    val connectedPeopleList: LiveData<List<ConnectedPeopleVo>> get() = _connectedPeopleList

    val memberInfo = memberInfoUseCase()

    val receiver = MediatorLiveData<String>().apply {
        addSource(this) {
            search(it)
        }
    }

    private fun search(keyword: String) {
        viewModelScope.launch {
            _connectedPeopleList.value = searchConnectedPeopleUseCase(
                SearchConnectedPeopleParams(memberInfo?.uid ?: "", keyword)
            ).successOr(emptyList()).map(::dtoToVo)
        }
    }

    fun onClickPeople(item: ConnectedPeopleVo) {
        viewModelScope.launch {
            getChatIdUseCase(
                GetChatIdParams(memberInfo?.uid ?: "", item.uid)
            ).successOr(null)?.let {
                _state.value = NewChatState.OnClickPeople(it, item)
            }
        }
    }
}
