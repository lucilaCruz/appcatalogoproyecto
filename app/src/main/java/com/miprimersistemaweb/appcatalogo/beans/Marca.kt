package com.miprimersistemaweb.appcatalogo.beans

data class Marca(var id:String,var nombreMarca:String){
    override fun toString(): String {
        return nombreMarca
    }
}
