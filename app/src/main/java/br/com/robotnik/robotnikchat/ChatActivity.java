package br.com.robotnik.robotnikchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ibm.watson.assistant.v1.Assistant;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        AssistantFactory assistant = new AssistantFactory();
        assistant.execute("");
    }
}
