package net.fbvictorhugo.k.barreirasanitaria.data.dao

import net.fbvictorhugo.k.barreirasanitaria.data.model.Questionario

interface QuestionarioDAO {
    fun listar(): ArrayList<Questionario>

    @Throws(Exception::class)
    fun inserir(questionario: Questionario)

}