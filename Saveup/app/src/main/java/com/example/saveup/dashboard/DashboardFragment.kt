package com.example.saveup.dashboard

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.saveup.EditarDespesaActivity
import com.example.saveup.R
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.MPPointF
import models.Despesa
import java.util.Locale

class DashboardFragment : Fragment() {

    private val viewModel: DashboardViewModel by lazy {
        DashboardViewModel()
    }

    private lateinit var ourPieChart: PieChart
    private lateinit var recyclerView: RecyclerView
    private lateinit var meuAdapter: DespesaAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        viewModel.getDespesasList()

//        val mapColors = mapOf(
//            "Alimentação" to R.color.yellow_dahsboard,
//            "Educação" to R.color.pink_dashboard,
//            "Lazer" to R.color.blue_bold_dashboard,
//            "Moradia" to R.color.orange_dashboard,
//            "Transporte" to R.color.teal_200,
//            "Alimentos" to R.color.yellow_dahsboard,
//            "Categoriazinha" to R.color.pink_dashboard,
//            "Viagens" to R.color.blue_bold_dashboard,
//            "Moradia" to R.color.orange_dashboard,
//        )

        val mapColors = emptyMap<String, Int>().toMutableMap()
        val colors: ArrayList<Int> = ArrayList()
        colors.add(resources.getColor(R.color.yellow_dahsboard))
        colors.add(resources.getColor(R.color.pink_dashboard))
        colors.add(resources.getColor(R.color.blue_bold_dashboard))
        colors.add(resources.getColor(R.color.orange_dashboard))
        colors.add(resources.getColor(R.color.teal_200))

        viewModel.despesasList.observe(viewLifecycleOwner){

            var indexCor = 0
            it.forEach {
                if(!mapColors.containsKey(it.categoria)){
                    mapColors[it.categoria] = colors[indexCor]
                    indexCor++
                }
            }

            val dataList = it
            meuAdapter = DespesaAdapter(ordenarDespesasPorData(dataList), requireContext(), mapColors)
            recyclerView.adapter = meuAdapter
        }

        recyclerView = view.findViewById(R.id.recyclerView)
        ourPieChart = view.findViewById(R.id.pieChartt)

        val color = ContextCompat.getColor(requireContext(), R.color.white)

        ourPieChart.setBackgroundColor(color)

        ourPieChart.setUsePercentValues(false)
        ourPieChart.getDescription().setEnabled(false)
        ourPieChart.setExtraOffsets(5f, 10f, 5f, 5f)

        ourPieChart.legend.setDrawInside(true)

        ourPieChart.setDragDecelerationFrictionCoef(0.95f)

        ourPieChart.setDrawHoleEnabled(true)
        ourPieChart.setHoleColor(Color.WHITE)

        ourPieChart.setTransparentCircleColor(Color.WHITE)
        ourPieChart.setTransparentCircleAlpha(110)

        ourPieChart.setHoleRadius(58f)
        ourPieChart.setTransparentCircleRadius(61f)

        ourPieChart.setDrawCenterText(false)

        ourPieChart.setRotationAngle(0f)

        ourPieChart.setRotationEnabled(false)
        ourPieChart.setHighlightPerTapEnabled(true)

        ourPieChart.animateY(1400, Easing.EaseInOutQuad)

        ourPieChart.legend.isEnabled = true
        ourPieChart.setEntryLabelColor(Color.WHITE)
        ourPieChart.setEntryLabelTextSize(12f)
        ourPieChart.setHoleRadius(0f)
        ourPieChart.setTransparentCircleRadius(1f)
        ourPieChart.contentDescription = "Valores em %"


        viewModel.despesasGrafico.observe(viewLifecycleOwner) { mapResponse ->
            val entries = convertToPieEntries(mapResponse)
            val dataSet = PieDataSet(entries, "Categorias")
            dataSet.setDrawIcons(true)
            dataSet.sliceSpace = 0f
            dataSet.iconsOffset = MPPointF(0f, 40f)
            dataSet.selectionShift = 5f

            dataSet.colors = colors

            val data = PieData(dataSet)
            data.setValueFormatter(PercentFormatter())
            data.setValueTextSize(15f)
            data.setValueTypeface(Typeface.DEFAULT_BOLD)
            data.setValueTextColor(Color.WHITE)

            ourPieChart.data = data
            ourPieChart.invalidate()
        }

        ourPieChart.highlightValues(null)

        ourPieChart.invalidate()

        meuAdapter = DespesaAdapter(emptyList(), requireContext(), mapColors)
        val linearLayoutManager: LinearLayoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = meuAdapter

        return view
    }

    fun convertToPieEntries(map: MutableMap<String, Double>): ArrayList<PieEntry> {
        val pieEntries = ArrayList<PieEntry>()

        for ((categoria, valor) in map) {
            val pieEntry = PieEntry(valor.toFloat(), categoria)
            pieEntries.add(pieEntry)
        }

        return pieEntries
    }

    fun ordenarDespesasPorData(despesas: List<Despesa>): List<Despesa> {
        return despesas.sortedByDescending { it.data }
    }
}

class DespesaAdapter(
    private val list: List<Despesa>,
    private val context: Context,
    private val mapColors: Map<String, Int> = mapOf()
) : RecyclerView.Adapter<DespesaAdapter.GraficoCategoriaViewHolder>() {

    inner class GraficoCategoriaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        //FUNCAO QUE PEGA OS DADOS E TRANSFORMA NO GRAFICO DE OBJETIVO
        fun bind(despesa: Despesa){
            val item = itemView.findViewById<ConstraintLayout>(R.id.clItemDespesa)
            val titulo = itemView.findViewById<TextView>(R.id.tvTitulo)
            val descricao = itemView.findViewById<TextView>(R.id.tvDescricao)
            val letra = itemView.findViewById<TextView>(R.id.tvLetra)
            val valor = itemView.findViewById<TextView>(R.id.tvValor)

            letra.backgroundTintList = ColorStateList.valueOf(mapColors[despesa.categoria] ?: R.color.pink_dashboard)

            titulo.text = despesa.nome
            descricao.text = despesa.categoria
            letra.text = despesa.nome[0].toString().uppercase(Locale.getDefault())
            valor.text = "R$${despesa.valor}"

            item.setOnClickListener{
                val intent = Intent(context, EditarDespesaActivity::class.java).apply {
                    putExtra("nome", despesa.nome)
                    putExtra("descricao", despesa.descricao)
                    putExtra("valor", despesa.valor)
                    putExtra("data", despesa.data)
                    putExtra("id", despesa.codigo)
                }
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GraficoCategoriaViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_dashboard_categoria, parent, false)
        return GraficoCategoriaViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: GraficoCategoriaViewHolder, position: Int) {
        val despesa = list[position]

        holder.bind(despesa)
    }
}