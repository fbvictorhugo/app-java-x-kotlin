package net.fbvictorhugo.j.barreirasanitaria.data.dao.dummy;

import net.fbvictorhugo.j.barreirasanitaria.data.dao.IUsuarioDAO;
import net.fbvictorhugo.j.barreirasanitaria.data.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDummyDataBase implements IUsuarioDAO {

    private static UsuarioDummyDataBase sFactory;
    private List<Usuario> mDummyList;

    public static UsuarioDummyDataBase getInstance() {
        if (sFactory == null) {
            sFactory = new UsuarioDummyDataBase();
        }
        return sFactory;
    }

    private UsuarioDummyDataBase() {
        mDummyList = new ArrayList<>();
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setLogin("abc");
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
