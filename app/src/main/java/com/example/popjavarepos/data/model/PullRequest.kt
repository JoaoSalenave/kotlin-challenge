package com.example.popjavarepos.data.model

import com.google.gson.annotations.SerializedName

data class PullRequest(
    val id: Long,
    val title: String,
    val body: String?,
    @SerializedName("created_at")
    val createdAt: String,
    val user: Owner,
    @SerializedName("html_url")
    val htmlUrl: String
)
