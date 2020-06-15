package net.fbvictorhugo.j.barreirasanitaria.ui;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import net.fbvictorhugo.j.barreirasanitaria.R;
import net.fbvictorhugo.j.barreirasanitaria.data.dao.DAOFactory;
import net.fbvictorhugo.j.barreirasanitaria.data.dao.TabelasDataBase;
import net.fbvictorhugo.j.barreirasanitaria.data.dao.UsuarioDAO;
import net.fbvictorhugo.j.barreirasanitaria.data.model.Usuario;
import net.fbvictorhugo.j.barreirasanitaria.utils.Constantes;
import net.fbvictorhugo.j.barreirasanitaria.utils.UtilDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText mEdtUsuario;
    private TextInputLayout mInputLayoutUsuario;
    private TextInputEditText mEdtSenha;
    private TextInputLayout mInputLayoutSenha;
    private AppCompatButton mBtnEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViews();
        configuraClickListeners();
    }

    private void findViews() {
        mEdtUsuario = findViewById(R.id.login_edt_usuario);
        mInputLayoutUsuario = findViewById(R.id.login_inputlayout_usuario);
        mEdtSenha = findViewById(R.id.login_edt_senha);
        mInputLayoutSenha = findViewById(R.id.login_inputlayout_senha);
        mBtnEntrar = findViewById(R.id.login_btn_entrar);
    }

    private void configuraClickListeners() {
        if (mBtnEntrar != null) {
            mBtnEntrar.setOnClickListener(view -> clickBotaoEntrar());
        }
    }

    private void clickBotaoEntrar() {
        if (mInputLayoutUsuario != null) {
            mInputLayoutUsuario.setError("");
        }

        if (mInputLayoutSenha != null) {
            mInputLayoutSenha.setError("");
        }

        String login = null;
        String senha = null;

        if (mEdtUsuario != null) {
            login = mEdtUsuario.getText().toString();
        }

        if (mEdtSenha != null) {
            senha = mEdtSenha.getText().toString();
        }

        if (login == null || login.trim().isEmpty()) {
            mInputLayoutUsuario.setError(getResources().getString(R.string.msg_erro_campo_obrigatorio));
        } else if (senha == null || senha.trim().isEmpty()) {
            mInputLayoutSenha.setError(getResources().getString(R.string.msg_erro_campo_obrigatorio));
        } else {

            final UsuarioDAO usuarioDAO = (UsuarioDAO) DAOFactory.getInstance().getDataSource(TabelasDataBase.USUARIO);
            final Usuario usuario = usuarioDAO.procurarUsuario(login, senha);

            if (usuarioValido(usuario)) {
                Intent intent = new Intent(this, ListaBarreirasActivity.class);
                intent.putExtra(Constantes.EXTRA_ID_USURARIO, usuario.getId());
                intent.putExtra(Constantes.EXTRA_NOME_USURARIO, usuario.getNome());
                startActivity(intent);
                finish();
            } else {
                UtilDialog.showDialogOK(this, getResources().getString(R.string.msg_usuario_nao_encontrado));
            }
        }
    }

    private boolean usuarioValido(Usuario usuario) {
        return usuario != null && usuario.getId() > 0;
    }

}
