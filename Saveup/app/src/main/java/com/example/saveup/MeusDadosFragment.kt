package com.example.saveup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MeusDadosFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_meus_dados, container, false)

        val recyclerView: RecyclerView = view.findViewById<RecyclerView>(R.id.rvMeusDados)
        val dataList = listOf(
            DadoDoUsuario("Nome", "João da Silva"),
            DadoDoUsuario("E-mail", "joao.silva@email.com"),
            DadoDoUsuario("Senha", "Senha@123"),
            DadoDoUsuario("Telefone", "(11) 99999-9999"),
            DadoDoUsuario("CPF", "999.999.999-99"),
            DadoDoUsuario("Data de Nascimento", "01/01/2000")
        )
        val linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager
        val meuAdapter = MeusDadosAdapter(requireContext(), dataList)
        recyclerView.adapter = meuAdapter

        return view
    }
}

data class DadoDoUsuario(
    val atributo: String,
    val valor: String
)

class MeusDadosAdapter(
    private val context: Context,
    private val list: List<DadoDoUsuario>
    ): RecyclerView.Adapter<MeusDadosAdapter.MeusDadosViewHolder>(){

    inner class MeusDadosViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(dado: DadoDoUsuario){
            val label = itemView.findViewById<TextView>(R.id.tvLabel)
            val campo = itemView.findViewById<TextView>(R.id.etFormField)
            val editIcon = itemView.findViewById<ImageView>(R.id.ivEditIcon)
            label.text = dado.atributo
            campo.text = dado.valor
            editIcon.setOnClickListener {
                val intent = Intent(context, EditarCampoActivity::class.java).apply {
                    putExtra("atributo", dado.atributo)
                }
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeusDadosViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.texto_form_field_meus_dados, parent, false)
        return MeusDadosViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MeusDadosViewHolder, position: Int) {
        holder.bind(list[position])
    }

}
