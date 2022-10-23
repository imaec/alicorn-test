package com.imaec.alicorntest.ui.login

sealed class LoginState {

    object LoginSuccess : LoginState()
    data class ShowToast(val message: String) : LoginState()
}
