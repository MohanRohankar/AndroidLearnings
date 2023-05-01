package com.example.notekeeper.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notekeeper.model.dao.repository.NoteRepository
import com.example.notekeeper.model.repository.IRepository

class ViewModelFactory(private val repositories: Map<String, IRepository>) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository = getRepositoryForModelClass(modelClass)

        @Suppress("UNCHECKED_CAST")
        return createViewModel(modelClass, repository) as T
    }

    private fun getRepositoryForModelClass(modelClass: Class<out ViewModel>): IRepository {
        val key = modelClass.simpleName.removeSuffix("ViewModel")
        return repositories[key] ?: throw IllegalArgumentException("Unknown repository for $key")
    }

    private fun createViewModel(modelClass: Class<out ViewModel>, repository: IRepository): ViewModel {
        return when (modelClass) {
//            NoteViewModel::class.java -> NoteViewModel(repository as NoteRepository)
            // Add other ViewModel classes here
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

