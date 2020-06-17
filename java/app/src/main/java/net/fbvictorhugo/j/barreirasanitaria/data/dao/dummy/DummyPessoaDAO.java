package net.fbvictorhugo.j.barreirasanitaria.data.dao.dummy;

import net.fbvictorhugo.j.barreirasanitaria.data.dao.PessoaDAO;
import net.fbvictorhugo.j.barreirasanitaria.data.model.Pessoa;

import java.util.ArrayList;
import java.util.List;

public class DummyPessoaDAO implements PessoaDAO {

    private static DummyPessoaDAO sInstance;
    private List<Pessoa> mDummyList;

    public static DummyPessoaDAO getInstance() {
        if (sInstance == null) {
            sInstance = new DummyPessoaDAO();
        }
        return sInstance;
    }

    private DummyPessoaDAO() {
        mDummyList = new ArrayList<>();
        buildFakeLista();
    }

    private long getProximoIdDisponivel() {
        if (mDummyList.size() == 0) {
            return 1;
        } else {
            return mDummyList.get(mDummyList.size() - 1).getId() + 1;
        }
    }

    public List<Pessoa> listar() {
        return mDummyList;
    }

    @Override
    public void inserir(Pessoa pessoa) throws Exception {
        if (pessoa == null) {
            throw new NullPointerException("Modelo Pessoa está nulo.");
        }

        pessoa.setId(getProximoIdDisponivel());
        mDummyList.add(pessoa);
    }

    @Override
    public void atualizar(Pessoa pessoa) throws Exception {
        //TODO("Not yet implemented")
    }

    public List<Pessoa> pesquisar(long numeroDocumento) {
        //Para usar API streams requer minSdkVersion = 24
        List<Pessoa> resultado = new ArrayList<>();
        for (Pessoa pessoa : mDummyList) {
            if (pessoa.getNumeroDocumento() == numeroDocumento) {
                resultado.add(pessoa);
                break;
            }
        }
        return resultado;
    }

    private void buildFakeLista() {
        Pessoa p = new Pessoa();
        p.setId(1);
        p.setNumeroDocumento(1);
        p.setNome("Vitor");
        mDummyList.add(p);

        p = new Pessoa();
        p.setId(2);
        p.setNumeroDocumento(2);
        p.setNome("Hugo");
        mDummyList.add(p);

        p = new Pessoa();
        p.setId(3);
        p.setNumeroDocumento(3);
        p.setNome("Fabio");
        mDummyList.add(p);

    }

}
