package br.com.robotnik.robotnikchat;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.ibm.cloud.sdk.core.service.security.IamOptions;
import com.ibm.watson.assistant.v2.Assistant;
import com.ibm.watson.assistant.v2.model.CreateSessionOptions;
import com.ibm.watson.assistant.v2.model.MessageInput;
import com.ibm.watson.assistant.v2.model.MessageOptions;
import com.ibm.watson.assistant.v2.model.MessageResponse;
import com.ibm.watson.assistant.v2.model.SessionResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private ChatAdapter adapter;
    private List<Chat> chats;
    private RecyclerView chatRecyclerView;
    private ImageButton enviarButton;
    private EditText chatEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chatRecyclerView =  findViewById(R.id.chatRecyclerView);
        chats = new ArrayList<>();
         //new AssistantManager().execute("Oi Chatbot, tudo bem?");


        adapter = new ChatAdapter(chats, this);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatRecyclerView.setAdapter(adapter);


        chatEditText = findViewById(R.id.chatEditText);
        enviarButton = findViewById(R.id.enviarButton);


        enviarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(chatEditText.getText().length() > 0){
                    chats.add(new Chat(chatEditText.getText().toString(),1,null));
                    adapter.notifyDataSetChanged();

                    AssistantFactory assistant  = new AssistantFactory(chatRecyclerView, chats);
                    assistant.execute(chatEditText.getText().toString());

                    chatEditText.setText("");
                    chatRecyclerView.scrollToPosition(chats.size() - 1);
                }
            }

        });
    }




}
