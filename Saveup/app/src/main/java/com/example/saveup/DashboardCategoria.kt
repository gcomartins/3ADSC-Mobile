package com.example.saveup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Pie
import com.example.saveup.databinding.ActivityDashboardCategoriaBinding

class DashboardCategoria: AppCompatActivity() {
    private lateinit var binding: ActivityDashboardCategoriaBinding
    private var chart: AnyChartView? = null

    private val salary = listOf(200,300,400,600,500)
    private val month = listOf("Comidas","Contas","Transporte","Compras","Entreterimento")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardCategoriaBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_dashboard_categoria)
        supportActionBar?.hide()
        chart = findViewById(R.id.pieChart)

        configChartView()
    }

    private fun configChartView() {
        val pie : Pie = AnyChart.pie()


        val dataPieChart: MutableList<DataEntry> = mutableListOf()

        for (index in salary.indices){
            dataPieChart.add(ValueDataEntry(month.elementAt(index),salary.elementAt(index)))
        }

        pie.data(dataPieChart)
        chart!!.setChart(pie)

    }
}



