package com.miprimersistemaweb.appcatalogo.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.miprimersistemaweb.appcatalogo.db.entity.Usuario

@Dao
interface UsuarioDao {
    @Insert
    fun insert(vararg usuario:Usuario)
    @Delete
    fun eliminar(vararg usuario: Usuario)
    @Query("SELECT * FROM usuario")
    fun  listar():Usuario
}