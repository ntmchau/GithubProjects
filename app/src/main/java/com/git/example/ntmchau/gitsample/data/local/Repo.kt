package com.git.example.ntmchau.gitsample.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import java.util.*

@Entity(
    tableName = "repository",
    indices = [
        Index("id"),
        Index("ownerLogin")],
    primaryKeys = ["name", "ownerLogin"],
    foreignKeys = [ForeignKey(
        entity = Owner::class,
        parentColumns = ["login"],
        childColumns = ["ownerLogin"],
        onUpdate = ForeignKey.CASCADE,
        deferred = true
    )]
)
data class Repo constructor(
    var id: Int,
    var name: String,
    var fullName: String,
    var isPrivate: Boolean,
    var description: String?,
    var createdAt: Date,
    var updatedAt: Date,
    var pushedAt: Date,
    var gitUrl: String,
    var sshUrl: String,
    var cloneUrl: String,
    var defaultBranch: String,
    var ownerLogin: String
)