package com.miprimersistemaweb.appcatalogo.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuario")
data class Usuario(
    @PrimaryKey val id:Int,
    @ColumnInfo(name = "name") val name:String,
    @ColumnInfo(name = "email") val email:String,
    @ColumnInfo(name = "token") val token:String,
)
