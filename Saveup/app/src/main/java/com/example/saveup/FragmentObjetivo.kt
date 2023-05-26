package com.example.saveup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentObjetivo.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentObjetivo : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var meuAdapter: ObjetivoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_objetivo, container, false)
        // Inicialize o RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView)
//        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Inicialize o Adapter com os dados desejados e defina-o no RecyclerView
        val dataList = listOf(Objetivo("Titulo", "Descricao"))// Exemplo de dados
        meuAdapter = ObjetivoAdapter(dataList)
        recyclerView.adapter = meuAdapter

        // Inflate the layout for this fragment
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentObjetivo.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentObjetivo().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

data class Objetivo(
    val titulo: String,
    val descricao: String,
)

class ObjetivoAdapter(private val list: List<Objetivo>) : RecyclerView.Adapter<ObjetivoAdapter.GraficoObjetivoViewHolder>() {

    inner class GraficoObjetivoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(objetivo: Objetivo){
            itemView.findViewById<TextView>(R.id.titulo).text = objetivo.titulo
            itemView.findViewById<TextView>(R.id.descricao).text = objetivo.descricao
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