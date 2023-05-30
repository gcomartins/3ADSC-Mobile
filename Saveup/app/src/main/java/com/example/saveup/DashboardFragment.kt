package com.example.saveup

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.MPPointF

class DashboardFragment : Fragment() {

    private lateinit var ourPieChart: PieChart
    private lateinit var recyclerView: RecyclerView
    private lateinit var meuAdapter: CategoriAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        ourPieChart = view.findViewById(R.id.pieChartt)

        val color = ContextCompat.getColor(requireContext(), R.color.white_ice) // Substitua R.color.my_color pelo ID da cor desejada

        ourPieChart.setBackgroundColor(color) // Define a cor desejada como plano de fundo do gráfico

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
//        entries.add(PieEntry(5f))

        // on below line we are setting pie data set
        val dataSet = PieDataSet(entries, "teste")
        // on below line we are setting icons.
        dataSet.setDrawIcons(false)

        // on below line we are setting slice for pie
        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 5f
        dataSet.sliceSpace = 2f

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

        val dataList = listOf(
            Categoria("Teste", "A", 20.0, "testeeee"),
            Categoria("Teste", "B", 20.0, "testeeee"),
            Categoria("Teste", "C", 20.0, "testeeee"),
        )

        // Inicialize o Adapter com os dados desejados e defina-o no RecyclerView
        // O adapter que vai pegar os dados e transformar no grafico de objetivo
        meuAdapter = CategoriAdapter(dataList, requireContext())
        recyclerView.adapter = meuAdapter

        return view
    }


}

data class Categoria(
    val titulo: String,
    val Letra: String,
    val valorCategoria: Double,
    val descricao: String,
)

class CategoriAdapter(
    private val list: List<Categoria>,
    private val context: Context
) : RecyclerView.Adapter<CategoriAdapter.GraficoCategoriaViewHolder>() {

    inner class GraficoCategoriaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        //FUNCAO QUE PEGA OS DADOS E TRANSFORMA NO GRAFICO DE OBJETIVO
        fun bind(objetivo: Categoria){
            val titulo = itemView.findViewById<TextView>(R.id.titulo)
            val descricao = itemView.findViewById<TextView>(R.id.descricao)
            val valorObjetivo = itemView.findViewById<TextView>(R.id.valorObjetivo)
            val letraObjetivo = itemView.findViewById<TextView>(R.id.card_letra)
            val grafico = itemView.findViewById<ProgressBar>(R.id.progress_circular)

            titulo.text = objetivo.titulo
            descricao.text = objetivo.descricao
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GraficoCategoriaViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_deposito_objetivo, parent, false)
        return GraficoCategoriaViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: GraficoCategoriaViewHolder, position: Int) {
        val categoria = list[position]

        holder.bind(categoria)
    }
}