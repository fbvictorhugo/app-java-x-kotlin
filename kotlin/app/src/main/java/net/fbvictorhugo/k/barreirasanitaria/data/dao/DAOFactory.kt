package net.fbvictorhugo.k.barreirasanitaria.data.dao

import net.fbvictorhugo.k.barreirasanitaria.data.dao.TabelasDataBase.*
import net.fbvictorhugo.k.barreirasanitaria.data.dao.dummy.DummyBarreiraSanitariaDAO
import net.fbvictorhugo.k.barreirasanitaria.data.dao.dummy.DummyPessoaDAO
import net.fbvictorhugo.k.barreirasanitaria.data.dao.dummy.DummyQuestionarioDAO
import net.fbvictorhugo.k.barreirasanitaria.data.dao.dummy.DummyUsuarioDAO

object DAOFactory {

    fun getDataSource(tabela: TabelasDataBase): Any {
        return when (tabela) {
            USUARIO -> DummyUsuarioDAO
            BARREIRA_SANITARIA -> DummyBarreiraSanitariaDAO
            PESSOA -> DummyPessoaDAO
            QUESTIONARIO -> DummyQuestionarioDAO
        }
    }
}
