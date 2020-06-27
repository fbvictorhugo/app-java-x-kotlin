package net.fbvictorhugo.k.barreirasanitaria.data.dao.dummy

import net.fbvictorhugo.k.barreirasanitaria.data.dao.QuestionarioDAO
import net.fbvictorhugo.k.barreirasanitaria.data.model.Questionario
import java.util.*
import kotlin.collections.ArrayList

object DummyQuestionarioDAO : QuestionarioDAO {

    private val _dummyList: ArrayList<Questionario> = ArrayList()

    fun getProximoIdDisponivel(): Long {
        return if (_dummyList.size == 0) {
            1
        } else {
            _dummyList[_dummyList.size - 1].id + 1
        }
    }

    override fun listar(): ArrayList<Questionario> {
        return _dummyList
    }

    override fun inserir(questionario: Questionario)  {
        questionario.id = getProximoIdDisponivel()
        _dummyList.add(questionario)
    }

    override fun procurarUltimoQuestionarioRespondidoPorPessoa(idPessoa: Long): Questionario? {
        return _dummyList
            .sortedByDescending { it.dataResposta }
            .find {
                it.pessoaId == idPessoa
            }
    }

    init {
        val quest = Questionario(1, 1, 1).apply {
            dataResposta = Date()
            sintomaCancaco = true
            sintomaCoriza = true
        }
        val quest2 = Questionario(2, 2, 1).apply {
            dataResposta = Date()
            sintomaCancaco = true
            sintomaDorGarganta = true
        }
        val quest3 = Questionario(3, 1, 1).apply {
            dataResposta = Date()
            sintomaCancaco = true
            sintomaDorGarganta = true
        }

        _dummyList.add(quest3)
        _dummyList.add(quest2)
        _dummyList.add(quest)

    }

}
