package com.imaec.alicorntest.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.SimpleItemAnimator
import com.imaec.alicorntest.BR
import com.imaec.alicorntest.R
import com.imaec.alicorntest.base.BaseActivity
import com.imaec.alicorntest.base.BaseSingleViewAdapter
import com.imaec.alicorntest.databinding.ActivityMainBinding
import com.imaec.alicorntest.model.ChatListVo
import com.imaec.alicorntest.ui.chat.ChatActivity
import com.imaec.alicorntest.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupRecyclerView()
        setupData()
        setupObserver()
    }

    private fun setupBinding() {
        binding.vm = viewModel
    }

    private fun setupRecyclerView() {
        with(binding.rvChatList) {
            val diffUtil = object : DiffUtil.ItemCallback<ChatListVo>() {
                override fun areItemsTheSame(oldItem: ChatListVo, newItem: ChatListVo): Boolean =
                    oldItem == newItem

                override fun areContentsTheSame(oldItem: ChatListVo, newItem: ChatListVo): Boolean =
                    oldItem == newItem
            }

            val animator = itemAnimator
            if (animator is SimpleItemAnimator) {
                animator.supportsChangeAnimations = false
            }

            adapter = BaseSingleViewAdapter(
                layoutResId = R.layout.item_chat_list,
                bindingItemId = BR.item,
                viewModel = mapOf(BR.vm to viewModel),
                diffUtil = diffUtil
            )
        }
    }

    private fun setupData() {
        viewModel.fetchData()
    }

    private fun setupObserver() {
        viewModel.state.observe(this) {
            when (it) {
                is MainState.OnClickChat -> {
                    startActivity(
                        Intent(this, ChatActivity::class.java).apply {
                            putExtras(bundleOf("chatId" to it.item.chatId, "name" to it.item.name))
                        }
                    )
                }
                MainState.StartLoginActivity -> {
                    activityResultRegistry.register(
                        "LOGIN",
                        ActivityResultContracts.StartActivityForResult()
                    ) { result ->
                        if (result.resultCode == RESULT_OK) {
                            Toast.makeText(this, "로그인 되었습니다.", Toast.LENGTH_SHORT).show()
                            setupData()
                        } else {
                            onBackPressed()
                        }
                    }.launch(Intent(this, LoginActivity::class.java))
                }
                is MainState.ShowToast -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
