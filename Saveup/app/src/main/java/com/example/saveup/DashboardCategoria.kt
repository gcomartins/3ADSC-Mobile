package com.example.saveup

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.saveup.databinding.ActivityDashboardCategoriaBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class   DashboardCategoria: AppCompatActivity() {
    private lateinit var binding: ActivityDashboardCategoriaBinding
    private lateinit var ourPieChart: PieChart
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardCategoriaBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_dashboard_categoria)
        supportActionBar?.hide()
        ourPieChart = binding.pieChart
        retrieveRecordsAndPopulateCharts()
    }
    fun retrieveRecordsAndPopulateCharts() {
        //calling the retreiveAnimals method of DatabaseHandler class to read the records
         val animal = listOf(200,300,400,600,500)
        //create arrays for storing the values gotten
        val animalIDArray = Array<Int>(animal.size) { 0 }
        val animalNameArray = Array<String>(animal.size) { "natgeo" }
        val animalNumberArray = Array<Int>(animal.size) { 5 }
        val animalAgeArray = Array<Int>(animal.size) { 5 }
        val animalGrowthArray = Array<Int>(animal.size) { 0 }

        //call the methods for populating the charts
        populatePieChart(animalNumberArray, animalNameArray)

    }


    private fun populatePieChart(values: Array<Int>, labels: Array<String>) {
        //an array to store the pie slices entry
        val ourPieEntry = ArrayList<PieEntry>()
        var i = 0

        for (entry in values) {
            //converting to float
            var value = values[i].toFloat()
            var label = labels[i]
            //adding each value to the pieentry array
            ourPieEntry.add(PieEntry(value, label))
            i++
        }

        //assigning color to each slices
        val pieShades: ArrayList<Int> = ArrayList()
        pieShades.add(Color.parseColor("#0E2DEC"))
        pieShades.add(Color.parseColor("#B7520E"))
        pieShades.add(Color.parseColor("#5E6D4E"))
        pieShades.add(Color.parseColor("#DA1F12"))

        //add values to the pie dataset and passing them to the constructor
        val ourSet = PieDataSet(ourPieEntry, "")
        val data = PieData(ourSet)

        //setting the slices divider width
        ourSet.sliceSpace = 1f

        //populating the colors and data
        ourSet.colors = pieShades
        ourPieChart.data = data
        //setting color and size of text
        data.setValueTextColor(Color.WHITE)
        data.setValueTextSize(10f)

        //add an animation when rendering the pie chart
        ourPieChart.animateY(1400, Easing.EaseInOutQuad)
        //disabling center hole
        ourPieChart.isDrawHoleEnabled = false
        //do not show description text
        ourPieChart.description.isEnabled = false
        //legend enabled and its various appearance settings
        ourPieChart.legend.isEnabled = true
        ourPieChart.legend.orientation = Legend.LegendOrientation.HORIZONTAL
        ourPieChart.legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        ourPieChart.legend.isWordWrapEnabled = true

        //dont show the text values on slices e.g Antelope, impala etc
        ourPieChart.setDrawEntryLabels(false)
        //refreshing the chart
        ourPieChart.invalidate()

    }
}
class AnimalModel (var animalId: Int, var animalName:String, var totNumber:Int, var avgAge: Int, var avgGrowth: Int)



