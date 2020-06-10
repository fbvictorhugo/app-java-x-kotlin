package net.fbvictorhugo.j.barreirasanitaria.data.dao.dummy;

import net.fbvictorhugo.j.barreirasanitaria.data.dao.QuestionarioDAO;
import net.fbvictorhugo.j.barreirasanitaria.data.model.Questionario;

import java.util.ArrayList;
import java.util.List;

public class DummyQuestionarioDAO implements QuestionarioDAO {

    private static DummyQuestionarioDAO sInstance;
    private List<Questionario> mDummyList;

    public static DummyQuestionarioDAO getInstance() {
        if (sInstance == null) {
            sInstance = new DummyQuestionarioDAO();
        }
        return sInstance;
    }

    private DummyQuestionarioDAO() {
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
