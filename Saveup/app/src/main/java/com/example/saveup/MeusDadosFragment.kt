package com.example.saveup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MeusDadosFragment : Fragment() {

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val function = (activity as TelaPrincipalActivity)


        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_meus_dados, container, false)

        val button = view.findViewById<Button>(R.id.button)
        button.setOnClickListener {
            logoff(it)
        }

        val recyclerView: RecyclerView = view.findViewById<RecyclerView>(R.id.rvMeusDados)
        val dataList = listOf(
            DadoDoUsuario("Nome", USUARIO.nome ?: "-"),
            DadoDoUsuario("E-mail", USUARIO.email ?: "-"),
            DadoDoUsuario("Senha", USUARIO.senha ?: "-", true),
            DadoDoUsuario("Data de Nascimento",USUARIO.dataNascimento ?: "-")
        )
        val linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager
        val meuAdapter = MeusDadosAdapter(requireContext(), dataList)
        recyclerView.adapter = meuAdapter

        return view
    }

    private fun logoff(view: View) {
        emptyUsuario()
        goToLogin()
    }

    private fun goToLogin(){
        val intent = Intent(requireContext(), Login::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
    private fun emptyUsuario() {
        USUARIO.id = null
        USUARIO.nome = null
        USUARIO.senha = null
        USUARIO.dataNascimento = null
    }
}



    private data class DadoDoUsuario(
    val atributo: String,
    val valor: String,
    val isObscured: Boolean = false
)

private class MeusDadosAdapter(
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
                    putExtra("valor", dado.valor)
                    putExtra("isObscured", dado.isObscured)
                }
                context.startActivity(intent)
            }

            if(dado.isObscured) campo.inputType = 129 // 129 = password
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

