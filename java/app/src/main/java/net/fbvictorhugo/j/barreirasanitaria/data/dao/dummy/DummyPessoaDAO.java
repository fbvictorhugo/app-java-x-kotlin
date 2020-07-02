package net.fbvictorhugo.j.barreirasanitaria.data.dao.dummy;

import net.fbvictorhugo.j.barreirasanitaria.data.dao.PessoaDAO;
import net.fbvictorhugo.j.barreirasanitaria.data.model.Pessoa;
import net.fbvictorhugo.j.barreirasanitaria.utils.DateUtil;

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
        if (pessoa == null) {
            throw new NullPointerException("Modelo Pessoa está nulo.");
        }

        for (int i = 0; i < mDummyList.size(); i++) {
            if (mDummyList.get(i).getId() == pessoa.getId()) {
                mDummyList.set(i, pessoa);
            }
        }
    }

    public List<Pessoa> pesquisarPorDocumento(long numeroDocumento) {
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

    @Override
    public Pessoa procurarPessoa(long pessoaId) {
        //Para usar API streams requer minSdkVersion = 24
        for (Pessoa pessoa : mDummyList) {
            if (pessoa.getId() == pessoaId) {
                return pessoa;
            }
        }
        return null;
    }

    private void buildFakeLista() {
        //TODO LISTA FAKE
        Pessoa p = new Pessoa();
        p.setId(1);
        p.setNumeroDocumento(10020010051L);
        p.setNome("Paulo Pereira Araujo");
        p.setDataNascimento(DateUtil.formataDataNascimento("05/08/1996"));
        p.setCidade("Rio de Janeiro");
        p.setEstado("RJ");
        p.setTelefone(229999999);
        mDummyList.add(p);

        p = new Pessoa();
        p.setId(2);
        p.setNumeroDocumento(10020010052L);
        p.setNome("Tiago Pereira Rocha");
        p.setDataNascimento(DateUtil.formataDataNascimento("04/11/1980"));
        p.setCidade("Ferraz de Vasconcelos");
        p.setEstado("SP");
        p.setTelefone(369999999);
        mDummyList.add(p);

        p = new Pessoa();
        p.setId(3);
        p.setNumeroDocumento(10020010053L);
        p.setNome("Laura Pereira Costa");
        p.setDataNascimento(DateUtil.formataDataNascimento("27/03/1953"));
        p.setCidade("Cataguases");
        p.setEstado("MG");
        p.setBairro("Centro");
        p.setTelefone(329999999);
        mDummyList.add(p);

    }

}
