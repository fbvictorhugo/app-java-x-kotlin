package net.fbvictorhugo.k.barreirasanitaria.data.dao

import net.fbvictorhugo.k.barreirasanitaria.data.model.BarreiraSanitaria

interface BarreiraSanitariaDAO {

    fun listar(): ArrayList<BarreiraSanitaria>

    @Throws(Exception::class)
    fun inserir(barreiraSanitaria: BarreiraSanitaria)

    @Throws(Exception::class)
    fun atualizar(barreiraSanitaria: BarreiraSanitaria)

    fun procurarBarreira(barreiraId: Long): BarreiraSanitaria?

    fun procurarNomeBarreira(barreiraId: Long): String

}
