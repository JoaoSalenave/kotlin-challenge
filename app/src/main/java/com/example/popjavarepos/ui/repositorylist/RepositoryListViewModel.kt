package com.example.popjavarepos.ui.repositorylist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.popjavarepos.data.model.Repository
import com.example.popjavarepos.data.repository.RepositoryListRepository
import kotlinx.coroutines.launch

class RepositoryListViewModel(
    private val repository: RepositoryListRepository
) : ViewModel() {

    private val _repositories = MutableLiveData<List<Repository>>()
    val repositories: LiveData<List<Repository>> = _repositories

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var page = 1
    private val perPage = 30
    private var isLastPage = false

    init {
        loadRepositories()
    }

    fun loadRepositories() {
        if (_isLoading.value == true || isLastPage) return

        _isLoading.value = true
        viewModelScope.launch {
            try {
                val newRepositories = repository.getRepositories(page, perPage)
                if (newRepositories.isNotEmpty()) {
                    val currentList = _repositories.value.orEmpty()
                    _repositories.postValue(currentList + newRepositories)
                    page++
                } else {
                    isLastPage = true
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}

