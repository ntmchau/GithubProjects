package com.git.example.ntmchau.gitsample.data.local

import androidx.room.Entity

@Entity(
    tableName = "owner",
    primaryKeys = ["login"]
)
data class Owner constructor(var ownerId: Int,
                             var login: String,
                             var avatarUrl: String,
                             var siteAdmin: Boolean,
                             var url: String,
                             var subscriptionsUrl: String,
                             var organizationsUrl: String,
                             var reposUrl: String)