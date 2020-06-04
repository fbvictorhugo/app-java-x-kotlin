package net.fbvictorhugo.k.barreirasanitaria.data.dao.dummy

import net.fbvictorhugo.k.barreirasanitaria.data.dao.IPessoaDAO
import net.fbvictorhugo.k.barreirasanitaria.data.model.Pessoa

object PessoaDummyDataBase : IPessoaDAO {

    private val mDummyList: ArrayList<Pessoa> = ArrayList()

    init {
        val pessoa = Pessoa("João das Neves")
            .apply {
                id = 1
                numeroDocumento = 10020010052
            }
        mDummyList.add(pessoa)

        val pessoa2 = Pessoa("Fulano de Tal")
            .apply {
                id = 2
                numeroDocumento = 10020010042
            }
        mDummyList.add(pessoa2)
    }

    override fun listar(): ArrayList<Pessoa> {
        return mDummyList
    }

    override fun inserir(pessoa: Pessoa) {
        TODO("Not yet implemented")
    }

    override fun atualizar(pessoa: Pessoa) {
        TODO("Not yet implemented")
    }

}
