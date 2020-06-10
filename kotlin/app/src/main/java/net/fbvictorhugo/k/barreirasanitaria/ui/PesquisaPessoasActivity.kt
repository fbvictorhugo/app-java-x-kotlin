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
import net.fbvictorhugo.k.barreirasanitaria.utils.Constantes.RESULT_CADASTRO
import net.fbvictorhugo.k.barreirasanitaria.ui.adapter.PessoasRecyclerAdapter
import net.fbvictorhugo.k.barreirasanitaria.utils.Constantes
import net.fbvictorhugo.k.barreirasanitaria.utils.UtilDialog

class PesquisaPessoasActivity : AppCompatActivity() {

    private var _recyclerView: RecyclerView? = null
    private var _fabCadastroPessoa: FloatingActionButton? = null
    private var _pessoasRecyclerAdapter: PessoasRecyclerAdapter? = null
    private var _txtNomeBarreira: AppCompatTextView? = null
    private var _txtListaVazia: AppCompatTextView? = null
    private var _edtPesquisa: TextInputEditText? = null
    private var _btnPesquisar: AppCompatImageButton? = null

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
                _edtPesquisa?.setText(numeroDocumento.toString())
                _btnPesquisar?.callOnClick()
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
        _recyclerView = findViewById(R.id.pesquisa_pessoas_recyclerview)
        _fabCadastroPessoa = findViewById(R.id.pesquisa_pessoas_fab)
        _txtNomeBarreira = findViewById(R.id.pesquisa_pessoas_txt_nome_barreira)
        _txtListaVazia = findViewById(R.id.pesquisa_pessoas_txt_lista_vazia)
        _edtPesquisa = findViewById(R.id.pesquisa_pessoas_edt_pesquisar)
        _btnPesquisar = findViewById(R.id.pesquisa_pessoas_btn_pesquisar)
    }

    private fun configuraActionBar(supportActionBar: ActionBar?) {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    private fun configuraDadosTela() {
        _txtNomeBarreira?.text = intent.getStringExtra(Constantes.EXTRA_NOME_BARREIRA)
    }

    private fun configuraRecyclerView() {
        _pessoasRecyclerAdapter = PessoasRecyclerAdapter()

        _recyclerView?.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = _pessoasRecyclerAdapter
        }
    }

    private fun configuraClickListeners() {
        _fabCadastroPessoa?.setOnClickListener { clickBotaoCadastroPessoa() }

        _pessoasRecyclerAdapter?.onItemClickListener =
            object : PessoasRecyclerAdapter.OnItemClickListener {
                override fun onClick(pessoa: Pessoa?) {
                    onClickItemLista(pessoa)
                }

                override fun onLongClick(pessoa: Pessoa?) {
                    onLongClickClickItemLista(pessoa)
                }
            }

        _btnPesquisar?.setOnClickListener { pesquisaPessoas() }
    }

    private fun pesquisaPessoas() {
        var termoPesquisa = "";
        try {
            termoPesquisa = _edtPesquisa?.text.toString()
            val pessoaDAO: PessoaDAO =
                DAOFactory.getDataSource(TabelasDataBase.PESSOA) as PessoaDAO
            if (termoPesquisa.isEmpty()) {
                _pessoasRecyclerAdapter?.atualiza(ArrayList())
                _txtListaVazia?.visibility = View.VISIBLE
            } else {
                val documentoPesquisa = termoPesquisa.toLong()
                val pessoas = pessoaDAO.pesquisar(documentoPesquisa)
                _pessoasRecyclerAdapter?.atualiza(pessoas)

                if (pessoas.size > 0) {
                    _txtListaVazia?.visibility = View.GONE
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
                putExtra(Constantes.EXTRA_NUMERO_DOCUMENTO, _edtPesquisa?.text.toString())
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
            DialogInterface.OnClickListener { dialogInterface, i ->
                //TODO não implementado
                UtilDialog.showToast(baseContext, "Não implementado.")
            })
    }
}
