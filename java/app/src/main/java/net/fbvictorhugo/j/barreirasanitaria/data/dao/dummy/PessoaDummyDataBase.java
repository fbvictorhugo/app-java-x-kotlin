package net.fbvictorhugo.j.barreirasanitaria.data.dao.dummy;

import net.fbvictorhugo.j.barreirasanitaria.data.dao.IPessoaDAO;
import net.fbvictorhugo.j.barreirasanitaria.data.model.Pessoa;

import java.util.ArrayList;
import java.util.List;

public class PessoaDummyDataBase implements IPessoaDAO {

    private static PessoaDummyDataBase sFactory;
    private List<Pessoa> mDummyList;

    public static PessoaDummyDataBase getInstance() {
        if (sFactory == null) {
            sFactory = new PessoaDummyDataBase();
        }
        return sFactory;
    }

    private PessoaDummyDataBase() {
        mDummyList = new ArrayList<>();
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
        List<Pessoa> resultado = new ArrayList<>();
        for (Pessoa pessoa : mDummyList) {
            if (pessoa.getNumeroDocumento() == numeroDocumento) {
                resultado.add(pessoa);
                break;
            }
        }
        return resultado;
    }

}
