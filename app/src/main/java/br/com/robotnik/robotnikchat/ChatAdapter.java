package br.com.robotnik.robotnikchat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
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
            chat = v.findViewById(R.id.botChatTextView);
            logo = v.findViewById(R.id.botChatImageView);
        }
    }
    //quando um viewholder for criado


    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        View raiz = inflater.inflate(R.layout.bot_chat_view, viewGroup, false);
         Log.v("viewholder","onCreateViewHolder "+i);

        return new ChatViewHolder(raiz);
    }
    //quando um viewholder for vinculado ao recyclerview


    @Override
    public void onBindViewHolder(ChatViewHolder chatViewHolder, int i) {
    Log.v("viewholder","onBindViewHolder "+i);
        Chat chatAtual = chats.get(i);
        chatViewHolder.chat.setText(chats.get(i).getMensagem());

    }
    //qual o total de elementos?
    @Override

    public int getItemCount() {
        return chats.size();
    }



}