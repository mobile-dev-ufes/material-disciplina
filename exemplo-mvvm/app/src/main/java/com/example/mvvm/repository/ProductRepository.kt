package com.example.mvvm.repository

class ProductRepository {
    /*
        Essa classe seria a interface com um banco de dados local ou remoto via API.
        Por enquanto, vamos apenas retornar que o registro foi feito com sucesso.
        No futuro, a ideia é implementar as funções de registro aqui dentro.
     */

    fun register(idProd: String, nameProd: String): Boolean {
        return (idProd != "" && nameProd != "")
    }
}