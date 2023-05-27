package com.example.saveup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MeusDadosFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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
        val meuAdapter = MeusDadosAdapter(dataList)
        recyclerView.adapter = meuAdapter

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) = MeusDadosFragment()
    }

}

data class Usuario(
    val nome: String,
    val email: String,
    val senha: String,
    val telefone: String,
    val cpf: String,
    val dataNascimento: String,
    )

data class DadoDoUsuario(
    val atributo: String,
    val valor: String
)

class MeusDadosAdapter(private val list: List<DadoDoUsuario>): RecyclerView.Adapter<MeusDadosAdapter.MeusDadosViewHolder>(){

    inner class MeusDadosViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(dado: DadoDoUsuario){
            itemView.findViewById<android.widget.TextView>(R.id.tvLabel).text = dado.atributo
            itemView.findViewById<android.widget.TextView>(R.id.etFormField).text = dado.valor
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