package com.example.submissionfundamentalandroid

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submissionfundamentalandroid.databinding.GithubUserRecyclerViewBinding
import de.hdodenhof.circleimageview.CircleImageView

class ListUserAdapter : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {
    private var userData = ArrayList<GithubUser>()

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = GithubUserRecyclerViewBinding.bind(itemView)
        fun bind(githubUser: GithubUser){
            Glide.with(itemView.context)
                .load(githubUser.userAvatar)
                .into(binding.ivUserPhoto)
            binding.tvUsername.text = githubUser.userName
            binding.tvFollowerCount.text = githubUser.followerCount.toString()
            binding.tvFollowingCount.text = githubUser.followingCount.toString()
        }

    }

    fun setData(items: ArrayList<GithubUser>){
        userData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.github_user_recycler_view, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = userData.size


    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(userData[position])
        holder.itemView.setOnClickListener{
            val intent = Intent(it.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.GITHUB_USER_DATA, userData[position])
            it.context.startActivity(intent)
        }
    }

}