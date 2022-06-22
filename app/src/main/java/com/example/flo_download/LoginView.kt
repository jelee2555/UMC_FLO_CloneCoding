package com.example.flo_download

interface LoginView {
    fun onLoginSuccess(code : Int, result: Result)
    fun onSLoginFailure()
}