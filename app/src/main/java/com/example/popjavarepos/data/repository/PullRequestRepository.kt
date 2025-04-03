package com.example.popjavarepos.data.repository

import com.example.popjavarepos.data.model.PullRequest
import com.example.popjavarepos.data.remote.GitHubApi

class PullRequestRepository(private val api: GitHubApi) {

    suspend fun getPullRequests(owner: String, repo: String): List<PullRequest> {
        return api.getPullRequests(owner, repo)
    }
}