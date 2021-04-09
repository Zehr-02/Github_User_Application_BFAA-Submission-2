package com.example.submissionfundamentalandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.example.submissionfundamentalandroid.databinding.ActivityDetailBinding



class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    companion object {
        const val GITHUB_USER_DATA = "data_user_github"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userData = intent.getParcelableExtra<GithubUser>(GITHUB_USER_DATA) as GithubUser
        Glide.with(this)
            .load(userData.userAvatar)
            .into(binding.userAvatar)
        binding.username.text = userData.userName
        binding.tvNameOfUser.text = userData.nameOfUser
        binding.githubLink.text = userData.githubLink
        binding.tvCompany.text = userData.company

        binding.btnShare.setOnClickListener {
            val textName =
                "I found a promising programmer in GitHub with the username of ${userData.userName}. \nGithub Link: ${userData.githubLink}."
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, textName)
            startActivity(shareIntent)
        }
    }

}