package com.example.submissionfundamentalandroid

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubUser(
    var userAvatar: String? = null,
    var userName: String? = null,
    var nameOfUser: String?= null,
    var githubLink: String? = null,
    var company: String? = null,
    var followerCount: String? = null,
    var followingCount: String? = null,
) : Parcelable