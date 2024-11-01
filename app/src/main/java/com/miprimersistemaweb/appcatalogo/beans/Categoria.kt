package com.miprimersistemaweb.appcatalogo.beans

data class Categoria(var id:String,var nombreCategoria:String){
    override fun toString(): String {
        return nombreCategoria
    }
}
