package com.example.saveup.resumo

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.saveup.R
import com.example.saveup.USUARIO
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.util.Calendar

class FragmentDashboardResumo : Fragment() {

    lateinit var viewModel: DashboardResumoViewModel

    lateinit var lineList:ArrayList<Entry>
    lateinit var dataSet: LineDataSet
    lateinit var lineData: LineData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dashboard_resumo, container, false)

        viewModel = DashboardResumoViewModel()

        viewModel.getAllDespesasByIdUsuario(USUARIO.id ?: 0)
        viewModel.getAllReceitasByIdUsuario(USUARIO.id ?: 0)

        viewModel.allReceitas.observe(viewLifecycleOwner){
            reloadValues()
        }

        viewModel.allDespesas.observe(viewLifecycleOwner){
            reloadValues()
        }

        viewModel.saldoPorMes.observe(viewLifecycleOwner){
            plotaGraficoValorGuardadoPorMes(view)
        }

        return view
    }

    private fun reloadValues(){
        calculaSaldo()
        calculaSaldoDoMes()
        geraSaldoPorMes()
    }

    fun getValorGuardadoPorMes():List<ValorGrafico>{
        return viewModel.saldoPorMes.value ?: listOf()
    }

    private fun calculaSaldo(){
        var saldo = 0.0
        viewModel.allReceitas.value?.forEach {
            saldo += it.valor
        }
        viewModel.allDespesas.value?.forEach {
            saldo -= it.valor
        }
        val tvSaldo = requireView().findViewById<TextView>(R.id.tv_saldo)
        tvSaldo.text = "$saldo"
    }

    private fun calculaSaldoDoMes(){
        var saldo = 0.0
        val mesAtual = Calendar.getInstance().get(Calendar.MONTH) + 1
        val anoAtual = Calendar.getInstance().get(Calendar.YEAR)
        viewModel.allReceitas.value?.forEach {
            val mes = it.mes
            val ano = it.ano
            if(mes == mesAtual && ano == anoAtual){
                saldo += it.valor
            }
        }
        viewModel.allDespesas.value?.forEach {
            val mes = it.mes
            val ano = it.ano
            if(mes == mesAtual && ano == anoAtual){
                saldo -= it.valor
            }
        }
        val tvGuardadoMes = requireView().findViewById<TextView>(R.id.tv_valor_guardado_mes)
        tvGuardadoMes.text = "$saldo"
    }

    private fun geraSaldoPorMes(){
        val saldoPorMes = mutableListOf<ValorGrafico>()
        val mesAtual = Calendar.getInstance().get(Calendar.MONTH) + 1
        val anoAtual = Calendar.getInstance().get(Calendar.YEAR)
        for(i in 1..mesAtual){
            var saldo = 0.0
            viewModel.allReceitas.value?.forEach {
                val mes = it.mes
                val ano = it.ano
                if(mes == i && ano == anoAtual){
                    saldo += it.valor
                }
            }
            viewModel.allDespesas.value?.forEach {
                val mes = it.mes
                val ano = it.ano
                if(mes == i && ano == anoAtual){
                    saldo -= it.valor
                }
            }
            saldoPorMes.add(ValorGrafico(saldo, i, anoAtual))
        }
        viewModel.saldoPorMes.value = saldoPorMes
    }

    private fun plotaGraficoValorGuardadoPorMes(view: View){
        // Obtenha uma referência para o LineChart
        val lineChart = view.findViewById<LineChart>(R.id.linear_chart)

        // Criar uma lista de entradas (Entry) com dados de exemplo
        val entries = mutableListOf<Entry>()

        for (valorGrafico in viewModel.saldoPorMes.value!!) {
            val xValue = valorGrafico.ano.toFloat() + valorGrafico.mes.toFloat() / 100 // combinação de ano e mês para o eixo X
            val yValue = valorGrafico.valor.toFloat() // valor do eixo Y (valor)

            val entry = Entry(xValue, yValue)
            entries.add(entry)
        }

        // Criar um conjunto de dados (LineDataSet) e atribuir as entradas a ele
        val dataSet = LineDataSet(entries, "Valores Guardados")

        val verde_bolinha_grafico = ContextCompat.getColor(requireContext(),
            R.color.verde_grafico_inline
        )

        dataSet.color = verde_bolinha_grafico
        dataSet.valueTextColor = Color.BLACK
        dataSet.valueTextSize = 15f
        dataSet.setDrawFilled(true)
        dataSet.fillColor = Color.parseColor("#4CAF50") // verde claro
        dataSet.fillAlpha = 30 // opacidade de 0 a 255
        dataSet.circleRadius = 8f
        dataSet.circleHoleColor = verde_bolinha_grafico
        dataSet.circleColors = mutableListOf(verde_bolinha_grafico)

        // Criar um objeto LineData e atribuir o conjunto de dados a ele
        val lineData = LineData(dataSet)

        // Configurar o LineChart com os dados e personalizações desejadas
        lineChart.data = lineData
        lineChart.description.isEnabled = false

        // Configurar o eixo X para exibir todos os rótulos
        val xAxis = lineChart.xAxis
        val xLabels = mutableListOf<String>()
        for (valorGrafico in viewModel.saldoPorMes.value!!) {
            val mes = obterNomeMes(valorGrafico.mes)
            xLabels.add("$mes ${valorGrafico.ano}")
        }
        xAxis.valueFormatter = IndexAxisValueFormatter(xLabels.toTypedArray())

        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
//        xAxis.setAvoidFirstLastClipping(false)

        //removendo grids no fundo do gráfico

        val yAxisLeft = lineChart.axisLeft
        yAxisLeft.setDrawGridLines(false)
        yAxisLeft.setDrawAxisLine(false)

        val yAxisRight = lineChart.axisRight
        yAxisRight.setDrawGridLines(false)
        yAxisRight.setDrawAxisLine(false)
        //Fim removendo grid do fundo

        // Ajustar a largura dos rótulos do eixo X
        xAxis.labelRotationAngle = -45f
        xAxis.labelCount = entries.size

        val yValues = entries.map { it.y } // Extrair os valores y de cada entrada
        val minValue = yValues.minOrNull()?.minus(100f) ?: 0f // Valor mínimo, considerando que o mínimo seja 0 se não houver valores
        val maxValue = (yValues.maxOrNull()?.plus(100f)) ?: 100f // Valor máximo, considerando que o máximo seja 100 se não houver valores

        lineChart.axisLeft.axisMinimum = minValue
        lineChart.axisLeft.axisMaximum = maxValue
        lineChart.axisRight.isEnabled = false
        lineChart.legend.isEnabled = true
        lineChart.invalidate()
    }

    fun obterNomeMes(numeroMes: Int): String {
        return when (numeroMes) {
            1 -> "Jan"
            2 -> "Fev"
            3 -> "Mar"
            4 -> "Abr"
            5 -> "Mai"
            6 -> "Jun"
            7 -> "Jul"
            8 -> "Ago"
            9 -> "Set"
            10 -> "Out"
            11 -> "Nov"
            12 -> "Dez"
            else -> ""
        }
    }


}

data class ValorGrafico(val valor:Double, val mes:Int, val ano:Int)