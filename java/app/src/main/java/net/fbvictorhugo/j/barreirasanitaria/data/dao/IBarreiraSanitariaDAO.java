package net.fbvictorhugo.j.barreirasanitaria.data.dao;

import net.fbvictorhugo.j.barreirasanitaria.data.model.BarreiraSanitaria;

import java.util.List;

public interface IBarreiraSanitariaDAO {

    List<BarreiraSanitaria> listar();

    void inserir(BarreiraSanitaria barreiraSanitaria) throws Exception;

    void atualizar(BarreiraSanitaria barreiraSanitaria) throws Exception;

}
