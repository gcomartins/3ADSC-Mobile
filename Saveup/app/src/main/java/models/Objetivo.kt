package models

data class Objetivo(
    val id : Int,
    val nome: String,
    val descricao: String,
    val valor: Double,
    val data: String,
    val categoria: String,
    val valorAtual: Double,
    val dataFinal: String,
    val fkUsuario: Int
)

data class ObjetivoEditado(
    val id : Int,
    val nome: String,
    val descricao: String,
    val valor: Double,
    val fkUsuario: Int
)