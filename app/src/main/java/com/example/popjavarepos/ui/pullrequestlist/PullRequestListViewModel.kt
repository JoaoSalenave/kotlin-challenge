package com.example.popjavarepos.ui.pullrequestlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.popjavarepos.data.model.PullRequest
import com.example.popjavarepos.data.repository.PullRequestRepository
import kotlinx.coroutines.launch

class PullRequestListViewModel(
    private val repository: PullRequestRepository,
    private val owner: String,
    private val repo: String
) : ViewModel() {

    private val _pullRequests = MutableLiveData<List<PullRequest>>()
    val pullRequests: LiveData<List<PullRequest>> = _pullRequests

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        loadPullRequests()
    }

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private fun loadPullRequests() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val pullRequests = repository.getPullRequests(owner, repo)
                _pullRequests.postValue(pullRequests)
            } catch (e: Exception) {
                _error.postValue(e.message)
                e.printStackTrace()
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}

