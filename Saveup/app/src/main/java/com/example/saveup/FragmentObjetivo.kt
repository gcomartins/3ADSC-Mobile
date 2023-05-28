package com.example.saveup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FragmentObjetivo : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var meuAdapter: ObjetivoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_objetivo, container, false)
        // Inicialize o RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView)


        // Exemplo de dados
        // E NECESSARIO REVER O LAYOUT POIS NAO ESTA DINAMICO
        // TA COM A QUANTIDADE DE GRAFICOS MOCADA
        val dataList = listOf(
            Objetivo("Viagem", "Viagem para o exterior", 10000.0, 5000.0),
            Objetivo("Carro", "Carro novo", 50000.0, 10000.0),
            Objetivo("Casa", "Casa nova", 100000.0, 20000.0),
            Objetivo("Celular", "Celular novo", 2000.0, 1000.0),
        )

        // Inicialize o Adapter com os dados desejados e defina-o no RecyclerView
        // O adapter que vai pegar os dados e transformar no grafico de objetivo
        meuAdapter = ObjetivoAdapter(dataList, requireContext())
        recyclerView.adapter = meuAdapter

        return view
    }

}

data class Objetivo(
    val titulo: String,
    val descricao: String,
    val valorObjetivo: Double,
    val valorAtual: Double,
)

class ObjetivoAdapter(
    private val list: List<Objetivo>,
    private val context: Context
    ) : RecyclerView.Adapter<ObjetivoAdapter.GraficoObjetivoViewHolder>() {

    inner class GraficoObjetivoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        //FUNCAO QUE PEGA OS DADOS E TRANSFORMA NO GRAFICO DE OBJETIVO
        fun bind(objetivo: Objetivo){
            val titulo = itemView.findViewById<TextView>(R.id.titulo)
            val descricao = itemView.findViewById<TextView>(R.id.descricao)
            val valorObjetivo = itemView.findViewById<TextView>(R.id.valorObjetivo)
            val grafico = itemView.findViewById<ProgressBar>(R.id.progress_circular)
            val progresso = (objetivo.valorAtual / objetivo.valorObjetivo * 100).toInt()

            titulo.text = objetivo.titulo
            descricao.text = objetivo.descricao
            valorObjetivo.text = "R$ ${objetivo.valorObjetivo}"
            grafico.progress = progresso

            val btnDetalhes = itemView.findViewById<Button>(R.id.btnDetalhes)
            btnDetalhes.setOnClickListener {
                val objetivoExpandido = Intent(context, ObjetivoExpandidoActivity::class.java).apply {
                    putExtra("titulo", objetivo.titulo)
                    putExtra("descricao", objetivo.descricao)
                    putExtra("valorObjetivo", objetivo.valorObjetivo)
                    putExtra("valorAtual", objetivo.valorAtual)
                    putExtra("progresso", progresso)
                }
                context.startActivity(objetivoExpandido)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GraficoObjetivoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_deposito_objetivo, parent, false)
        return GraficoObjetivoViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: GraficoObjetivoViewHolder, position: Int) {
        val objetivo = list[position]

        holder.bind(objetivo)
    }
}