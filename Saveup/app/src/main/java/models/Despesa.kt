package models

data class Despesa(
    val codigo: Int?,
    val nome: String,
    val descricao: String,
    val valor: Double,
    val data: String,
    val categoria: String,
    val qtdParcelas: Int,
    val fkUsuario: Int
)
data class DespesaCriacao(
    val nome: String,
    val descricao: String,
    val valor: Double,
    val data: String,
    val categoria: String,
)
