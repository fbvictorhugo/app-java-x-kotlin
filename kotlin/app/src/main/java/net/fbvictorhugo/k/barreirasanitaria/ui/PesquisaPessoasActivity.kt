package net.fbvictorhugo.k.barreirasanitaria.ui

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import net.fbvictorhugo.k.barreirasanitaria.R
import net.fbvictorhugo.k.barreirasanitaria.data.dao.DAOFactory
import net.fbvictorhugo.k.barreirasanitaria.data.dao.PessoaDAO
import net.fbvictorhugo.k.barreirasanitaria.data.dao.TabelasDataBase
import net.fbvictorhugo.k.barreirasanitaria.data.model.Pessoa
import net.fbvictorhugo.k.barreirasanitaria.extension.maiorQue
import net.fbvictorhugo.k.barreirasanitaria.ui.adapter.PessoasRecyclerAdapter

class PesquisaPessoasActivity : AppCompatActivity() {
    private val RESULT_CADASTRO = 999

    private var mRecyclerView: RecyclerView? = null
    private var mFabCadastroPessoa: FloatingActionButton? = null
    private var mPessoasRecyclerAdapter: PessoasRecyclerAdapter? = null
    private var mTxtNomeBarreira: AppCompatTextView? = null
    private var mTxtListaVazia: AppCompatTextView? = null
    private var mEdtPesquisa: TextInputEditText? = null
    private var mBtnPesquisar: AppCompatImageButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesquisa_pessoas)

        findViews()

        configuraActionBar(supportActionBar)
        configuraDadosTela()

        configuraRecyclerView()
        configuraClickListeners()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == RESULT_CADASTRO) {
            val numeroDocumento = data?.getLongExtra(Constantes.EXTRA_NUMERO_DOCUMENTO, 0)
            if (numeroDocumento?.maiorQue(0)!!) {
                mEdtPesquisa?.setText(numeroDocumento.toString())
                mBtnPesquisar?.callOnClick()
            }
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

    private fun findViews() {
        mRecyclerView = findViewById(R.id.pesquisa_pessoas_recyclerview)
        mFabCadastroPessoa = findViewById(R.id.pesquisa_pessoas_fab)
        mTxtNomeBarreira = findViewById(R.id.pesquisa_pessoas_txt_nome_barreira)
        mTxtListaVazia = findViewById(R.id.pesquisa_pessoas_txt_lista_vazia)
        mEdtPesquisa = findViewById(R.id.pesquisa_pessoas_edt_pesquisar)
        mBtnPesquisar = findViewById(R.id.pesquisa_pessoas_btn_pesquisar)
    }

    private fun configuraActionBar(supportActionBar: ActionBar?) {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    private fun configuraDadosTela() {
        mTxtNomeBarreira?.text = intent.getStringExtra(Constantes.EXTRA_NOME_BARREIRA)
    }

    private fun configuraRecyclerView() {
        mPessoasRecyclerAdapter = PessoasRecyclerAdapter()

        mRecyclerView?.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = mPessoasRecyclerAdapter
        }
    }

    private fun configuraClickListeners() {
        mFabCadastroPessoa?.setOnClickListener { clickBotaoCadastroPessoa() }

        mPessoasRecyclerAdapter?.onItemClickListener =
            object : PessoasRecyclerAdapter.OnItemClickListener {
                override fun onClick(pessoa: Pessoa?) {
                    onClickItemLista(pessoa)
                }

                override fun onLongClick(pessoa: Pessoa?) {
                    onLongClickClickItemLista(pessoa)
                }
            }

        mBtnPesquisar?.setOnClickListener { pesquisaPessoas() }
    }

    private fun pesquisaPessoas() {
        var termoPesquisa = "";
        try {
            termoPesquisa = mEdtPesquisa?.text.toString()
            val pessoaDAO: PessoaDAO =
                DAOFactory.getDataSource(TabelasDataBase.PESSOA) as PessoaDAO
            if (termoPesquisa.isEmpty()) {
                mPessoasRecyclerAdapter?.atualiza(ArrayList())
                mTxtListaVazia?.visibility = View.VISIBLE
            } else {
                val documentoPesquisa = termoPesquisa.toLong()
                val pessoas = pessoaDAO.pesquisar(documentoPesquisa)
                mPessoasRecyclerAdapter?.atualiza(pessoas)

                if (pessoas.size > 0) {
                    mTxtListaVazia?.visibility = View.GONE
                }
            }

        } catch (e: Exception) {
            val mensagem = String.format(
                resources.getString(R.string.msg_erro_termo_pesquisa_),
                termoPesquisa
            )
            UtilDialog.showDialogAlerta(this, mensagem)
            e.printStackTrace()
        }
    }

    private fun clickBotaoCadastroPessoa() {
        val intent = Intent(this, DetalhesPessoaActivity::class.java)
            .apply {
                putExtra(Constantes.EXTRA_MODO_CADASTRO, true)
                putExtra(Constantes.EXTRA_NUMERO_DOCUMENTO, mEdtPesquisa?.text.toString())
            }
        startActivityForResult(intent, RESULT_CADASTRO)
    }

    private fun onClickItemLista(pessoa: Pessoa?) {
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
                putExtra(Constantes.EXTRA_ID_PESSOA, pessoa?.id)
                putExtra(Constantes.EXTRA_NOME_PESSOA, pessoa?.nome)
            }
        startActivity(intent)
    }

    private fun onLongClickClickItemLista(pessoa: Pessoa?) {
        val messagem = String.format(
            resources.getString(R.string.msg_deseja_editar_informacoes_),
            pessoa?.nome
        )

        UtilDialog.showDialogSimNao(this, messagem,
            DialogInterface.OnClickListener { dialogInterface, i -> //TODO não implementado
                UtilDialog.showToast(baseContext, "Não implementado.")
            })
    }
}
