package com.example.saveup

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate

class DashboardResumo : AppCompatActivity() {
    lateinit var lineList:ArrayList<Entry>
    lateinit var dataSet: LineDataSet
    lateinit var lineData: LineData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_resumo)
        lineList = ArrayList()
        lineList.add(Entry(10f,100f))
        lineList.add(Entry(20f,300f))
        lineList.add(Entry(30f,200f))
        lineList.add(Entry(40f,600f))
        lineList.add(Entry(50f,500f))
        lineList.add(Entry(60f,900f))

        dataSet = LineDataSet(lineList, "Valor guardado por mês")
        lineData = LineData(dataSet)
        R.layout.activity_dashboard_resumo
        val line_chart: LineChart = findViewById(R.id.linear_chart)

        val verde_bolinha_grafico = ContextCompat.getColor(this, R.color.verde_grafico_inline)

        line_chart.data = lineData
        dataSet.color = verde_bolinha_grafico
        dataSet.valueTextColor = Color.CYAN
        dataSet.valueTextSize = 15f
        dataSet.setDrawFilled(true)
        dataSet.fillColor = Color.parseColor("#4CAF50") // verde claro
        dataSet.fillAlpha = 30 // opacidade de 0 a 255
        dataSet.circleRadius = 8f
        dataSet.circleHoleColor = verde_bolinha_grafico
        dataSet.circleColors = mutableListOf(verde_bolinha_grafico)

        //Descricao do grafico

        line_chart.legend.setDrawInside(false)
        line_chart.legend.textColor = Color.WHITE
        line_chart.legend.form = Legend.LegendForm.CIRCLE

        line_chart.apply {
            setNoDataText("Nenhum dado disponível no momento")//msg exibida quando não há dados
            title = "Dinheiro guardado"
            setNoDataTextColor(Color.GREEN)
        }

        //removendo grids no fundo do gráfico
        val xAxis = line_chart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)
        xAxis.valueFormatter

        val yAxisLeft = line_chart.axisLeft
        yAxisLeft.setDrawGridLines(false)
        yAxisLeft.setDrawAxisLine(false)

        val yAxisRight = line_chart.axisRight
        yAxisRight.setDrawGridLines(false)
        yAxisRight.setDrawAxisLine(false)
        //Fim removendo grid do fundo
    }
}