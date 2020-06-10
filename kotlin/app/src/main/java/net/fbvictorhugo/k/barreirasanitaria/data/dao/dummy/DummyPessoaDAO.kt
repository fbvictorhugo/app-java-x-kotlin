package net.fbvictorhugo.k.barreirasanitaria.data.dao.dummy

import net.fbvictorhugo.k.barreirasanitaria.data.dao.PessoaDAO
import net.fbvictorhugo.k.barreirasanitaria.data.model.Pessoa

object DummyPessoaDAO : PessoaDAO {

    private val _dummyList: ArrayList<Pessoa> = ArrayList()

    fun getProximoIdDisponivel(): Long {
        return if (_dummyList.size == 0) {
            1
        } else {
            _dummyList[_dummyList.size - 1].id + 1
        }
    }

    override fun listar(): ArrayList<Pessoa> {
        return _dummyList
    }

    override fun inserir(pessoa: Pessoa) {
        pessoa.id = getProximoIdDisponivel()
        _dummyList.add(pessoa);
    }

    override fun atualizar(pessoa: Pessoa) {
        TODO("Not yet implemented")
    }

    override fun pesquisar(numeroDocumento: Long): ArrayList<Pessoa> {
        val resultado: ArrayList<Pessoa> = ArrayList()
        for (pessoa in _dummyList) {
            if (pessoa.numeroDocumento == numeroDocumento) {
                resultado.add(pessoa)
                break
            }
        }
        return resultado
    }

}
