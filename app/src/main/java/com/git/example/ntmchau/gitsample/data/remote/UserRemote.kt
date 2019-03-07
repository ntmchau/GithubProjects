package com.git.example.ntmchau.gitsample.data.remote

import com.git.example.ntmchau.gitsample.data.local.Owner
import com.google.gson.annotations.SerializedName

class UserRemote constructor(@field:SerializedName("id") var id: Int,
                             @field:SerializedName("login") var login: String,
                             @field:SerializedName("avatar_url") var avatarUrl: String,
                             @field:SerializedName("site_admin") var siteAdmin: Boolean,
                             @field:SerializedName("url") var url: String,
                             @field:SerializedName("subscriptions_url") var subscriptionsUrl: String,
                             @field:SerializedName("organizations_url") var organizationsUrl: String,
                             @field:SerializedName("repos_url") var reposUrl: String) {
    fun toOwner(): Owner = Owner(id, login, avatarUrl, siteAdmin, url, subscriptionsUrl, organizationsUrl, reposUrl)
}