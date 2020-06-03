package net.fbvictorhugo.j.barreirasanitaria.data.dao.dummy;

import net.fbvictorhugo.j.barreirasanitaria.data.dao.IQuestionarioDAO;
import net.fbvictorhugo.j.barreirasanitaria.data.model.Questionario;

import java.util.ArrayList;
import java.util.List;

public class QuestionarioDummyDataBase implements IQuestionarioDAO {

    private static QuestionarioDummyDataBase sFactory;
    private List<Questionario> mDummyList;

    public static QuestionarioDummyDataBase getInstance() {
        if (sFactory == null) {
            sFactory = new QuestionarioDummyDataBase();
        }
        return sFactory;
    }

    private QuestionarioDummyDataBase() {
        mDummyList = new ArrayList<>();
    }

    @Override
    public List<Questionario> listar() {
        return mDummyList;
    }

    @Override
    public void inserir(Questionario questionario) throws Exception {
        //TODO("Not yet implemented")
    }

}
