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
import java.util.Calendar

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
    val datePicker by lazy { binding.datePicker }
    val tvCategoria by lazy { binding.tvCategoria }
    val spinnerCategoria by lazy { binding.spinnerCategoria }

    val btnSalvar by lazy { binding.btnSalvar }
    val btnExcluir by lazy { binding.btnExcluir }

    var dataSelecionada = ""

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

        // Defina a data inicial
        val initialYear = data?.substring(0, 4)?.toInt()
        val initialMonth = data?.substring(5, 7)?.toInt()?.minus(1)
        val initialDay = data?.substring(8, 10)?.toInt()

        // Data de hoje
        val today = Calendar.getInstance()
        val todayYear = today.get(Calendar.YEAR)
        val todayMonth = today.get(Calendar.MONTH)
        val todayDay = today.get(Calendar.DAY_OF_MONTH)

        datePicker.init(datePicker.year, datePicker.month, datePicker.dayOfMonth) { view, year, monthOfYear, dayOfMonth ->
            // Aqui você pode capturar a data selecionada e fazer o que precisar com ela

            // Formate os números abaixo de 10 com zero à esquerda
            val dayFormatted = String.format("%02d", dayOfMonth)
            val monthFormatted = String.format("%02d", monthOfYear + 1)
            val yearFormatted = String.format("%02d", year)

            dataSelecionada = "$dayFormatted/$monthFormatted/$yearFormatted"
        }

        datePicker.updateDate(
            initialYear ?: todayYear,
            initialMonth ?: todayMonth,
            initialDay ?: todayDay
        )

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
            data = formatarData(),
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

    private fun formatarData(): String {
        val partes = dataSelecionada.split("/")
        val dataFormatada = "${partes[2]}-${partes[1]}-${partes[0]}"
        return dataFormatada

    }
}