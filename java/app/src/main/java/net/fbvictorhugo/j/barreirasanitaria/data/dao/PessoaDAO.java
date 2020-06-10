package net.fbvictorhugo.j.barreirasanitaria.data.dao;

import net.fbvictorhugo.j.barreirasanitaria.data.model.Pessoa;

import java.util.List;

public interface PessoaDAO {

    List<Pessoa> listar();

    void inserir(Pessoa pessoa) throws Exception;

    void atualizar(Pessoa pessoa) throws Exception;

    List<Pessoa> pesquisar(long numeroDocumento);

}
