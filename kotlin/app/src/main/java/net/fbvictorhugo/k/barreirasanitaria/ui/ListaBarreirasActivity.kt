package net.fbvictorhugo.k.barreirasanitaria.ui

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import net.fbvictorhugo.k.barreirasanitaria.R
import net.fbvictorhugo.k.barreirasanitaria.data.dao.DAOFactory
import net.fbvictorhugo.k.barreirasanitaria.data.dao.BarreiraSanitariaDAO
import net.fbvictorhugo.k.barreirasanitaria.data.dao.TabelasDataBase
import net.fbvictorhugo.k.barreirasanitaria.data.model.BarreiraSanitaria
import net.fbvictorhugo.k.barreirasanitaria.ui.adapter.BarreirasRecyclerAdapter

class ListaBarreirasActivity : AppCompatActivity() {

    private var mRecyclerView: RecyclerView? = null
    private var mBarreirasRecyclerAdapter: BarreirasRecyclerAdapter? = null
    private var mFabNovaBarreira: FloatingActionButton? = null
    private var mTxtListaVazia: AppCompatTextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barreiras)

        findViews()
        configuraActionBar(supportActionBar)
        configuraRecyclerView()
        configuraClickListeners()
    }

    override fun onResume() {
        super.onResume()

        pesquisaBarreirasSanitarias()
    }

    private fun findViews() {
        mRecyclerView = findViewById(R.id.barreiras_recyclerview)
        mFabNovaBarreira = findViewById(R.id.barreiras_fab)
        mTxtListaVazia = findViewById(R.id.barreiras_txt_lista_vazia)
    }

    private fun configuraActionBar(supportActionBar: ActionBar?) {
        val nomeUsuario = intent.getStringExtra(Constantes.EXTRA_NOME_USURARIO)
        supportActionBar?.subtitle =
            String.format(resources.getString(R.string.msg_bem_vindo_), nomeUsuario)
    }

    private fun configuraRecyclerView() {
        mBarreirasRecyclerAdapter = BarreirasRecyclerAdapter()

        mRecyclerView?.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = mBarreirasRecyclerAdapter
        }

    }

    private fun configuraClickListeners() {
        mFabNovaBarreira?.setOnClickListener { clickBotaoNovaBarreira() }

        mBarreirasRecyclerAdapter?.onItemClickListener =
            object : BarreirasRecyclerAdapter.OnItemClickListener {
                override fun onClick(barreiraSanitaria: BarreiraSanitaria?) {
                    onClickItemLista(barreiraSanitaria)
                }

                override fun onLongClick(barreiraSanitaria: BarreiraSanitaria?) {
                    onLongClickClickItemLista(barreiraSanitaria)
                }
            }
    }

    fun pesquisaBarreirasSanitarias() {
        val barreiraSanitariaDAO: BarreiraSanitariaDAO =
            DAOFactory.getDataSource(TabelasDataBase.BARREIRA_SANITARIA) as BarreiraSanitariaDAO
        val barreiraSanitarias: List<BarreiraSanitaria> = barreiraSanitariaDAO.listar()

        mBarreirasRecyclerAdapter?.atualiza(barreiraSanitarias as ArrayList<BarreiraSanitaria>)

        if (barreiraSanitarias.isNotEmpty()) {
            mTxtListaVazia?.visibility = View.GONE
        }
    }

    private fun clickBotaoNovaBarreira() {
        val intent = Intent(this, DetalhesBarreiraSanitariaActivty::class.java)
            .apply {
                putExtra(Constantes.EXTRA_MODO_CADASTRO, true)
            }
        startActivity(intent)
    }

    private fun onClickItemLista(barreiraSanitaria: BarreiraSanitaria?) {
        val intent = Intent(this, PesquisaPessoasActivity::class.java)
            .apply {
                putExtra(Constantes.EXTRA_ID_BARREIRA, barreiraSanitaria?.id)
                putExtra(Constantes.EXTRA_NOME_BARREIRA, barreiraSanitaria?.nome)
            }
        startActivity(intent)
    }

    private fun onLongClickClickItemLista(barreiraSanitaria: BarreiraSanitaria?) {
        val messagem = String.format(
            resources.getString(R.string.msg_deseja_editar_informacoes_),
            barreiraSanitaria?.nome
        )

        UtilDialog.showDialogSimNao(this, messagem,
            DialogInterface.OnClickListener { dialogInterface, i -> //TODO não implementado
                UtilDialog.showToast(baseContext, "Não implementado.")
            })
    }

}
