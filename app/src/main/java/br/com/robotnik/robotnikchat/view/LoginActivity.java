package br.com.robotnik.robotnikchat.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

import br.com.robotnik.robotnikchat.R;
import br.com.robotnik.robotnikchat.model.Usuario;
import br.com.robotnik.robotnikchat.model.UsuarioDAO;

public class LoginActivity extends AppCompatActivity {

    private EditText nome;
    private EditText email;
    private Button loginButton;
    private Pattern padraoEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        padraoEmail = Pattern.compile("^[a-zA-Z0-9.!#$%&'*+\\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$");
        nome = findViewById(R.id.nomeEditText);
        email = findViewById(R.id.emailEditText);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("email",email.getText().toString());
                if(padraoEmail.matcher(email.getText().toString()).matches()){
                    Intent i = new Intent(LoginActivity.this, ChatActivity.class);
                UsuarioDAO usuarioDAO = new UsuarioDAO(getApplicationContext());
                usuarioDAO.insereUsuario(new Usuario(1, nome.getText().toString(), email.getText().toString()));
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(), "Digite um texto valido", Toast.LENGTH_SHORT).show();
                    email.setText("");

                }
            }
        });


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(this, "Selected Item: " +item.getTitle(), Toast.LENGTH_SHORT).show();

        switch (item.getItemId()) {
            case R.id.menu_relatorios:
                Intent i = new Intent(LoginActivity.this, ReportActivity.class);
                startActivity(i);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
