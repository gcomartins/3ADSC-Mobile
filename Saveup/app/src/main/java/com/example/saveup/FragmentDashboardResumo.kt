package com.example.saveup

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
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

class FragmentDashboardResumo : Fragment() {

    lateinit var lineList:ArrayList<Entry>
    lateinit var dataSet: LineDataSet
    lateinit var lineData: LineData

    private val retrofit = Rest.getInstance()

    private var allDespesas = listOf<Despesa>()
    private var allReceitas = listOf<Receita>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dashboard_resumo, container, false)

        getAllDespesasByIdUsuario(USUARIO.id ?: 0)
        getAllReceitasByIdUsuario(USUARIO.id ?: 0)

        val tvSaldo = view.findViewById<TextView>(R.id.tv_saldo)
        val tvValorGuardadoEsteMes = view.findViewById<TextView>(R.id.tv_valor_guardado_mes)

        tvSaldo.text = "R$ ${getSaldo()}"
        tvValorGuardadoEsteMes.text = "R$ ${getValorGuardadoEsteMes()}"

        val valoresGuardadoPorMes = getValorGuardadoPorMes()

        var etiquetas = valoresGuardadoPorMes.map { "${it.mes}/${it.ano}" }
        var listaAuxiliar = listOf(etiquetas.get(0))
        etiquetas = listaAuxiliar + etiquetas
        lineList = ArrayList()
        val entries = mutableListOf<Entry>()
        valoresGuardadoPorMes.forEach { dinheiroGuardadoNoMes ->
            entries.add(Entry(dinheiroGuardadoNoMes.mes.toFloat(), dinheiroGuardadoNoMes.valor.toFloat()))
        }

        dataSet = LineDataSet(entries, "")

        lineData = LineData(dataSet)
        R.layout.activity_dashboard_resumo
        val line_chart = view.findViewById<LineChart>(R.id.linear_chart)

        val verde_bolinha_grafico = ContextCompat.getColor(requireContext(), R.color.verde_grafico_inline)

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

    private fun getValorGuardadoEsteMes(): String {
        return "100,00"
    }

    private fun getSaldo(): String {
        return "1000,00"
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

    private fun getAllDespesasByIdUsuario(idUsuario: Int) {
        val despesaService = retrofit.create(FinancasService::class.java)
        val call = despesaService.getAllDespesasById(idUsuario)

        call.enqueue(object : Callback<List<Despesa>> {
            override fun onResponse(
                call: Call<List<Despesa>>,
                response: Response<List<Despesa>>
            ) {
                if (response.isSuccessful){
                    allDespesas = response.body() ?: listOf()
                }
            }

            override fun onFailure(call: Call<List<Despesa>>, t: Throwable) {
                Toast.makeText(requireContext(), "Erro ao carregar despesas", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun getAllReceitasByIdUsuario(idUsuario: Int) {
        val receitaService = retrofit.create(FinancasService::class.java)
        val call = receitaService.getAllReceitasById(idUsuario)

        call.enqueue(object : Callback<List<Receita>> {
            override fun onResponse(
                call: Call<List<Receita>>,
                response: Response<List<Receita>>
            ) {
                if (response.isSuccessful){
                    allReceitas = response.body() ?: listOf()
                }
            }

            override fun onFailure(call: Call<List<Receita>>, t: Throwable) {
                Toast.makeText(requireContext(), "Erro ao carregar receitas", Toast.LENGTH_SHORT).show()
            }

        })
    }
}

data class DinheiroGuardado(val valor:Double, val mes:Int, val ano:Int)