package net.fbvictorhugo.k.barreirasanitaria.ui

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_pesquisa_pessoas.*
import net.fbvictorhugo.k.barreirasanitaria.R
import net.fbvictorhugo.k.barreirasanitaria.data.dao.DAOFactory
import net.fbvictorhugo.k.barreirasanitaria.data.dao.PessoaDAO
import net.fbvictorhugo.k.barreirasanitaria.data.dao.TabelasDataBase
import net.fbvictorhugo.k.barreirasanitaria.data.model.Pessoa
import net.fbvictorhugo.k.barreirasanitaria.extension.maiorQue
import net.fbvictorhugo.k.barreirasanitaria.ui.adapter.PessoasRecyclerAdapter
import net.fbvictorhugo.k.barreirasanitaria.utils.Constantes
import net.fbvictorhugo.k.barreirasanitaria.utils.Constantes.EXTRA_MODO_CADASTRO
import net.fbvictorhugo.k.barreirasanitaria.utils.Constantes.RESULT_TELA_DETALHES
import net.fbvictorhugo.k.barreirasanitaria.utils.UtilDialog

class PesquisaPessoasActivity : AppCompatActivity() {

    private var _pessoasRecyclerAdapter: PessoasRecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesquisa_pessoas)
        configuraActionBar(supportActionBar)
        configuraDadosTela()

        configuraRecyclerView()
        configuraClickListeners()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == RESULT_TELA_DETALHES) {
                val numeroDocumento = data?.getLongExtra(Constantes.EXTRA_NUMERO_DOCUMENTO, 0)
                if (numeroDocumento?.maiorQue(0)!!) {
                    pesquisa_pessoas_edt_pesquisar.setText(numeroDocumento.toString())
                }
            }
            pesquisa_pessoas_btn_pesquisar.callOnClick()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun configuraActionBar(supportActionBar: ActionBar?) {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    private fun configuraDadosTela() {
        pesquisa_pessoas_txt_nome_barreira.text =
            intent.getStringExtra(Constantes.EXTRA_NOME_BARREIRA)
    }

    private fun configuraRecyclerView() {
        _pessoasRecyclerAdapter = PessoasRecyclerAdapter(this)

        pesquisa_pessoas_recyclerview.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = _pessoasRecyclerAdapter
        }
    }

    private fun configuraClickListeners() {
        pesquisa_pessoas_fab.setOnClickListener { clickBotaoCadastroPessoa() }

        _pessoasRecyclerAdapter?.onItemClickListener =
            object : PessoasRecyclerAdapter.OnItemClickListener {
                override fun onClick(pessoa: Pessoa) {
                    onClickItemLista(pessoa)
                }

                override fun onLongClick(pessoa: Pessoa) {
                    onLongClickClickItemLista(pessoa)
                }
            }

        pesquisa_pessoas_btn_pesquisar.setOnClickListener { pesquisaPessoas() }
    }

    private fun pesquisaPessoas() {
        var termoPesquisa = ""
        try {
            termoPesquisa = pesquisa_pessoas_edt_pesquisar.text.toString()
            val pessoaDAO: PessoaDAO =
                DAOFactory.getDataSource(TabelasDataBase.PESSOA) as PessoaDAO
            if (termoPesquisa.isEmpty()) {
                _pessoasRecyclerAdapter?.atualiza(ArrayList())
                pesquisa_pessoas_txt_lista_vazia.visibility = View.VISIBLE
            } else {
                val documentoPesquisa = termoPesquisa.toLong()
                val pessoas = pessoaDAO.pesquisarPorDocumento(documentoPesquisa)
                _pessoasRecyclerAdapter?.atualiza(pessoas)

                if (pessoas.size > 0) {
                    pesquisa_pessoas_txt_lista_vazia.visibility = View.GONE
                }
            }

        } catch (e: Exception) {
            val mensagem = String.format(
                resources.getString(R.string.msg_erro_termo_pesquisa_),
                termoPesquisa
            )
            UtilDialog.showDialogOK(this, mensagem)
            e.printStackTrace()
        }
    }

    private fun clickBotaoCadastroPessoa() {
        val intent = Intent(this, DetalhesPessoaActivity::class.java)
            .apply {
                putExtra(Constantes.EXTRA_MODO_CADASTRO, true)
                putExtra(
                    Constantes.EXTRA_NUMERO_DOCUMENTO,
                    pesquisa_pessoas_edt_pesquisar.text.toString()
                )
            }
        startActivityForResult(intent, RESULT_TELA_DETALHES)
    }

    private fun onClickItemLista(pessoa: Pessoa) {
        val intent = Intent(this, QuestionarioActivity::class.java)
            .apply {
                putExtra(
                    Constantes.EXTRA_ID_BARREIRA,
                    intent.getLongExtra(Constantes.EXTRA_ID_BARREIRA, 0)
                )
                putExtra(
                    Constantes.EXTRA_NOME_BARREIRA,
                    intent.getStringExtra(Constantes.EXTRA_NOME_BARREIRA)
                )
                putExtra(Constantes.EXTRA_ID_PESSOA, pessoa.id)
                putExtra(Constantes.EXTRA_NOME_PESSOA, pessoa.nome)
            }
        startActivity(intent)
    }

    private fun onLongClickClickItemLista(pessoa: Pessoa) {
        val messagem = String.format(
            resources.getString(R.string.msg_deseja_editar_informacoes_),
            pessoa.nome
        )
        UtilDialog.showDialogSimNao(this, messagem,
            DialogInterface.OnClickListener { dialogInterface, i ->

                val intent = Intent(this, DetalhesPessoaActivity::class.java)
                    .apply {
                        putExtra(Constantes.EXTRA_ID_PESSOA, pessoa.id)
                        putExtra(EXTRA_MODO_CADASTRO, false)
                    }
                startActivityForResult(intent, RESULT_TELA_DETALHES)

            })
    }
}
