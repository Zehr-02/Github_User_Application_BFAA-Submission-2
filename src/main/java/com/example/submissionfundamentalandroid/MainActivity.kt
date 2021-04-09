package com.example.submissionfundamentalandroid

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionfundamentalandroid.databinding.ActivityMainBinding
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ListUserAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()

        binding.rvGithubUser.layoutManager = LinearLayoutManager(this)
        binding.rvGithubUser.adapter = adapter

        binding.userSearchButton.setOnClickListener {
            val sUserField = binding.userSearchField.text.toString()
            if (sUserField.isEmpty()) return@setOnClickListener
            showLoading(true)
            setUserNames(sUserField)
        }
    }

    private fun setUserNames(username: String) {
        val url = "https://api.github.com/search/users?q=$username"

        val client = AsyncHttpClient()
        client.addHeader("User-Agent", "zehr-02")
        client.addHeader("Authorization", "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")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val userCount = responseObject.getJSONArray("items")
                    Log.d("TAG", result)
                    for (i in 0 until userCount.length()) {
                        val user = userCount.getJSONObject(i)
                        val loginName: String = user.getString("url")
                        getUserDetail(loginName)
                    }
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray,
                error: Throwable?
            ) {
                Log.d("onFaliure1()", error?.message.toString())
            }

        })
    }

    private fun getUserDetail(username: String) {
        val userData = ArrayList<GithubUser>()

        val client = AsyncHttpClient()
        client.addHeader("User-Agent", "zehr-02")
        client.addHeader("Authorization", "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")
        client.get(username, object : AsyncHttpResponseHandler() {
            //username = url = https://api.github.com/users/$username
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val githubUser = GithubUser()
                    githubUser.userAvatar = responseObject.getString("avatar_url").toString()
                    githubUser.userName = responseObject.getString("login").toString()
                    githubUser.nameOfUser = responseObject.getString("name").toString()
                    githubUser.company = responseObject.getString("company").toString()
                    githubUser.githubLink = responseObject.getString("html_url").toString()
                    githubUser.followingCount = responseObject.getString("following").toString()
                    githubUser.followerCount = responseObject.getString("followers").toString()
                    userData.add(githubUser)
                    showLoading(false)
                    adapter.setData(userData)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.d("OnFailureUserData", error?.message.toString())
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}