package com.example.saveup.objetivo

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
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.saveup.ObjetivoExpandidoActivity
import com.example.saveup.R
import com.example.saveup.USUARIO
import models.Objetivo

class FragmentObjetivo : Fragment() {

    lateinit var viewModel: ObjetivoCardViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var meuAdapter: ObjetivoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_objetivo, container, false)

        viewModel = ObjetivoCardViewModel()

        viewModel.getAllObjetivosByIdUsuario(USUARIO.id ?: 0)

        // Inicialize o RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView)

//        viewModel.allObjetivos.observe(viewLifecycleOwner){
//            meuAdapter = ObjetivoAdapter(MutableLiveData)
//        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager

        val meuAdapter = ObjetivoAdapter( emptyList(), requireContext(), viewModel) // Inicialmente, o adaptador está vazio
        recyclerView.adapter = meuAdapter

        viewModel.allObjetivos.observe(viewLifecycleOwner) { objetivos ->
            if(objetivos != null){
                meuAdapter.list = objetivos // Atualize os dados do adaptador com os objetivos recebidos
                meuAdapter.notifyDataSetChanged() // Notifique o adaptador sobre a mudança nos dados
            }
        }


        // Exemplo de dados
//        val dataList = listOf(
//            Objetivo("Viagem", "Viagem para o exterior", 10000.0, 5000.0),
//            Objetivo("Carro", "Carro novo", 50000.0, 10000.0),
//            Objetivo("Casa", "Casa nova", 100000.0, 20000.0),
//            Objetivo("Celular", "Celular novo", 2000.0, 1000.0),
//        )

        // Inicialize o Adapter com os dados desejados e defina-o no RecyclerView
        // O adapter que vai pegar os dados e transformar no grafico de objetivo

        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = meuAdapter

        return view
    }

}


class ObjetivoAdapter(
    var list: List<Objetivo>,
    private val context: Context,
    val viewModel: ObjetivoCardViewModel
    ) : RecyclerView.Adapter<ObjetivoAdapter.GraficoObjetivoViewHolder>() {

    inner class GraficoObjetivoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        //FUNCAO QUE PEGA OS DADOS E TRANSFORMA NO GRAFICO DE OBJETIVO
        fun bind(objetivo: Objetivo){
            val titulo = itemView.findViewById<TextView>(R.id.titulo)
            val descricao = itemView.findViewById<TextView>(R.id.descricao)
            val valorObjetivo = itemView.findViewById<TextView>(R.id.valorObjetivo)
            val grafico = itemView.findViewById<ProgressBar>(R.id.progress_circular)
            val progresso = (objetivo.valorAtual / objetivo.valor * 100).toInt()

            titulo.text = objetivo.nome
            descricao.text = objetivo.descricao
            valorObjetivo.text = "R$ ${objetivo.valor}"
            grafico.progress = progresso

            val btnDetalhes = itemView.findViewById<Button>(R.id.btnDetalhes)
            btnDetalhes.setOnClickListener {
                val objetivoExpandido = Intent(context, ObjetivoExpandidoActivity::class.java).apply {
                    putExtra("titulo", objetivo.nome)
                    putExtra("descricao", objetivo.descricao)
                    putExtra("valorObjetivo", objetivo.valor)
                    putExtra("valorAtual", objetivo.valorAtual)
                    putExtra("progresso", progresso)
                }
                context.startActivity(objetivoExpandido)
            }

            val btExcluir = itemView.findViewById<Button>(R.id.btnExcluirObjetivo)
            btExcluir.setOnClickListener{
                viewModel.deleteObjetivoByIdObjetivo(objetivo.id)
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