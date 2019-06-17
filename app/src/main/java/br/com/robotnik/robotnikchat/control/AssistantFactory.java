package br.com.robotnik.robotnikchat.control;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.ibm.cloud.sdk.core.service.security.IamOptions;
import com.ibm.watson.assistant.v2.Assistant;
import com.ibm.watson.assistant.v2.model.CreateSessionOptions;
import com.ibm.watson.assistant.v2.model.MessageInput;
import com.ibm.watson.assistant.v2.model.MessageOptions;
import com.ibm.watson.assistant.v2.model.MessageResponse;
import com.ibm.watson.assistant.v2.model.SessionResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import br.com.robotnik.robotnikchat.R;
import br.com.robotnik.robotnikchat.view.Chat;
import br.com.robotnik.robotnikchat.view.ChatAdapter;

public class AssistantFactory extends AsyncTask<String, Void, String> {

    private com.ibm.watson.assistant.v2.Assistant assistant;
    private String url;
    private String apiKey;
    private String version;
    private String assistantId;
    private String resposta;
    private String pergunta;
    private RecyclerView chatRecyclerView;
    private List<Chat> chats;



    public AssistantFactory(RecyclerView view, List<Chat> chats) {
        this.url = "https://gateway.watsonplatform.net/assistant/api";
        this.apiKey = "FnNkUk_YRYZIZ3d-bWRqGtC6XAlE0zGpZD9NTSVOcUi_";
        this.version = "2019-02-28";
        this.assistantId = "315959f5-1d0d-4875-9fef-2fc41ed83c5c";
        this.chatRecyclerView = view;
        this.chats = chats;

        //IamOptions options = new IamOptions.Builder()
                //.apiKey(apiKey)
                //.build();
        //this.assistant = new com.ibm.watson.assistant.v2.Assistant(version, options);
        //this.assistant.setEndPoint(url);
        //Log.i("AssistantFactory","Construtor iniciado com suessso.");
    }

    @Override
    protected String doInBackground(String... strings) {

        IamOptions iamOptions = new IamOptions.Builder().apiKey(this.apiKey).build();
        com.ibm.watson.assistant.v2.Assistant service = new Assistant("2019-02-28", iamOptions);
        service.setEndPoint(this.url);

        CreateSessionOptions options = new CreateSessionOptions.Builder(this.assistantId).build();
        SessionResponse response = service.createSession(options).execute().getResult();

        MessageInput input = new MessageInput.Builder()
                .messageType("text")
                .text(strings[0]) //string da pergunta
                .build();

        MessageOptions messageOptions = new MessageOptions.Builder(this.assistantId, response.getSessionId())
                .input(input)
                .build();

        MessageResponse resposta = service.message(messageOptions).execute().getResult();
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

            String resposta = json.getJSONObject("output")
                    .getJSONArray("generic")
                    .getJSONObject(0)
                    .get("text")
                    .toString();
            int intent = json.getJSONObject("output")
                    .getJSONArray("intents")
                    .length();


            if(chats != null)//foi usado o construtor que traz o Array de Chats, msg do Bot
                if(intent == 0) { //se o intent for 0, é pq a pergunta não foi reconhecida e deve ser digitada novamente
                    ((EditText) ((LinearLayout) ((LinearLayout) chatRecyclerView.getParent().getParent()).getChildAt(1)).getChildAt(0)).setEnabled(true);
                    chats.add(new Chat(resposta, 2));
                    ((ChatAdapter) chatRecyclerView.getAdapter()).incrementTentativa();
                }else{
                    chats.add(new Chat(resposta, 0));
                }

            chatRecyclerView.getAdapter().notifyDataSetChanged();
            chatRecyclerView.scrollToPosition(chats.size() - 1);
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }
}