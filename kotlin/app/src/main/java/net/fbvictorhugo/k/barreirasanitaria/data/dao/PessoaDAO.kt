package net.fbvictorhugo.k.barreirasanitaria.data.dao

import net.fbvictorhugo.k.barreirasanitaria.data.model.Pessoa

interface PessoaDAO {

    fun listar(): ArrayList<Pessoa>

    @Throws(Exception::class)
    fun inserir(pessoa: Pessoa)

    @Throws(Exception::class)
    fun atualizar(pessoa: Pessoa)

    fun pesquisarPorDocumento(numeroDocumento: Long): ArrayList<Pessoa>

    fun procurarPessoa(pessoaId: Long): Pessoa?

}
