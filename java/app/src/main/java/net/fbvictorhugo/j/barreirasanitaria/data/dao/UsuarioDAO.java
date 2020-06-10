package net.fbvictorhugo.j.barreirasanitaria.data.dao;

import net.fbvictorhugo.j.barreirasanitaria.data.model.Usuario;

public interface UsuarioDAO {
    Usuario procurarUsuario(String login, String senha);
}
