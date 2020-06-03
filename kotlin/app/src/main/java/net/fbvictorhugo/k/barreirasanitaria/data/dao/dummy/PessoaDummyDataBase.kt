package net.fbvictorhugo.k.barreirasanitaria.data.dao.dummy

import net.fbvictorhugo.k.barreirasanitaria.data.dao.IPessoaDAO
import net.fbvictorhugo.k.barreirasanitaria.data.model.Pessoa

object PessoaDummyDataBase : IPessoaDAO {

    private val mDummyList: ArrayList<Pessoa> = ArrayList()

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
