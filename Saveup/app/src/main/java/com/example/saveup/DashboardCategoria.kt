package com.example.saveup

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.saveup.databinding.ActivityDashboardCategoriaBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.MPPointF

class   DashboardCategoria: AppCompatActivity() {
    private lateinit var binding: ActivityDashboardCategoriaBinding
    private lateinit var ourPieChart: PieChart
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardCategoriaBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_dashboard_categoria)
        supportActionBar?.hide()
        ourPieChart = findViewById(R.id.pieChart)

        // on below line we are setting user percent value,
        // setting description as enabled and offset for pie chart
        ourPieChart.setUsePercentValues(true)
        ourPieChart.getDescription().setEnabled(false)
        ourPieChart.setExtraOffsets(5f, 10f, 5f, 5f)

        // on below line we are setting drag for our pie chart
        ourPieChart.setDragDecelerationFrictionCoef(0.95f)

        // on below line we are setting hole
        // and hole color for pie chart
        ourPieChart.setDrawHoleEnabled(true)
        ourPieChart.setHoleColor(Color.WHITE)

        // on below line we are setting circle color and alpha
        ourPieChart.setTransparentCircleColor(Color.WHITE)
        ourPieChart.setTransparentCircleAlpha(110)

        // on  below line we are setting hole radius
        ourPieChart.setHoleRadius(58f)
        ourPieChart.setTransparentCircleRadius(61f)

        // on below line we are setting center text
        ourPieChart.setDrawCenterText(true)

        // on below line we are setting
        // rotation for our pie chart
        ourPieChart.setRotationAngle(0f)

        // enable rotation of the pieChart by touch
        ourPieChart.setRotationEnabled(true)
        ourPieChart.setHighlightPerTapEnabled(true)

        // on below line we are setting animation for our pie chart
        ourPieChart.animateY(1400, Easing.EaseInOutQuad)

        // on below line we are disabling our legend for pie chart
        ourPieChart.legend.isEnabled = false
        ourPieChart.setEntryLabelColor(Color.WHITE)
        ourPieChart.setEntryLabelTextSize(12f)
        ourPieChart.setHoleRadius(1f)
        ourPieChart.setTransparentCircleRadius(1f)

        // on below line we are creating array list and
        // adding data to it to display in pie chart
        val entries: ArrayList<PieEntry> = ArrayList()
        entries.add(PieEntry(25f))
        entries.add(PieEntry(30f))
        entries.add(PieEntry(20f))
        entries.add(PieEntry(30f))
        entries.add(PieEntry(5f))

        // on below line we are setting pie data set
        val dataSet = PieDataSet(entries, "teste")
        // on below line we are setting icons.
        dataSet.setDrawIcons(false)

        // on below line we are setting slice for pie
        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 5f
        dataSet.sliceSpace = 0f

        // add a lot of colors to list
        val colors: ArrayList<Int> = ArrayList()
        colors.add(resources.getColor(R.color.yellow_dahsboard))
        colors.add(resources.getColor(R.color.pink_dashboard))
        colors.add(resources.getColor(R.color.blue_bold_dashboard))
        colors.add(resources.getColor(R.color.orange_dashboard))
        colors.add(resources.getColor(R.color.teal_200))


        // on below line we are setting colors.
        dataSet.colors = colors

        // on below line we are setting pie data set
        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(15f)
        data.setValueTypeface(Typeface.DEFAULT_BOLD)
        data.setValueTextColor(Color.WHITE)
        ourPieChart.setData(data)

        // undo all highlights
        ourPieChart.highlightValues(null)

        // loading chart
        ourPieChart.invalidate()

    }
}



