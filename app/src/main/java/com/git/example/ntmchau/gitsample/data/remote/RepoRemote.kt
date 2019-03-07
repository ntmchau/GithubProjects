package com.git.example.ntmchau.gitsample.data.remote

import com.git.example.ntmchau.gitsample.data.local.Repo
import com.google.gson.annotations.SerializedName
import java.util.*

class RepoRemote constructor(@field:SerializedName ("id") var id: Int,
                             @field:SerializedName ("name") var name: String,
                             @field:SerializedName ("full_name") var fullName: String,
                             @field:SerializedName ("private") var isPrivate: Boolean,
                             @field:SerializedName ("description") var description: String,
                             @field:SerializedName ("created_at") var createdAt: Date,
                             @field:SerializedName ("updated_at") var updatedAt: Date,
                             @field:SerializedName ("pushed_at") var pushedAt: Date,
                             @field:SerializedName ("git_url") var gitUrl: String,
                             @field:SerializedName ("ssh_url") var sshUrl: String,
                             @field:SerializedName ("clone_url") var cloneUrl: String,
                             @field:SerializedName ("default_branch") var defaultBranch: String,
                             @field:SerializedName ("owner") val owner: UserRemote) {

    fun toRepo(): Repo  = Repo(id, name, fullName, isPrivate, description, createdAt, updatedAt, pushedAt, gitUrl, sshUrl,
            cloneUrl, defaultBranch, owner.toOwner().login)

}