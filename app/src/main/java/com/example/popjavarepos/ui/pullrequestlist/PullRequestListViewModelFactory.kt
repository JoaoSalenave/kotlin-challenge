package com.example.popjavarepos.ui.pullrequestlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.popjavarepos.data.repository.PullRequestRepository

class PullRequestListViewModelFactory(
    private val repository: PullRequestRepository,
    private val owner: String,
    private val repo: String
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PullRequestListViewModel(repository, owner, repo) as T
    }
}