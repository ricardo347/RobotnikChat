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
    private Button enviarButton;
    private EditText chatEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chatRecyclerView =  findViewById(R.id.chatRecyclerView);
        chats = new ArrayList<>();
        //chats.add(new Chat("primeiro", 1, null));
        new AssistantManager().execute("Oi Chatbot, tudo bem?");


        adapter = new ChatAdapter(chats, this);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatRecyclerView.setAdapter(adapter);

        chatEditText = findViewById(R.id.chatEditText);
        enviarButton = findViewById(R.id.enviarButton);

        enviarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //chats.add(new Chat("segundo", 1,null));
                //adapter.notifyDataSetChanged();
                chats.add(new Chat(chatEditText.getText().toString(),1,null));
                adapter.notifyDataSetChanged();
                if(chatEditText.getText().length() > 0){
                    new AssistantManager().execute(chatEditText.getText().toString());
                    chatEditText.setText("");
                }
            }

        });

        //AssistantFactory assistant = new AssistantFactory();
        //assistant.execute("");


    }

    public class AssistantManager extends AsyncTask<String, Void, String> {

        private com.ibm.watson.assistant.v2.Assistant assistant;
        private String url;
        private String apiKey;
        private String version;
        private String assistantId;
        private String resposta;
        private String pergunta;

        public AssistantManager() {
            this.url = "https://gateway.watsonplatform.net/assistant/api";
            this.apiKey = "FnNkUk_YRYZIZ3d-bWRqGtC6XAlE0zGpZD9NTSVOcUi_";
            this.version = "2019-02-28";
            this.assistantId = "315959f5-1d0d-4875-9fef-2fc41ed83c5c";

            IamOptions options = new IamOptions.Builder()
                    .apiKey(apiKey)
                    .build();
            this.assistant = new com.ibm.watson.assistant.v2.Assistant(version, options);
            this.assistant.setEndPoint(url);
            Log.i("AssistantFactory","Construtor iniciado com suessso.");

        }

        @Override
        protected String doInBackground(String... strings) {

            IamOptions iamOptions = new IamOptions.Builder().apiKey(this.apiKey).build();
            com.ibm.watson.assistant.v2.Assistant service = new Assistant("2019-02-28", iamOptions);
            service.setEndPoint(this.url);

            CreateSessionOptions options = new CreateSessionOptions.Builder(this.assistantId).build();

            SessionResponse response = service.createSession(options).execute().getResult();

            System.out.println(response);

            MessageInput input = new MessageInput.Builder()
                    .messageType("text")
                    .text(strings[0]) //string da pergunta
                    .build();

            MessageOptions messageOptions = new MessageOptions.Builder(this.assistantId, response.getSessionId())
                    .input(input)
                    .build();

            MessageResponse resposta = this.assistant.message(messageOptions).execute().getResult();
            Log.v("Mensagem", resposta.toString());

            return resposta.toString();
        }

        @Override
        protected void onPostExecute(String resultado){
            jsonParser(resultado.toString());
        }

        private void jsonParser(String resultado){
            try{
                JSONObject json = new JSONObject(resultado);

                Log.v("Tamanho do json inicial",""+json.length());
                Log.v("Tamanho do json inicial",""+json.toString());
                String resposta = json.getJSONObject("output")
                        .getJSONArray("generic")
                        .getJSONObject(0)
                        .get("text")
                        .toString();

                Log.v("output", ""+ resposta);

                chats.add(new Chat(resposta,1,null));

                adapter.notifyDataSetChanged();

            }
            catch (JSONException e){
                e.printStackTrace();

            }
        }
    }


}
