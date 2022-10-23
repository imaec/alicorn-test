package com.imaec.alicorntest.ui.newchat

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.SimpleItemAnimator
import com.imaec.alicorntest.BR
import com.imaec.alicorntest.R
import com.imaec.alicorntest.base.BaseActivity
import com.imaec.alicorntest.base.BaseSingleViewAdapter
import com.imaec.alicorntest.databinding.ActivityNewChatBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewChatActivity : BaseActivity<ActivityNewChatBinding>(R.layout.activity_new_chat) {

    private val viewModel by viewModels<NewChatViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupRecyclerView()
        setupData()
        setupListener()
    }

    private fun setupBinding() {
        binding.vm = viewModel
    }

    private fun setupRecyclerView() {
        with(binding.rvConnectedPeople) {
            val diffUtil = object : DiffUtil.ItemCallback<String>() {
                override fun areItemsTheSame(
                    oldItem: String,
                    newItem: String
                ): Boolean = oldItem == newItem

                override fun areContentsTheSame(
                    oldItem: String,
                    newItem: String
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

    private fun setupData() {
        viewModel.fetchData()
    }

    private fun setupListener() {
        binding.mtbNewChat.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}
