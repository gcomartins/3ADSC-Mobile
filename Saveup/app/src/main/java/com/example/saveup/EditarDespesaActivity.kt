package com.example.saveup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import com.example.saveup.databinding.ActivityEditarDespesaBinding
import models.Despesa
import rest.Rest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import service.FinancasService

class EditarDespesaActivity : AppCompatActivity() {
    val retrofit = Rest.getInstance()
    val service = retrofit.create(FinancasService::class.java)

    val binding by lazy { ActivityEditarDespesaBinding.inflate(layoutInflater) }

    val tvNome by lazy { binding.tvTitulo }
    val etNome by lazy { binding.editNome }
    val tvDescricao by lazy { binding.tvDescricao }
    val etDescricao by lazy { binding.editDescricao }
    val tvValor by lazy { binding.tvValor }
    val etValor by lazy { binding.editValor }
    val tvData by lazy { binding.textViewData }
    val etData by lazy { binding.editData }
    val tvCategoria by lazy { binding.tvCategoria }
    val spinnerCategoria by lazy { binding.spinnerCategoria }

    val btnSalvar by lazy { binding.btnSalvar }
    val btnExcluir by lazy { binding.btnExcluir }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val id = intent.getIntExtra("id", 0)
        val nome = intent.getStringExtra("nome")
        val valor = intent.getDoubleExtra("valor", 0.0)
        val categoria = intent.getStringExtra("categoria")
        val data = intent.getStringExtra("data")
        val descricao = intent.getStringExtra("descricao")
        val parcelas = intent.getIntExtra("parcelas", 0)

        val categorias = resources.getStringArray(R.array.categorias_principais)

        val index = categorias.indexOf(categoria)
        if (index != -1) {
            spinnerCategoria.setSelection(index)
        }

        etNome.text = Editable.Factory.getInstance().newEditable(nome)
        etValor.text = Editable.Factory.getInstance().newEditable(valor.toString())
        etDescricao.text = Editable.Factory.getInstance().newEditable(descricao)
        etData.text = Editable.Factory.getInstance().newEditable(data)

        btnSalvar.setOnClickListener {
            atualizarDespesaById(id)
            finish()
        }
        btnExcluir.setOnClickListener {
            deleteDespesaById(id)
            finish()
        }
    }

    private fun deleteDespesaById(idDespesa: Int) {
        service.deletaDespesa(USUARIO.id!!, idDespesa).enqueue(object : Callback<Despesa> {
            override fun onResponse(call: Call<Despesa>, response: Response<Despesa>) {
                if (response.isSuccessful) {
                    val despesa = response.body()
                    if (despesa != null) {
                        Toast.makeText(
                            this@EditarDespesaActivity,
                            "Despesa deletada com sucesso!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            override fun onFailure(call: Call<Despesa>, t: Throwable) {
                Toast.makeText(
                    this@EditarDespesaActivity,
                    "Erro ao deletar despesa!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun atualizarDespesaById(idDespesa: Int) {
        val despesa = Despesa(
            codigo = idDespesa,
            nome = etNome.text.toString(),
            valor = etValor.text.toString().toDouble(),
            categoria = spinnerCategoria.selectedItem.toString(),
            data = etData.text.toString(),
            descricao = etDescricao.text.toString(),
            qtdParcelas = 1,
            fkUsuario = USUARIO.id!!,
        )

        service.atualizaDespesa(USUARIO.id!!, idDespesa, despesa).enqueue(object : Callback<Despesa> {
            override fun onResponse(call: Call<Despesa>, response: Response<Despesa>) {
                if (response.isSuccessful) {
                    val despesa = response.body()
                    if (despesa != null) {
                        Toast.makeText(
                            this@EditarDespesaActivity,
                            "Despesa atualizada com sucesso!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            override fun onFailure(call: Call<Despesa>, t: Throwable) {
                Toast.makeText(
                    this@EditarDespesaActivity,
                    "Erro ao atualizar despesa!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}