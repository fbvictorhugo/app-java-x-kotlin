package net.fbvictorhugo.k.barreirasanitaria.data.dao

import net.fbvictorhugo.k.barreirasanitaria.data.model.Pessoa

interface IPessoaDAO {

    fun listar(): ArrayList<Pessoa>

    @Throws(Exception::class)
    fun inserir(pessoa: Pessoa)

    @Throws(Exception::class)
    fun atualizar(pessoa: Pessoa)

    fun pesquisar(numeroDocumento: Long): ArrayList<Pessoa>

}
