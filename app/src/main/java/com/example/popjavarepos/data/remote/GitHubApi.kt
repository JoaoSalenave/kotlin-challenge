package com.example.popjavarepos.data.remote

import com.example.popjavarepos.data.model.PullRequest
import com.example.popjavarepos.data.model.RepoResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApi {

    @GET("search/repositories")
    suspend fun getRepositories(
        @Query("q") query: String = "language:Java",
        @Query("sort") sort: String = "stars",
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): RepoResponse

    @GET("repos/{owner}/{repo}/pulls")
    suspend fun getPullRequests(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): List<PullRequest>
}