package net.fbvictorhugo.k.barreirasanitaria.ui

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import net.fbvictorhugo.k.barreirasanitaria.R
import net.fbvictorhugo.k.barreirasanitaria.data.dao.DAOFactory
import net.fbvictorhugo.k.barreirasanitaria.data.dao.IPessoaDAO
import net.fbvictorhugo.k.barreirasanitaria.data.dao.TabelasDataBase
import net.fbvictorhugo.k.barreirasanitaria.data.model.Pessoa
import net.fbvictorhugo.k.barreirasanitaria.ui.adapter.PessoasRecyclerAdapter

class PesquisaPessoasActivity : AppCompatActivity() {
    private var mRecyclerView: RecyclerView? = null
    private var mFabCadastroPessoa: FloatingActionButton? = null
    private var mPessoasRecyclerAdapter: PessoasRecyclerAdapter? = null
    private var mTxtNomeBarreira: AppCompatTextView? = null
    private var mTxtListaVazia: AppCompatTextView? = null
    private var mEdtPesquisa: TextInputEditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesquisa_pessoas)

        findViews()

        configuraActionBar(supportActionBar)
        configuraDadosTela()

        configuraRecyclerView()
        configuraClickListeners()
    }

    override fun onResume() {
        super.onResume()
        pesquisaPessoas()
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
    }

    private fun pesquisaPessoas() {
        val pessoaDAO: IPessoaDAO = DAOFactory.getDataSource(TabelasDataBase.PESSOA) as IPessoaDAO
        val pessoas: ArrayList<Pessoa> = pessoaDAO.listar()

        mPessoasRecyclerAdapter?.atualiza(pessoas)

        if (pessoas.isNotEmpty()) {
            mTxtListaVazia?.visibility = View.GONE
        }
    }

    private fun clickBotaoCadastroPessoa() {
        val intent = Intent(this, DetalhesPessoaActivity::class.java)
            .apply {
                putExtra(Constantes.EXTRA_MODO_CADASTRO, true)
                putExtra(Constantes.EXTRA_NUMERO_DOCUMENTO, mEdtPesquisa?.text.toString())
            }
        startActivity(intent)
    }

    private fun onClickItemLista(pessoa: Pessoa?) {
        val intent = Intent(this, QuestionarioActivity::class.java)
            .apply {
                putExtra(Constantes.EXTRA_ID_BARREIRA,intent.getLongExtra(Constantes.EXTRA_ID_BARREIRA, 0)                )
                putExtra(Constantes.EXTRA_NOME_BARREIRA,intent.getStringExtra(Constantes.EXTRA_NOME_BARREIRA)                )
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
