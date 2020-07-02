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

    override fun inserir(questionario: Questionario) {
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
            viagemExterior = randomBoolean()
            sintomaFebre = randomBoolean()
            sintomaCoriza = randomBoolean()
            sintomaTosse = randomBoolean()
            sintomaCancaco = randomBoolean()
            sintomaDorGarganta = randomBoolean()
            sintomaFaltaAr = randomBoolean()
            sintomaContatoComEnfermos = randomBoolean()
        }
        val quest2 = Questionario(2, 2, 2).apply {
            dataResposta = Date()
            viagemExterior = randomBoolean()
            sintomaFebre = randomBoolean()
            sintomaCoriza = randomBoolean()
            sintomaTosse = randomBoolean()
            sintomaCancaco = randomBoolean()
            sintomaDorGarganta = randomBoolean()
            sintomaFaltaAr = randomBoolean()
            sintomaContatoComEnfermos = randomBoolean()
        }
        val quest3 = Questionario(3, 1, 2).apply {
            dataResposta = Date()
            viagemExterior = randomBoolean()
            sintomaFebre = randomBoolean()
            sintomaCoriza = randomBoolean()
            sintomaTosse = randomBoolean()
            sintomaCancaco = randomBoolean()
            sintomaDorGarganta = randomBoolean()
            sintomaFaltaAr = randomBoolean()
            sintomaContatoComEnfermos = randomBoolean()
        }
        val quest4 = Questionario(4, 1, 1).apply {
            dataResposta = Date()
            viagemExterior = randomBoolean()
            sintomaFebre = randomBoolean()
            sintomaCoriza = randomBoolean()
            sintomaTosse = randomBoolean()
            sintomaCancaco = randomBoolean()
            sintomaDorGarganta = randomBoolean()
            sintomaFaltaAr = randomBoolean()
            sintomaContatoComEnfermos = randomBoolean()
        }

        _dummyList.add(quest4)
        _dummyList.add(quest3)
        _dummyList.add(quest2)
        _dummyList.add(quest)

    }

    private fun randomBoolean(): Boolean {
        return Math.random() < 0.5
    }

}
