package com.dicoding.mygithubapi.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.mygithubapi.R
import com.dicoding.mygithubapi.data.local.entity.FavoriteEntity
import com.dicoding.mygithubapi.databinding.ActivityDetailBinding
import com.dicoding.mygithubapi.ui.viewModel.DetailViewModel
import com.dicoding.mygithubapi.ui.ViewModelFactory
import com.dicoding.mygithubapi.ui.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    lateinit var activityDetailBinding: ActivityDetailBinding
    lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(activityDetailBinding.root)

        val username : String = intent.getStringExtra(USER_ID)?:"Fwish"
        val image: String = intent.getStringExtra(USER_IMAGE)?:"Fwish"

        var favoritedUser: FavoriteEntity = FavoriteEntity().apply {
            this.username = username
            this.avatarUrl = image
        }

        viewModel = obtainViewModel(this@DetailActivity)

        viewModel.user.observe(this){
            activityDetailBinding.nameTextView.text = it.name
            activityDetailBinding.usernameTextView.text = it.login
            activityDetailBinding.jumlahFollowing.text = it.following.toString()
            activityDetailBinding.jumlahFollower.text = it.followers.toString()
            Glide.with(activityDetailBinding.detailImageView)
                .load(it.avatarUrl)
                .into(activityDetailBinding.detailImageView)
        }

        viewModel.getAllFavorited().observe(this){favoritedUsers ->
            favoritedUsers?.forEach{
                if(it.username == username){
                    viewModel.setIsFavorite(true)
                    favoritedUser = it
                }
            }
        }

        viewModel.isFavorite.observe(this){
            setIconIsFavorite(it)
        }

        viewModel.loadingDetail.observe(this){
            showLoading(it)
        }

        viewModel.findGetUserData(username)

        viewModel.findGetUserFollowers(username)

        viewModel.findGetUserFollowing(username)

        val pagerAdapter = ViewPagerAdapter(this)
        activityDetailBinding.viewPager.adapter = pagerAdapter
        TabLayoutMediator(activityDetailBinding.tabs, activityDetailBinding.viewPager){tab, position ->
            when(position){
                0 -> tab.text = "Followers"
                1 -> tab.text = "Followings"
            }
        }.attach()
        supportActionBar?.elevation = 0f

        activityDetailBinding.fabFavorite?.setOnClickListener {
            viewModel.updateFavorite(favoritedUser, this)
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(DetailViewModel::class.java)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            activityDetailBinding.progressBarDetail.visibility = View.VISIBLE
        } else {
            activityDetailBinding.progressBarDetail.visibility = View.GONE
        }
    }

    private fun setIconIsFavorite(isFavorite: Boolean){
        if(isFavorite){
            activityDetailBinding.fabFavorite?.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorited))
        }else{
            activityDetailBinding.fabFavorite?.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite))
        }
    }

    companion object{
        const val USER_ID = "userid"
        const val USER_IMAGE ="image"
    }
}