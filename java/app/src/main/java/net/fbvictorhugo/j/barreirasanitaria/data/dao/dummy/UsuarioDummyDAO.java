package net.fbvictorhugo.j.barreirasanitaria.data.dao.dummy;

import net.fbvictorhugo.j.barreirasanitaria.data.dao.IUsuarioDAO;
import net.fbvictorhugo.j.barreirasanitaria.data.model.Usuario;

import java.util.Date;

public class UsuarioDummyDAO implements IUsuarioDAO {

    public UsuarioDummyDAO() {

    }

    @Override
    public Usuario procurarUsuario(String login, String senha) {
        if (login != null && !login.isEmpty() && senha != null && !senha.isEmpty()) {
            Usuario usuario = new Usuario();
            usuario.setId(1);
            usuario.setLogin(login);
            usuario.setSenha(senha);
            usuario.setNome("Victor Hugo");
            usuario.setUltimoLogin(new Date());
            return usuario;
        } else {
            return null;
        }
    }
}
