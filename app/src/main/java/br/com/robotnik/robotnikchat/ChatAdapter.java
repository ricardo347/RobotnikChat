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

public class ChatAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder> {
    private List<Chat> chats;
    private Context context;

    public ChatAdapter (List <Chat> chats, Context context){
        this.chats = chats;
        this.context = context;
    }


    //viewholders personalizados para cada layout
    public class BotViewHolder extends RecyclerView.ViewHolder{
       private TextView mensagem;
       private ImageView logo;

        public BotViewHolder (View v){
            super (v);
            mensagem = v.findViewById(R.id.botChatTextView);
            logo = v.findViewById(R.id.botChatImageView);
        }
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{
        private TextView mensagem;
        private ImageView logo;

        public UserViewHolder (View v){
            super (v);
            mensagem = v.findViewById(R.id.userChatTextView);
            logo = v.findViewById(R.id.userChatImageView);
        }
    }

    //metodo que é chamado pelo onCreateViewHolder em seu parametro type
    @Override
    public int getItemViewType(int position){
        return chats.get(position).getSender();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int type)
    {   View raiz = null;
        LayoutInflater inflater;

        switch (type) {
            case 0:
                inflater = LayoutInflater.from(context);
                raiz = inflater.inflate(R.layout.bot_chat_view, viewGroup, false);
                return new BotViewHolder(raiz);
            case 1:
                inflater = LayoutInflater.from(context);
                raiz = inflater.inflate(R.layout.user_chat_view, viewGroup, false);
                return new UserViewHolder(raiz);
        }
            return null;//nao chegara aqui
    }
    //quando um viewholder for vinculado ao recyclerview
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder chatViewHolder, int i) {

        switch (chatViewHolder.getItemViewType()) {
            case 0: //mensagem do bot
                ((BotViewHolder) chatViewHolder).mensagem.setText(chats.get(i).getMensagem());
                break;
            case 1:
                ((UserViewHolder) chatViewHolder).mensagem.setText(chats.get(i).getMensagem());
                break;
            default:
                    break;
        }
    }

    //qual o total de elementos?
    @Override

    public int getItemCount() {
        return chats.size();
    }



}