package com.example.saveup

data class GraficoObjetivo(
    val titulo: String,
    val corpo: String,
    val valorMeta: Double,
    val valorAtual: Double,
    val extrato: List<Double>
)
