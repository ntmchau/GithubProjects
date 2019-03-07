package com.git.example.ntmchau.gitsample.data.local

import androidx.room.Embedded

data class RepoExt constructor(@Embedded
                               var repo: Repo,
                               @Embedded
                               var owner: Owner)