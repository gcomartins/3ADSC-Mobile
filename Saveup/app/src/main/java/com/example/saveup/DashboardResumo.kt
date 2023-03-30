package com.example.saveup

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate

class DashboardResumo : AppCompatActivity() {
    lateinit var lineList:ArrayList<Entry>
    lateinit var lineDataSet: LineDataSet
    lateinit var lineData: LineData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_resumo)
        lineList = ArrayList()
        lineList.add(Entry(10f,100f))
        lineList.add(Entry(10f,100f))
        lineList.add(Entry(10f,100f))
        lineList.add(Entry(10f,100f))
        lineList.add(Entry(10f,100f))
        lineList.add(Entry(10f,100f))

        lineDataSet = LineDataSet(lineList, "SaveUp")
        lineData = LineData(lineDataSet)
        R.layout.activity_dashboard_resumo
        val line_chart: LineChart = findViewById(R.id.linear_chart)
        line_chart.data = lineData
        lineDataSet.setColors(*ColorTemplate.JOYFUL_COLORS)
        lineDataSet.valueTextColor = Color.BLUE
        lineDataSet.valueTextSize = 20f
    }
}