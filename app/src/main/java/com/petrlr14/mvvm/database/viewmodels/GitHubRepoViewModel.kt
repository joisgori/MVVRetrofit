package com.petrlr14.mvvm.database.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.petrlr14.mvvm.database.RoomDB
import com.petrlr14.mvvm.database.entities.GitHubRepo
import com.petrlr14.mvvm.database.repositories.GitHubRepoRepository

class GitHubRepoViewModel(private val app: Application) : AndroidViewModel(app) {

    private val repository: GitHubRepoRepository

    init {
        val repoDao=RoomDB.getInstance(app).repoDao()
        repository= GitHubRepoRepository(repoDao)
        repository = GitHubRepoRepository(repoDao, githubService)
    }

    private suspend fun insert(repo:GitHubRepo)=repository.insert(repo)
    
    fun retrieveRepo(user:String) = viewModelScope.launch{
        this@GitHubRepoViewModel.nuke() //Esta función manda a llamar a la base de datos y hace un delete
        val response = repository.retrieveReposAsync(user).await()
        
        if (respone.isSuccessful) with (response) {
            this.body()?.forEach{
                this@GitHubRepoViewModel.insert(it)
            }    
        } else with(response){
            when(response.code()){
                404->{
                    Toast.makeText(app, "RIP", Toast.LENGTH).show()
                }
            }
        }
    }

    fun getAll():LiveData<List<GitHubRepo>>{
        return repository.getAll()
    }

    private suspend fun nuke()= repository.nuke()

}
