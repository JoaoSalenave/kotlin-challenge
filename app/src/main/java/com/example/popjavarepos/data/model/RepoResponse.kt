package com.example.popjavarepos.data.model

data class RepoResponse(
    val total_count: Int,
    val incomplete_results: Boolean,
    val items: List<Repository>
)