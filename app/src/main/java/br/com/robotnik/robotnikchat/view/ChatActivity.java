package br.com.robotnik.robotnikchat.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import br.com.robotnik.robotnikchat.control.AssistantFactory;
import br.com.robotnik.robotnikchat.R;
import br.com.robotnik.robotnikchat.model.Sessao;
import br.com.robotnik.robotnikchat.model.SessaoDAO;
import br.com.robotnik.robotnikchat.model.Usuario;

public class ChatActivity extends AppCompatActivity {
    private ChatAdapter adapter;
    private List<Chat> chats;
    private RecyclerView chatRecyclerView;
    private ImageButton enviarButton;
    private EditText chatEditText;
    private Sessao sessao;
    private final String BOAS_VINDAS = "Olá, sou Robotnik, em que posso te ajudar?";


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chatRecyclerView =  findViewById(R.id.chatRecyclerView);
        chats = new ArrayList<>();

        //inicialização da sessão
        //recuperar o usuario do login do intent

        SessaoDAO sessaoDAO = new SessaoDAO(this);

        sessao = new Sessao(
                sessaoDAO.getProximaSessao(),
                new Usuario(
                        sessaoDAO.getProximaSessao(),
                        "nome",
                        "email"
                ),
                new Timestamp(System.currentTimeMillis()).toString(),
                new Timestamp(System.currentTimeMillis()).toString()
        );



        adapter = new ChatAdapter(chats, sessao, this);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatRecyclerView.setAdapter(adapter);

        chats.add(new Chat(BOAS_VINDAS,0));
        adapter.notifyDataSetChanged();

        chatEditText = findViewById(R.id.chatEditText);
        enviarButton = findViewById(R.id.enviarButton);


        enviarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(chatEditText.getText().length() > 0){
                    chats.add(new Chat(chatEditText.getText().toString(),1));
                    adapter.notifyDataSetChanged();

                    AssistantFactory assistant  = new AssistantFactory(chatRecyclerView, chats);
                    assistant.execute(chatEditText.getText().toString());

                    chatEditText.setText("");
                    chatRecyclerView.scrollToPosition(chats.size() - 1);
                    chatEditText.setEnabled(false);
                }


            }

        });
    }




}
