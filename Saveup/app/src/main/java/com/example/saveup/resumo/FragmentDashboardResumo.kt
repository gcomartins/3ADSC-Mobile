package com.example.saveup.resumo

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.saveup.R
import com.example.saveup.USUARIO
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import models.Despesa
import models.Receita
import rest.Rest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import service.FinancasService
import java.util.Calendar

class FragmentDashboardResumo : Fragment() {

    lateinit var viewModel: DashboardResumoViewModel



    lateinit var lineList:ArrayList<Entry>
    lateinit var dataSet: LineDataSet
    lateinit var lineData: LineData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dashboard_resumo, container, false)

        viewModel = DashboardResumoViewModel()

        viewModel.getAllDespesasByIdUsuario(USUARIO.id ?: 0)
        viewModel.getAllReceitasByIdUsuario(USUARIO.id ?: 0)

        viewModel.allReceitas.observe(viewLifecycleOwner){
            calculaSaldo()
            calculaSaldoDoMes()
            setaValoresGuardadosPorMes()
        }

        viewModel.allDespesas.observe(viewLifecycleOwner){
            calculaSaldo()
            calculaSaldoDoMes()
//            setaValoresGuardadosPorMes()
        }

        viewModel.saldoPorMes.observe(viewLifecycleOwner){
            setaGrafico(view)
        }

        val valoresGuardadoPorMes = getValorGuardadoPorMes()

        var etiquetas = valoresGuardadoPorMes.map { "${it.mes}/${it.ano}" }
        var listaAuxiliar = listOf(etiquetas.get(0))
        etiquetas = listaAuxiliar + etiquetas
        lineList = ArrayList()
        val entries = mutableListOf<Entry>()
        valoresGuardadoPorMes.forEach { dinheiroGuardadoNoMes ->
            entries.add(Entry(dinheiroGuardadoNoMes.mes.toFloat(), dinheiroGuardadoNoMes.valor.toFloat()))
        }
//
        dataSet = LineDataSet(entries, "")
//
        lineData = LineData(dataSet)
        R.layout.activity_dashboard_resumo
        val line_chart = view.findViewById<LineChart>(R.id.linear_chart)

        val verde_bolinha_grafico = ContextCompat.getColor(requireContext(),
            R.color.verde_grafico_inline
        )
//
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
//            title = "Dinheiro guardado"
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
        return view
    }

    fun getValorGuardadoPorMes():List<DinheiroGuardado>{
        return listOf(
            DinheiroGuardado(100.0, 1, 2022),
            DinheiroGuardado(200.0, 2, 2022),
            DinheiroGuardado(300.0, 3, 2022),
            DinheiroGuardado(500.0, 4, 2022),
            DinheiroGuardado(200.0, 5, 2022),
            DinheiroGuardado(300.0, 6, 2022),
            DinheiroGuardado(250.0, 7, 2022),
            DinheiroGuardado(0.0, 8, 2022),
        )
    }

    private fun calculaSaldo(){
        var saldo = 0.0
        viewModel.allReceitas.value?.forEach {
            saldo += it.valor
        }
        viewModel.allDespesas.value?.forEach {
            saldo -= it.valor
        }
        val tvSaldo = requireView().findViewById<TextView>(R.id.tv_saldo)
        tvSaldo.text = "$saldo"
    }

    private fun calculaSaldoDoMes(){
        var saldo = 0.0
        val mesAtual = Calendar.getInstance().get(Calendar.MONTH) + 1
        val anoAtual = Calendar.getInstance().get(Calendar.YEAR)
        viewModel.allReceitas.value?.forEach {
            val mes = it.data.subSequence(5,6).toString().toInt()
            val ano = it.data.subSequence(0,3).toString().toInt()
            if(mes == mesAtual && ano == anoAtual){
                saldo += it.valor
            }
        }
        viewModel.allDespesas.value?.forEach {
            val mes = it.data.subSequence(5,6).toString().toInt()
            val ano = it.data.subSequence(0,3).toString().toInt()
            if(mes == mesAtual && ano == anoAtual){
                saldo -= it.valor
            }
        }
        val tvGuardadoMes = requireView().findViewById<TextView>(R.id.tv_valor_guardado_mes)
        tvGuardadoMes.text = "$saldo"
    }

    private fun setaValoresGuardadosPorMes(){
        val despesasPorMesEAno = viewModel.allDespesas.value?.groupBy { "${it.data.subSequence(5,6)}${it.data.subSequence(0,3)}" }
        val receitasPorMesEAno = viewModel.allReceitas.value?.groupBy { "${it.data.subSequence(5,6)}${it.data.subSequence(0,3)}" }
    }

    private fun setaGrafico(view: View){
        val saldoPorMes = viewModel.saldoPorMes.value

    }

}

data class DinheiroGuardado(val valor:Double, val mes:Int, val ano:Int)