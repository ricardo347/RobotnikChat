package br.com.robotnik.robotnikchat;

import android.util.Log;

import com.ibm.cloud.sdk.core.service.security.IamOptions;
import com.ibm.watson.assistant.v2.Assistant;
import com.ibm.watson.assistant.v2.model.MessageInput;
import com.ibm.watson.assistant.v2.model.MessageOptions;
import com.ibm.watson.assistant.v2.model.MessageResponse;

public class AssistantFactory {

    private Assistant assistant;
    private String url;
    private String apiKey;
    private String version;

    public AssistantFactory(){
        this.url = "https://gateway.watsonplatform.net/assistant/api";
        this.apiKey = "FnNkUk_YRYZIZ3d-bWRqGtC6XAlE0zGpZD9NTSVOcUi_";
        this.version = "2019-02-28";

        IamOptions options = new IamOptions.Builder()
                .apiKey(apiKey)
                .build();
        this.assistant = new Assistant(version,options);
        this.assistant.setEndPoint(url);

        MessageInput input = new MessageInput.Builder()
                .messageType("text")
                .text("Oi tudo bem?")
                .build();

        MessageOptions messageOptions = new MessageOptions.Builder("{assistant_id}", "{session_id}")
                .input(input)
                .build();

        MessageResponse response = this.assistant.message(messageOptions).execute().getResult();
        Log.v("Mensagem",response.toString());


    }
}
