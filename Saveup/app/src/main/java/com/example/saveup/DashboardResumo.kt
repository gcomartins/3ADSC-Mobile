package com.example.saveup

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class DashboardResumo : AppCompatActivity() {
    lateinit var lineList:ArrayList<Entry>
    lateinit var dataSet: LineDataSet
    lateinit var lineData: LineData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_resumo)
        val valoresGuardadoPorMes = getValorGuardadoPorMes()
        supportActionBar?.hide()

        var etiquetas = valoresGuardadoPorMes.map { "${it.mes}/${it.ano}" }
        var listaAuxiliar = listOf(etiquetas.get(0))
        etiquetas = listaAuxiliar + etiquetas
        lineList = ArrayList()
        val entries = mutableListOf<Entry>()
        valoresGuardadoPorMes.forEach { dinheiroGuardadoNoMes ->
            entries.add(Entry(dinheiroGuardadoNoMes.mes.toFloat(), dinheiroGuardadoNoMes.valor.toFloat()))
        }

        dataSet = LineDataSet(entries, "")

        lineData = LineData(dataSet)
        R.layout.activity_dashboard_resumo
        val line_chart: LineChart = findViewById(R.id.linear_chart)

        val verde_bolinha_grafico = ContextCompat.getColor(this, R.color.verde_grafico_inline)

        line_chart.data = lineData
        dataSet.color = verde_bolinha_grafico
        dataSet.valueTextColor = Color.BLACK
        dataSet.valueTextSize = 15f
        dataSet.setDrawFilled(true)
        dataSet.fillColor = Color.parseColor("#4CAF50") // verde claro
        dataSet.fillAlpha = 30 // opacidade de 0 a 255
        dataSet.circleRadius = 8f
        dataSet.circleHoleColor = verde_bolinha_grafico
        dataSet.circleColors = mutableListOf(verde_bolinha_grafico)

        //Descricao do grafico

        line_chart.legend.setDrawInside(false)
        line_chart.legend.textColor = Color.GREEN
        line_chart.legend.form = Legend.LegendForm.NONE

        line_chart.apply {
            setNoDataText("Nenhum dado disponível no momento")//msg exibida quando não há dados
            title = "Dinheiro guardado"
            setNoDataTextColor(Color.GREEN)
            description = Description().also { description -> description.text = ""  }//retira description
        }


        //removendo grids no fundo do gráfico

        val yAxisLeft = line_chart.axisLeft
        yAxisLeft.setDrawGridLines(false)
        yAxisLeft.setDrawAxisLine(false)

        val yAxisRight = line_chart.axisRight
        yAxisRight.setDrawGridLines(false)
        yAxisRight.setDrawAxisLine(false)
        //Fim removendo grid do fundo

        val xAxis = line_chart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = IndexAxisValueFormatter(etiquetas)
        xAxis.textColor = Color.BLACK
        xAxis.textSize = 10F

    }

//    simula a busca de dados
    fun getValorGuardadoPorMes():List<DinheiroGuardado>{
        return listOf(
            DinheiroGuardado(100.0,1,2022),
            DinheiroGuardado(200.0,2,2022),
            DinheiroGuardado(300.0,3,2022),
            DinheiroGuardado(500.0,4,2022),
            DinheiroGuardado(200.0,5,2022),
            DinheiroGuardado(300.0,6,2022),
            DinheiroGuardado(250.0,7,2022),
            DinheiroGuardado(0.0,8,2022),

        )

    }
     data class DinheiroGuardado(val valor:Double, val mes:Int, val ano:Int)
}