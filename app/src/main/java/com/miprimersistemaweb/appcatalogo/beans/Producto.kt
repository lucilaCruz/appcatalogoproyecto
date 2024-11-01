package com.miprimersistemaweb.appcatalogo.beans

data class Producto(var idproducto:Int,
                    var titulo:String,
                    var precio:Double,
                    var nombreCategoria:String,
                    var nombreMarca:String,
                    var imagen:String,
                    var idMarca:Int?=0,
                    var idCategoria:Int?=0
)
