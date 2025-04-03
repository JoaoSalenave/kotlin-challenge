package com.example.popjavarepos.data.repository

import com.example.popjavarepos.data.model.Repository
import com.example.popjavarepos.data.remote.GitHubApi

class RepositoryListRepository(private val api: GitHubApi) {

    suspend fun getRepositories(page: Int, perPage: Int): List<Repository> {
        val response = api.getRepositories(page = page, perPage = perPage)
        return response.items
    }
}
