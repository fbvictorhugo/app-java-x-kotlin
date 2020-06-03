package net.fbvictorhugo.k.barreirasanitaria.data.dao

import net.fbvictorhugo.k.barreirasanitaria.data.dao.TabelasDataBase.*
import net.fbvictorhugo.k.barreirasanitaria.data.dao.dummy.BarreiraSanitariaDummyDataBase
import net.fbvictorhugo.k.barreirasanitaria.data.dao.dummy.PessoaDummyDataBase
import net.fbvictorhugo.k.barreirasanitaria.data.dao.dummy.QuestionarioDummyDataBase
import net.fbvictorhugo.k.barreirasanitaria.data.dao.dummy.UsuarioDummyDataBase

object DAOFactory {

    fun getDataSource(tabela: TabelasDataBase): Any? {
        return instaceDummyDAO(tabela)
    }

    fun instaceDummyDAO(tabela: TabelasDataBase): Any? {

        return when (tabela) {
            USUARIO -> UsuarioDummyDataBase
            BARREIRA_SANITARIA -> BarreiraSanitariaDummyDataBase
            PESSOA -> PessoaDummyDataBase
            QUESTIONARIO -> QuestionarioDummyDataBase
        }
    }

}
