package com.example.saveup.dashboard

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        viewModel.despesasList.observe(viewLifecycleOwner){
            val dataList = it
            meuAdapter = DespesaAdapter(ordenarDespesasPorData(dataList), requireContext())
            recyclerView.adapter = meuAdapter
        }

        recyclerView = view.findViewById(R.id.recyclerView)
        ourPieChart = view.findViewById(R.id.pieChartt)

        val color = ContextCompat.getColor(requireContext(), R.color.white)

        ourPieChart.setBackgroundColor(color)

        ourPieChart.setUsePercentValues(true)
        ourPieChart.getDescription().setEnabled(false)
        ourPieChart.setExtraOffsets(5f, 10f, 5f, 5f)

        ourPieChart.setDragDecelerationFrictionCoef(0.95f)

        ourPieChart.setDrawHoleEnabled(true)
        ourPieChart.setHoleColor(Color.WHITE)

        ourPieChart.setTransparentCircleColor(Color.WHITE)
        ourPieChart.setTransparentCircleAlpha(110)

        ourPieChart.setHoleRadius(58f)
        ourPieChart.setTransparentCircleRadius(61f)

        ourPieChart.setDrawCenterText(true)

        ourPieChart.setRotationAngle(0f)

        ourPieChart.setRotationEnabled(true)
        ourPieChart.setHighlightPerTapEnabled(true)

        ourPieChart.animateY(1400, Easing.EaseInOutQuad)

        ourPieChart.legend.isEnabled = false
        ourPieChart.setEntryLabelColor(Color.WHITE)
        ourPieChart.setEntryLabelTextSize(12f)
        ourPieChart.setHoleRadius(1f)
        ourPieChart.setTransparentCircleRadius(1f)

        val entries: ArrayList<PieEntry> = ArrayList()
        entries.add(PieEntry(25f))
        entries.add(PieEntry(30f))
        entries.add(PieEntry(20f))
        entries.add(PieEntry(30f))

        val dataSet = PieDataSet(entries, "teste")
        dataSet.setDrawIcons(false)

        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 5f
        dataSet.sliceSpace = 2f

        val colors: ArrayList<Int> = ArrayList()
        colors.add(resources.getColor(R.color.yellow_dahsboard))
        colors.add(resources.getColor(R.color.pink_dashboard))
        colors.add(resources.getColor(R.color.blue_bold_dashboard))
        colors.add(resources.getColor(R.color.orange_dashboard))
        colors.add(resources.getColor(R.color.teal_200))

        dataSet.colors = colors

        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(15f)
        data.setValueTypeface(Typeface.DEFAULT_BOLD)
        data.setValueTextColor(Color.WHITE)
        ourPieChart.setData(data)

        ourPieChart.highlightValues(null)

        ourPieChart.invalidate()

//        val dataList = listOf(
//            Categoria("Teste", "A", "20.0", "testeeee"),
//            Categoria("Teste", "B", "20.0", "testeeee"),
//            Categoria("Teste", "C", "20.0", "testeeee"),
//        )

        meuAdapter = DespesaAdapter(emptyList(), requireContext())
        val linearLayoutManager: LinearLayoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = meuAdapter

        return view
    }

    fun ordenarDespesasPorData(despesas: List<Despesa>): List<Despesa> {
        return despesas.sortedByDescending { it.data }
    }}

class DespesaAdapter(
    private val list: List<Despesa>,
    private val context: Context
) : RecyclerView.Adapter<DespesaAdapter.GraficoCategoriaViewHolder>() {

    inner class GraficoCategoriaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        //FUNCAO QUE PEGA OS DADOS E TRANSFORMA NO GRAFICO DE OBJETIVO
        fun bind(despesa: Despesa){
            val titulo = itemView.findViewById<TextView>(R.id.tvTitulo)
            val descricao = itemView.findViewById<TextView>(R.id.tvDescricao)
            val letra = itemView.findViewById<TextView>(R.id.tvLetra)
            val valor = itemView.findViewById<TextView>(R.id.tvValor)

            titulo.text = despesa.nome
            descricao.text = despesa.descricao
            letra.text = despesa.nome[0].toString().uppercase(Locale.getDefault())
            valor.text = "R$${despesa.valor}"
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