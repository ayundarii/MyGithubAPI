package com.dicoding.mygithubapi.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mygithubapi.databinding.FragmentFollowingBinding
import com.dicoding.mygithubapi.data.remote.response.FollowItem
import com.dicoding.mygithubapi.ui.viewModel.DetailViewModel
import com.dicoding.mygithubapi.ui.adapter.ListFollowerAdapter
import com.dicoding.mygithubapi.ui.activity.DetailActivity

class FollowingFragment : Fragment() {
    lateinit var binding: FragmentFollowingBinding
    lateinit var viewModel: DetailViewModel
    lateinit var adapter: ListFollowerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(DetailViewModel::class.java)
        viewModel.followings.observe(requireActivity()) {
            adapter = ListFollowerAdapter(it as ArrayList<FollowItem>)
            adapter.setOnItemClickCallback(object : ListFollowerAdapter.OnItemClickCallback {
                override fun onItemClicked(data: FollowItem) {
                    startActivity(
                        Intent(requireActivity(), DetailActivity::class.java)
                            .putExtra(DetailActivity.USER_ID,data.login)
                    )
                }
            })
            binding.rvFollowing.adapter = adapter
            binding.rvFollowing.layoutManager = LinearLayoutManager(requireActivity())
        }
        viewModel.loadingFollowing.observe(requireActivity()){
            showLoading(it)
        }
        return binding.root
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBarFollowing.visibility = View.VISIBLE
        } else {
            binding.progressBarFollowing.visibility = View.GONE
        }
    }
}