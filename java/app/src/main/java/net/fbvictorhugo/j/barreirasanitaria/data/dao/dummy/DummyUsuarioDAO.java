package net.fbvictorhugo.j.barreirasanitaria.data.dao.dummy;

import net.fbvictorhugo.j.barreirasanitaria.data.dao.UsuarioDAO;
import net.fbvictorhugo.j.barreirasanitaria.data.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class DummyUsuarioDAO implements UsuarioDAO {

    private static DummyUsuarioDAO sInstance;
    private List<Usuario> mDummyList = new ArrayList<>();

    public static DummyUsuarioDAO getInstance() {
        if (sInstance == null) {
            sInstance = new DummyUsuarioDAO();
        }
        return sInstance;
    }

    private DummyUsuarioDAO() {

        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setLogin("asd");
        usuario.setSenha("123");
        usuario.setNome("Victor Hugo");

        mDummyList.add(usuario);
    }

    @Override
    public Usuario procurarUsuario(String login, String senha) {
        if (login != null && !login.isEmpty() && senha != null && !senha.isEmpty()) {
            for (Usuario usuario : mDummyList) {
                if (usuario.getLogin().equals(login) && usuario.getSenha().equals(senha)) {
                    return usuario;
                }
            }
        }
        return null;
    }

}
