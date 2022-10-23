package com.imaec.alicorntest.ui.chat

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.SimpleItemAnimator
import com.imaec.alicorntest.BR
import com.imaec.alicorntest.R
import com.imaec.alicorntest.base.BaseActivity
import com.imaec.alicorntest.base.BaseSingleViewAdapter
import com.imaec.alicorntest.common.KeyboardVisibilityUtils
import com.imaec.alicorntest.databinding.ActivityChatBinding
import com.imaec.alicorntest.model.ChatVo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatActivity : BaseActivity<ActivityChatBinding>(R.layout.activity_chat) {

    private val viewModel by viewModels<ChatViewModel>()
    private lateinit var keyboardVisibilityUtils: KeyboardVisibilityUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupRecyclerView()
        setupData()
        setupListener()
        setupObserver()
    }

    override fun onDestroy() {
        keyboardVisibilityUtils.detachKeyboardListeners()
        super.onDestroy()
    }

    private fun setupBinding() {
        binding.vm = viewModel
    }

    private fun setupRecyclerView() {
        with(binding.rvChat) {
            val diffUtil = object : DiffUtil.ItemCallback<ChatVo>() {
                override fun areItemsTheSame(oldItem: ChatVo, newItem: ChatVo): Boolean =
                    oldItem == newItem

                override fun areContentsTheSame(oldItem: ChatVo, newItem: ChatVo): Boolean =
                    oldItem == newItem
            }

            val animator = itemAnimator
            if (animator is SimpleItemAnimator) {
                animator.supportsChangeAnimations = false
            }

            adapter = BaseSingleViewAdapter(
                layoutResId = R.layout.item_chat,
                bindingItemId = BR.item,
                viewModel = mapOf(BR.vm to viewModel),
                diffUtil = diffUtil
            )
        }
    }

    private fun setupData() {
        viewModel.fetchData()
    }

    private fun setupListener() {
        keyboardVisibilityUtils = KeyboardVisibilityUtils(
            window,
            onShowKeyboard = {
                scrollToBottom()
            }
        )
    }

    private fun setupObserver() {
        viewModel.state.observe(this) {
            when (it) {
                ChatState.OnDataLoaded -> {
                    scrollToBottom()
                }
            }
        }
    }

    private fun scrollToBottom() {
        with(binding.rvChat) {
            postDelayed(
                {
                    adapter?.itemCount?.let { itemCount ->
                        scrollToPosition(itemCount - 1)
                    }
                },
                100
            )
        }
    }
}
