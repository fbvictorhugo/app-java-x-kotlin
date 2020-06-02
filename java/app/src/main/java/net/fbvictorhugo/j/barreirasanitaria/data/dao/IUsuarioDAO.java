package net.fbvictorhugo.j.barreirasanitaria.data.dao;

import net.fbvictorhugo.j.barreirasanitaria.data.model.Usuario;

public interface IUsuarioDAO {

    Usuario procurarUsuario(String login, String senha);

}
