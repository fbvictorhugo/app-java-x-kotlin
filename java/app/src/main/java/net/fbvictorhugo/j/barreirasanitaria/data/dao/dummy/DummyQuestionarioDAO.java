package net.fbvictorhugo.j.barreirasanitaria.data.dao.dummy;

import net.fbvictorhugo.j.barreirasanitaria.data.dao.QuestionarioDAO;
import net.fbvictorhugo.j.barreirasanitaria.data.model.Questionario;

import java.util.ArrayList;
import java.util.Date;
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
        buildListaFake();
    }

    private long getProximoIdDisponivel() {
        if (mDummyList.size() == 0) {
            return 1;
        } else {
            return mDummyList.get(mDummyList.size() - 1).getId() + 1;
        }
    }

    @Override
    public List<Questionario> listar() {
        return mDummyList;
    }

    @Override
    public void inserir(Questionario questionario) throws Exception {
        if (questionario == null) {
            throw new NullPointerException("Modelo Questionario está nulo.");
        }

        questionario.setId(getProximoIdDisponivel());
        mDummyList.add(questionario);
    }

    @Override
    public Questionario procurarUltimoQuestionarioRespondidoPorPessoa(long idPessoa) {
        Date lasDate = null;
        Questionario questionario = null;
        for (Questionario itemQuestionario : mDummyList) {
            if (itemQuestionario.getPessoaId() == idPessoa) {
                if (lasDate == null) {
                    lasDate = itemQuestionario.getDataResposta();
                }

                if (itemQuestionario.getDataResposta().compareTo(lasDate) >= 0) {
                    lasDate = itemQuestionario.getDataResposta();
                    questionario = itemQuestionario;
                }
            }
        }
        return questionario;
    }

    void buildListaFake() {
        //TODO LISTA FAKE
        Questionario q;

        q = new Questionario();
        q.setId(3);
        q.setPessoaId(2);
        q.setBarreiraId(1);
        q.setViagemExterior(randomBoolean());
        q.setSintomaFebre(randomBoolean());
        q.setSintomaCoriza(randomBoolean());
        q.setSintomaTosse(randomBoolean());
        q.setSintomaCancaco(randomBoolean());
        q.setSintomaDorGarganta(randomBoolean());
        q.setSintomaFaltaAr(randomBoolean());
        q.setSintomaContatoComEnfermos(randomBoolean());
        q.setDataResposta(new Date());
        mDummyList.add(q);

        q = new Questionario();
        q.setId(2);
        q.setPessoaId(2);
        q.setBarreiraId(2);
        q.setViagemExterior(randomBoolean());
        q.setSintomaFebre(randomBoolean());
        q.setSintomaCoriza(randomBoolean());
        q.setSintomaTosse(randomBoolean());
        q.setSintomaCancaco(randomBoolean());
        q.setSintomaDorGarganta(randomBoolean());
        q.setSintomaFaltaAr(randomBoolean());
        q.setSintomaContatoComEnfermos(randomBoolean());
        q.setDataResposta(new Date());
        mDummyList.add(q);

        q = new Questionario();
        q.setId(1);
        q.setPessoaId(1);
        q.setBarreiraId(1);
        q.setViagemExterior(randomBoolean());
        q.setSintomaFebre(randomBoolean());
        q.setSintomaCoriza(randomBoolean());
        q.setSintomaTosse(randomBoolean());
        q.setSintomaCancaco(randomBoolean());
        q.setSintomaDorGarganta(randomBoolean());
        q.setSintomaFaltaAr(randomBoolean());
        q.setSintomaContatoComEnfermos(randomBoolean());
        q.setDataResposta(new Date());
        mDummyList.add(q);

        q = new Questionario();
        q.setId(4);
        q.setPessoaId(1);
        q.setBarreiraId(1);
        q.setViagemExterior(randomBoolean());
        q.setSintomaFebre(randomBoolean());
        q.setSintomaCoriza(randomBoolean());
        q.setSintomaTosse(randomBoolean());
        q.setSintomaCancaco(randomBoolean());
        q.setSintomaDorGarganta(randomBoolean());
        q.setSintomaFaltaAr(randomBoolean());
        q.setSintomaContatoComEnfermos(randomBoolean());
        q.setDataResposta(new Date());
        mDummyList.add(q);

    }

    private boolean randomBoolean() {
        return Math.random() < 0.5;
    }

}
