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
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1);
        pessoa.setNumeroDocumeto(1);
        pessoa.setNome("João das Neves");

        mDummyList.add(pessoa);

        pessoa = new Pessoa();
        pessoa.setId(2);
        pessoa.setNumeroDocumeto(2);
        pessoa.setNome("Fulano de Tal");
        mDummyList.add(pessoa);

    }

    public List<Pessoa> listar() {
        return mDummyList;
    }

    @Override
    public void inserir(Pessoa pessoa) throws Exception {

    }

    @Override
    public void atualizar(Pessoa pessoa) throws Exception {

    }

    private long getProximoIdDisponivel() {
        if (mDummyList.size() == 0) {
            return 1;
        } else {
            return mDummyList.get(mDummyList.size() - 1).getId() + 1;
        }
    }

}
