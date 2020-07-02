package net.fbvictorhugo.k.barreirasanitaria.data.dao.dummy

import net.fbvictorhugo.k.barreirasanitaria.data.dao.PessoaDAO
import net.fbvictorhugo.k.barreirasanitaria.data.model.Pessoa
import net.fbvictorhugo.k.barreirasanitaria.extension.fromString
import net.fbvictorhugo.k.barreirasanitaria.utils.Constantes.FORMATO_DATA_NASCIMENTO
import java.util.*
import kotlin.collections.ArrayList

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
        for (i in _dummyList.indices) {
            if (_dummyList[i].id == pessoa.id) {
                _dummyList[i] = pessoa
            }
        }
    }

    override fun pesquisarPorDocumento(numeroDocumento: Long): ArrayList<Pessoa> {
        val resultado: ArrayList<Pessoa> = ArrayList()
        for (pessoa in _dummyList) {
            if (pessoa.numeroDocumento == numeroDocumento) {
                resultado.add(pessoa)
                break
            }
        }
        return resultado
    }

    override fun procurarPessoa(pessoaId: Long): Pessoa? {
        return _dummyList
            .find {
                it.id == pessoaId
            }
    }

    init {
        //TODO LISTA FAKE
        _dummyList.add(
            Pessoa("Paulo Pereira Araujo", 10020010051, 1)
                .apply {
                    dataNascimento = Date().fromString("05/08/1996", FORMATO_DATA_NASCIMENTO)
                    cidade = "Rio de Janeiro"
                    estado = "RJ"
                    telefone = 229999999
                })

        _dummyList.add(Pessoa("Tiago Pereira Rocha", 10020010052, 2)

            .apply {
                dataNascimento = Date().fromString("04/11/1980", FORMATO_DATA_NASCIMENTO)
                cidade = "Ferraz de Vasconcelos"
                estado = "SP"
                telefone = 369999999
            })

        _dummyList.add(Pessoa("Laura Pereira Costa", 10020010053, 3)
            .apply {
                dataNascimento = Date().fromString("27/03/1953", FORMATO_DATA_NASCIMENTO)
                cidade = "Cataguases"
                estado = "MG"
                bairro = "Centro"
                telefone = 329999999
            })
    }

}
