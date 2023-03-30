package com.example.saveup

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class MainActivity : AppCompatActivity() {
    lateinit var lineList:ArrayList<Entry>
    lateinit var lineDataSet: LineDataSet
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

        lineDataSet = LineDataSet(lineList, "SaveUp")
        lineData = LineData(lineDataSet)
        R.layout.activity_dashboard_resumo
        val line_chart: LineChart = findViewById(R.id.linear_chart)
        line_chart.data = lineData
        lineDataSet.color = Color.BLACK
//        lineDataSet.setColors(*ColorTemplate.JOYFUL_COLORS)
        lineDataSet.valueTextColor = Color.BLUE
        lineDataSet.valueTextSize = 15f
        lineDataSet.setDrawFilled(true)
    }
}