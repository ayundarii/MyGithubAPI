package com.dicoding.mygithubapi.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.mygithubapi.data.local.entity.FavoriteEntity
import com.dicoding.mygithubapi.data.remote.response.SearchUserData
import com.dicoding.mygithubapi.databinding.ActivityFavoriteBinding
import com.dicoding.mygithubapi.ui.viewModel.FavoriteViewModel
import com.dicoding.mygithubapi.ui.ViewModelFactory
import com.dicoding.mygithubapi.ui.adapter.ListUserAdapter

class FavoriteActivity : AppCompatActivity() {
    private lateinit var rvUser: RecyclerView
    lateinit var activityFavoriteBinding: ActivityFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel

    //not done need an adapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityFavoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        rvUser = activityFavoriteBinding.rvUser

        viewModel = obtainViewModel(this@FavoriteActivity)

        viewModel.getAllFavorite().observe(this) { users: List<FavoriteEntity> ->
            val items = arrayListOf<SearchUserData>()

            users.map {
                val item = SearchUserData(login = it.username, avatarUrl = it.avatarUrl)
                items.add(item)
            }

            val listUserAdapter = ListUserAdapter(items)

            activityFavoriteBinding.rvUser.apply {
                layoutManager = LinearLayoutManager(this@FavoriteActivity)
                adapter = listUserAdapter
            }

            listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
                override fun onItemClicked(data: SearchUserData) {
                    startActivity(
                        Intent(this@FavoriteActivity, DetailActivity::class.java)
                            .putExtra(DetailActivity.USER_ID,data.login)
                            .putExtra(DetailActivity.USER_IMAGE,data.avatarUrl)
                    )
                }
            })
        }

        setContentView(activityFavoriteBinding.root)
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }
}