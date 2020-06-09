package net.fbvictorhugo.k.barreirasanitaria.data.dao.dummy

import net.fbvictorhugo.k.barreirasanitaria.data.dao.PessoaDAO
import net.fbvictorhugo.k.barreirasanitaria.data.model.Pessoa

object DummyPessoaDAO : PessoaDAO {

    private val mDummyList: ArrayList<Pessoa> = ArrayList()

    fun getProximoIdDisponivel(): Long {
        return if (mDummyList.size == 0) {
            1
        } else {
            mDummyList[mDummyList.size - 1].id + 1
        }
    }

    override fun listar(): ArrayList<Pessoa> {
        return mDummyList
    }

    override fun inserir(pessoa: Pessoa) {
        pessoa.id = getProximoIdDisponivel();
        mDummyList.add(pessoa);
    }

    override fun atualizar(pessoa: Pessoa) {
        TODO("Not yet implemented")
    }

    override fun pesquisar(numeroDocumento: Long): ArrayList<Pessoa> {
        val resultado: ArrayList<Pessoa> = ArrayList()
        for (pessoa in mDummyList) {
            if (pessoa.numeroDocumento == numeroDocumento) {
                resultado.add(pessoa)
                break
            }
        }
        return resultado
    }

}
