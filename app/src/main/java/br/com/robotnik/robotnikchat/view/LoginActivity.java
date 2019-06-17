package br.com.robotnik.robotnikchat.view;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ibm.cloud.sdk.core.service.security.IamOptions;
import com.ibm.watson.assistant.v2.Assistant;
import com.ibm.watson.assistant.v2.model.CreateSessionOptions;
import com.ibm.watson.assistant.v2.model.MessageInput;
import com.ibm.watson.assistant.v2.model.MessageOptions;
import com.ibm.watson.assistant.v2.model.MessageResponse;
import com.ibm.watson.assistant.v2.model.SessionResponse;

import java.util.regex.Pattern;

import br.com.robotnik.robotnikchat.R;
import br.com.robotnik.robotnikchat.control.ConnectionStateMonitor;
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

        //IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        //ConnectionStateMonitor connectivityReceiver = new ConnectionStateMonitor(this);
        //connectivityReceiver.enable(this);

        //filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");

        //NetworkChangeReceiver receiver = new NetworkChangeReceiver();
        //registerReceiver(receiver, filter);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(padraoEmail.matcher(email.getText().toString()).matches()){
                    Intent i = new Intent(LoginActivity.this, ChatActivity.class);

                //VERIFICAR SE O USUÁRIO JA EXISTE, SENÃO EXISTE PEGA O PROXIMO ID, CRIA O USUARIO E PASSA O ID POR INTENT

                    UsuarioDAO usuarioDAO = new UsuarioDAO(getApplicationContext());
                    Usuario usuarioLogado  = usuarioDAO.buscaUsuario(nome.getText().toString(), email.getText().toString());

                    if (usuarioLogado == null) { //usuario nao existe, precisa criar outro
                        i.putExtra("idUsuario", usuarioDAO.getProximoIdUsuario());
                        usuarioDAO.insereUsuario(new Usuario(1, nome.getText().toString(), email.getText().toString()));
                    }else{ //ja existe não será criado
                        i.putExtra("idUsuario", usuarioLogado.getId());
                    }

                    i.putExtra("nomeUsuario", nome.getText().toString());
                    i.putExtra("emailUsuario", email.getText().toString());
                    startActivity(i);
                    nome.setText("");
                    email.setText("");

                }else{
                    Toast.makeText(getApplicationContext(), "Digite um email valido", Toast.LENGTH_SHORT).show();
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
       // Toast.makeText(this, "Selected Item: " +item.getTitle(), Toast.LENGTH_SHORT).show();

        switch (item.getItemId()) {
            case R.id.menu_relatorios:
                Intent i = new Intent(LoginActivity.this, ReportActivity.class);
                startActivity(i);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class NetworkChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, final Intent intent) {

            ConnectivityManager cm =
                    (ConnectivityManager)context.getSystemService(
                            Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

            boolean isConnected = (activeNetwork != null) && activeNetwork.isConnected();
            if (isConnected) {
                Log.v("Estado da Rede", "voltou");
            }else{
                Log.v("Estado da Rede", "Caiu!!!!!!!!!");
            }
        }
    }


}
