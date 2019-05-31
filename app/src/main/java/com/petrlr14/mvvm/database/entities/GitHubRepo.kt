package com.petrlr14.mvvm.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repos")
data class GitHubRepo(
    
    @PrimaryKey
    @field:Json(name="id")
    val id: Long,
    
    @field:Json(name="name")
    val name: String,
    
    @field:Json(name="full_name") //no sé si esto debe ir en la línea 17 y lo de la 18 en la 17
    @ColumnInfo(name="full_name")
    val fullName:String
)
