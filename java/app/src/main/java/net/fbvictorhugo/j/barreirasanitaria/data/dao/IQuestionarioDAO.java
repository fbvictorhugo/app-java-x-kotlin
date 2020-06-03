package net.fbvictorhugo.j.barreirasanitaria.data.dao;

import net.fbvictorhugo.j.barreirasanitaria.data.model.Questionario;

import java.util.List;

public interface IQuestionarioDAO {

    List<Questionario> listar();

    void inserir(Questionario questionario) throws Exception;

}
