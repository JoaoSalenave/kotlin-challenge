package com.example.popjavarepos.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Repository(
    val id: Long,
    val name: String,
    val description: String?,
    val owner: Owner,
    @SerializedName("stargazers_count")
    val stars: Int,
    @SerializedName("forks_count")
    val forks: Int
) : Serializable