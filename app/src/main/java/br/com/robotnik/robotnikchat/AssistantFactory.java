package br.com.robotnik.robotnikchat;

import android.os.AsyncTask;
import android.util.Log;

import com.ibm.cloud.sdk.core.service.security.IamOptions;
import com.ibm.watson.assistant.v2.Assistant;
import com.ibm.watson.assistant.v2.model.CreateSessionOptions;
import com.ibm.watson.assistant.v2.model.MessageInput;
import com.ibm.watson.assistant.v2.model.MessageOptions;
import com.ibm.watson.assistant.v2.model.MessageResponse;
import com.ibm.watson.assistant.v2.model.SessionResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AssistantFactory extends AsyncTask<String, Void, String> {

    private Assistant assistant;
    private String url;
    private String apiKey;
    private String version;
    private String assistantId;
    private String resposta;
    private String pergunta;

    public AssistantFactory() {
        this.url = "https://gateway.watsonplatform.net/assistant/api";
        this.apiKey = "FnNkUk_YRYZIZ3d-bWRqGtC6XAlE0zGpZD9NTSVOcUi_";
        this.version = "2019-02-28";
        this.assistantId = "315959f5-1d0d-4875-9fef-2fc41ed83c5c";

        IamOptions options = new IamOptions.Builder()
                .apiKey(apiKey)
                .build();
        this.assistant = new Assistant(version, options);
        this.assistant.setEndPoint(url);
        Log.i("AssistantFactory","Construtor iniciado com suessso.");

    }

    @Override
    protected String doInBackground(String... strings) {

        IamOptions iamOptions = new IamOptions.Builder().apiKey(this.apiKey).build();
        Assistant service = new Assistant("2019-02-28", iamOptions);
        service.setEndPoint(this.url);

        CreateSessionOptions options = new CreateSessionOptions.Builder(this.assistantId).build();

        SessionResponse response = service.createSession(options).execute().getResult();

        System.out.println(response);

        MessageInput input = new MessageInput.Builder()
                .messageType("text")
                .text("Meu computador está lento")
                .build();

        MessageOptions messageOptions = new MessageOptions.Builder(this.assistantId, response.getSessionId())
                .input(input)
                .build();

        MessageResponse resposta = this.assistant.message(messageOptions).execute().getResult();
        Log.v("Mensagem", resposta.toString());

        return jsonParser(resposta.toString());//resposta.toString();
    }

    private String jsonParser(String resultado){
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
            return resposta;
            //weatherAdapter.notifyDataSetChanged();
        }
        catch (JSONException e){
            e.printStackTrace();
            return "";
        }

    }
}
