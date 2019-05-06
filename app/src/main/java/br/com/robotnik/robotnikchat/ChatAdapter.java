package br.com.robotnik.robotnikchat;

import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter <ChatAdapter.ChatViewHolder> {
    private List<Chat> chats;
    private Context context;

    public ChatAdapter (List <Chat> chats, Context context){
        this.chats = chats;
        this.context = context;
    }


    //viewholder
    class ChatViewHolder extends RecyclerView.ViewHolder{
       private TextView chat;
       private ImageView logo;

        ChatViewHolder (View v){

            super (v);
            chat.findViewById(R.id.botChatTextView);

        }
    }
    //quando um viewholder for criado



    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return null;
    }
    //quando um viewholder for vinculado ao recyclerview
    @Override
    public void onBindViewHolder(ChatViewHolder chatViewHolder, int i) {
    }
    //qual o total de elementos?
    @Override

    public int getItemCount() {
        return 0;
    }
}