package net.fbvictorhugo.k.barreirasanitaria.data.dao.dummy

import net.fbvictorhugo.k.barreirasanitaria.data.dao.QuestionarioDAO
import net.fbvictorhugo.k.barreirasanitaria.data.model.Questionario

object DummyQuestionarioDAO : QuestionarioDAO {

    private val _dummyList: ArrayList<Questionario> = ArrayList()

    override fun listar(): ArrayList<Questionario> {
        return _dummyList
    }

    override fun inserir(questionario: Questionario) {
        TODO("Not yet implemented")
    }

}
