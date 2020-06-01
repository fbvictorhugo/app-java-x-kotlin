package net.fbvictorhugo.k.barreirasanitaria.data.dao

import net.fbvictorhugo.k.barreirasanitaria.data.dao.dummy.UsuarioDummyDAO
import kotlin.reflect.KClass

object DAOFactory {

    fun getDataSource(dataSourceInterface: KClass<IUsuarioDAO>): Any? {
        return instaceDummyDAO(dataSourceInterface)
    }

    private fun instaceDummyDAO(dataSourceInterface: KClass<IUsuarioDAO>): Any? {
        return if (isSameClass(dataSourceInterface, IUsuarioDAO::class)) {
            UsuarioDummyDAO()
        } else {
            null
        }
    }

    private fun isSameClass(
        verifyClass: KClass<IUsuarioDAO>,
        targetClass: KClass<IUsuarioDAO>
    ): Boolean {
        if (verifyClass.toString() == targetClass.toString()) {
            return true
        }
        return false
    }
}
