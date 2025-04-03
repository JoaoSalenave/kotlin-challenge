package com.example.popjavarepos.ui.repositorylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.popjavarepos.data.repository.RepositoryListRepository

class RepositoryListViewModelFactory(
    private val repository: RepositoryListRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RepositoryListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RepositoryListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}