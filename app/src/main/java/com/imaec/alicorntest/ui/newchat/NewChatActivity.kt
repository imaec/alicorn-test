package com.imaec.alicorntest.ui.newchat

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.SimpleItemAnimator
import com.imaec.alicorntest.BR
import com.imaec.alicorntest.R
import com.imaec.alicorntest.base.BaseActivity
import com.imaec.alicorntest.base.BaseSingleViewAdapter
import com.imaec.alicorntest.databinding.ActivityNewChatBinding
import com.imaec.alicorntest.model.ConnectedPeopleVo
import com.imaec.alicorntest.ui.chat.ChatActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewChatActivity : BaseActivity<ActivityNewChatBinding>(R.layout.activity_new_chat) {

    private val viewModel by viewModels<NewChatViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupRecyclerView()
        setupListener()
        setupObserver()
    }

    private fun setupBinding() {
        binding.vm = viewModel
    }

    private fun setupRecyclerView() {
        with(binding.rvConnectedPeople) {
            val diffUtil = object : DiffUtil.ItemCallback<ConnectedPeopleVo>() {
                override fun areItemsTheSame(
                    oldItem: ConnectedPeopleVo,
                    newItem: ConnectedPeopleVo
                ): Boolean = oldItem == newItem

                override fun areContentsTheSame(
                    oldItem: ConnectedPeopleVo,
                    newItem: ConnectedPeopleVo
                ): Boolean = oldItem == newItem
            }

            val animator = itemAnimator
            if (animator is SimpleItemAnimator) {
                animator.supportsChangeAnimations = false
            }

            adapter = BaseSingleViewAdapter(
                layoutResId = R.layout.item_connected_people,
                bindingItemId = BR.item,
                viewModel = mapOf(BR.vm to viewModel),
                diffUtil = diffUtil
            )
        }
    }

    private fun setupListener() {
        binding.mtbNewChat.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setupObserver() {
        viewModel.state.observe(this) {
            when (it) {
                is NewChatState.OnClickPeople -> {
                    startActivity(
                        Intent(this, ChatActivity::class.java).apply {
                            putExtras(bundleOf("chatId" to it.chatId, "name" to it.item.name))
                        }
                    )
                }
            }
        }
    }
}
