package com.imaec.alicorntest.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imaec.domain.successOr
import com.imaec.domain.usecase.LoginParams
import com.imaec.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _state = MutableLiveData<LoginState>()
    val state: LiveData<LoginState> get() = _state

    val id = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    fun onClickLogin() {
        if (id.value.isNullOrEmpty()) {
            _state.value = LoginState.ShowToast("ID를 입력해주세요.")
            return
        }
        if (password.value.isNullOrEmpty()) {
            _state.value = LoginState.ShowToast("비밀번호를 입력해주세요.")
            return
        }

        viewModelScope.launch {
            val result = loginUseCase(LoginParams(id.value!!, password.value!!)).successOr(false)
            _state.value = if (result) {
                LoginState.LoginSuccess
            } else {
                LoginState.ShowToast("ID 또는 비밀번호를 확인해주세요.")
            }
        }
    }
}
