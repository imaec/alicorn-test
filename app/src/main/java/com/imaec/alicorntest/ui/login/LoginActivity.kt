package com.imaec.alicorntest.ui.login

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.imaec.alicorntest.R
import com.imaec.alicorntest.base.BaseActivity
import com.imaec.alicorntest.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupListener()
        setupObserver()
    }

    private fun setupBinding() {
        binding.vm = viewModel
    }

    private fun setupListener() {
        binding.mtbLogin.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setupObserver() {
        viewModel.state.observe(this) {
            when (it) {
                LoginState.LoginSuccess -> {
                    setResult(RESULT_OK)
                    finish()
                }
                is LoginState.ShowToast -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
