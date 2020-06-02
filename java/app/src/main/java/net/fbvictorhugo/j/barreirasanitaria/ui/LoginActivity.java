package net.fbvictorhugo.j.barreirasanitaria.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import net.fbvictorhugo.j.barreirasanitaria.R;
import net.fbvictorhugo.j.barreirasanitaria.data.dao.DAOFactory;
import net.fbvictorhugo.j.barreirasanitaria.data.dao.IUsuarioDAO;
import net.fbvictorhugo.j.barreirasanitaria.data.dao.TabelasDataBase;
import net.fbvictorhugo.j.barreirasanitaria.data.model.Usuario;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText mEdtUsuario;
    TextInputLayout mInputLayoutUsuario;
    TextInputEditText mEdtSenha;
    TextInputLayout mInputLayoutSenha;
    AppCompatButton mBtnEntrar;

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
            mBtnEntrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickBotaoEntrar();
                }
            });
        }
    }

    private void clickBotaoEntrar() {
        String login = null;
        String senha = null;

        if (mEdtUsuario != null && mInputLayoutUsuario != null) {
            mInputLayoutUsuario.setError("");
            login = mEdtUsuario.getText().toString();
        }

        if (mEdtSenha != null && mInputLayoutSenha != null) {
            mInputLayoutSenha.setError("");
            senha = mEdtSenha.getText().toString();
        }

        if (login == null || login.trim().isEmpty()) {
            mInputLayoutUsuario.setError(getResources().getString(R.string.msg_erro_usuario_obrigatorio));
        } else if (senha == null || senha.trim().isEmpty()) {
            mInputLayoutSenha.setError(getResources().getString(R.string.msg_erro_senha_obrigatoria));
        } else {

            IUsuarioDAO usuarioDAO = (IUsuarioDAO) DAOFactory.getInstance().getDataSource(TabelasDataBase.USUARIO);
            Usuario usuario = usuarioDAO.procurarUsuario(login, senha);

            if (usuarioValido(usuario)) {
                String menssagem = String.format(getResources().getString(R.string.msg_bem_vindo_), usuario.getNome());
                UtilDialog.showToast(getApplicationContext(), menssagem);
                startActivity(new Intent(getApplicationContext(), ListaBarreirasActivity.class));
            }
        }
    }

    private boolean usuarioValido(Usuario usuario) {
        return usuario != null && usuario.getId() > 0;
    }

}
