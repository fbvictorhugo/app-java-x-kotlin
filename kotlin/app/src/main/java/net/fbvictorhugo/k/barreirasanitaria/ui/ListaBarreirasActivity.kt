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
import net.fbvictorhugo.k.barreirasanitaria.data.dao.BarreiraSanitariaDAO
import net.fbvictorhugo.k.barreirasanitaria.data.dao.DAOFactory
import net.fbvictorhugo.k.barreirasanitaria.data.dao.TabelasDataBase
import net.fbvictorhugo.k.barreirasanitaria.data.model.BarreiraSanitaria
import net.fbvictorhugo.k.barreirasanitaria.ui.adapter.BarreirasRecyclerAdapter
import net.fbvictorhugo.k.barreirasanitaria.utils.Constantes
import net.fbvictorhugo.k.barreirasanitaria.utils.UtilDialog

class ListaBarreirasActivity : AppCompatActivity() {

    private var _recyclerView: RecyclerView? = null
    private var _barreirasRecyclerAdapter: BarreirasRecyclerAdapter? = null
    private var _fabNovaBarreira: FloatingActionButton? = null
    private var _txtListaVazia: AppCompatTextView? = null

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
        _recyclerView = findViewById(R.id.barreiras_recyclerview)
        _fabNovaBarreira = findViewById(R.id.barreiras_fab)
        _txtListaVazia = findViewById(R.id.barreiras_txt_lista_vazia)
    }

    private fun configuraActionBar(supportActionBar: ActionBar?) {
        val nomeUsuario = intent.getStringExtra(Constantes.EXTRA_NOME_USURARIO)
        supportActionBar?.subtitle =
            String.format(resources.getString(R.string.msg_bem_vindo_), nomeUsuario)
    }

    private fun configuraRecyclerView() {
        _barreirasRecyclerAdapter = BarreirasRecyclerAdapter()

        _recyclerView?.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = _barreirasRecyclerAdapter
        }

    }

    private fun configuraClickListeners() {
        _fabNovaBarreira?.setOnClickListener { clickBotaoNovaBarreira() }

        _barreirasRecyclerAdapter?.onItemClickListener =
            object : BarreirasRecyclerAdapter.OnItemClickListener {
                override fun onClick(barreiraSanitaria: BarreiraSanitaria?) {
                    onClickItemLista(barreiraSanitaria)
                }

                override fun onLongClick(barreiraSanitaria: BarreiraSanitaria?) {
                    onLongClickClickItemLista(barreiraSanitaria)
                }
            }
    }

    private fun pesquisaBarreirasSanitarias() {
        val barreiraSanitariaDAO: BarreiraSanitariaDAO =
            DAOFactory.getDataSource(TabelasDataBase.BARREIRA_SANITARIA) as BarreiraSanitariaDAO
        val barreiraSanitarias: List<BarreiraSanitaria> = barreiraSanitariaDAO.listar()

        _barreirasRecyclerAdapter?.atualiza(barreiraSanitarias as ArrayList<BarreiraSanitaria>)

        if (barreiraSanitarias.isNotEmpty()) {
            _txtListaVazia?.visibility = View.GONE
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
