package br.com.robotnik.robotnikchat.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Timestamp;
import java.util.List;

import br.com.robotnik.robotnikchat.control.AssistantFactory;
import br.com.robotnik.robotnikchat.R;
import br.com.robotnik.robotnikchat.control.ConnectionStateMonitor;
import br.com.robotnik.robotnikchat.model.Interacao;
import br.com.robotnik.robotnikchat.model.InteracaoDAO;
import br.com.robotnik.robotnikchat.model.Sessao;
import br.com.robotnik.robotnikchat.model.SessaoDAO;

public class ChatAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder> {
    private List<Chat> chats;
    private Context context;
    private Sessao sessao;
    private int tentativa;
    private ConnectionStateMonitor monitor;

    public ChatAdapter (List <Chat> chats, Sessao sessao, Context context){
        this.chats = chats;
        this.context = context;
        this.sessao = sessao;
        this.tentativa = 1;
        this.monitor = new ConnectionStateMonitor(context);
    }

    //viewholders personalizados para cada layout
    public class BotViewHolder extends RecyclerView.ViewHolder{
       private TextView mensagem;
       private ImageView logo;
       private Button yesButton;
       private Button noButton;
       private TextView lblYesNoTextView;


        public BotViewHolder (View v){
            super (v);
            yesButton = v.findViewById(R.id.yesButton);
            noButton = v.findViewById(R.id.noButton);
            mensagem = v.findViewById(R.id.botChatTextView);
            logo = v.findViewById(R.id.botChatImageView);
            lblYesNoTextView = v.findViewById(R.id.lblYesNoTextView);

            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   Log.v("botao yes", mensagem.getText().toString());
                   yesButton.setEnabled(false);
                   noButton.setEnabled(false);
                   //finalizar o atendimento
                    sessao.setFim(new Timestamp(System.currentTimeMillis()));

                    //exibir a mensagem de agradecimento
                    chats.add(new Chat("Obrigado por usar o Chatbot!, foi um prazer ajudá-lo!",2));
                    notifyDataSetChanged();
                    ((RecyclerView) v.getParent().getParentForAccessibility()).scrollToPosition(chats.size() -1);

                    //salvando sessão no banco
                    //adiciona a interação atual
                    sessao.getInteracoes().add(
                            new Interacao(
                                    sessao.getUsuario(),
                                    sessao.getId(),
                                    getUltimaPergunta(),
                                    mensagem.getText().toString(),
                                    1,
                                    tentativa));
                    tentativa++;
                    SessaoDAO sessaoDAO = new SessaoDAO(context);
                    sessaoDAO.insereSessao(sessao);
                }
            });


            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(tentativa <3 ) {
                        //verifica monitor de conexão
                        if(monitor.getConnectivityStatus()){
                            //realiza a pergunta na base e desabilita os botões de satisfação após o uso;
                            AssistantFactory assistant = new AssistantFactory(((RecyclerView) v.getParent().getParentForAccessibility()), chats);
                            assistant.execute(getUltimaPergunta());
                            yesButton.setEnabled(false);
                            noButton.setEnabled(false);

                            //adiciona a interação atual
                            sessao.getInteracoes().add(
                                    new Interacao(
                                            sessao.getUsuario(),
                                            sessao.getId(),
                                            getUltimaPergunta(),
                                            mensagem.getText().toString(),
                                            0,
                                            tentativa));
                            tentativa++;

                            //se depois do ultimo incremento (3), fecha a sessão
                            if(tentativa >=3)
                                sessao.setFechada(true);
                        }else{
                            Toast.makeText(context, "Verifique sua conexão com a internet", Toast.LENGTH_SHORT).show();
                        }

                    }else{ //sem solução
                        //finaliza sessão

                        //exibe a mensagem de utilização de outro meio
                        chats.add(new Chat("Contate o suporte Telefonico para encontrar a solução adequada. Atendimento finalizado",2));
                        notifyDataSetChanged();
                        ((RecyclerView) v.getParent().getParentForAccessibility()).scrollToPosition(chats.size() -1);

                        // salva a sessão no banco
                        sessao.getInteracoes().add(
                                new Interacao(
                                        sessao.getUsuario(),
                                        sessao.getId(),
                                        getUltimaPergunta(),
                                        mensagem.getText().toString(),
                                        0,
                                        tentativa));
                        tentativa++;
                        sessao.setFim(new Timestamp(System.currentTimeMillis()));
                        Log.v("salvando","salvando fim "+sessao.getFim());
                        SessaoDAO sessaoDAO = new SessaoDAO(context);
                        sessaoDAO.insereSessao(sessao);
                    }
                }
            });
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

    public class PopupViewHolder extends RecyclerView.ViewHolder{
        private TextView mensagem;
        private ImageView logo;

        public PopupViewHolder (View v){
            super (v);
            mensagem = v.findViewById(R.id.popupChatTextView);
            logo = v.findViewById(R.id.popupChatImageView);
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
                BotViewHolder bv = new BotViewHolder(raiz);

                return bv;//new BotViewHolder(raiz);

            case 1:
                inflater = LayoutInflater.from(context);
                raiz = inflater.inflate(R.layout.user_chat_view, viewGroup, false);
                return new UserViewHolder(raiz);

            case 2:
                inflater = LayoutInflater.from(context);
                raiz = inflater.inflate(R.layout.popup_chat_view, viewGroup, false);
                return new PopupViewHolder(raiz);
        }
            return null;//nao chegara aqui
    }
    //quando um viewholder for vinculado ao recyclerview
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder chatViewHolder, int i) {

        chatViewHolder.setIsRecyclable(false);
        switch (chatViewHolder.getItemViewType()) {

            case 0: //mensagem do bot
                Log.v("BIND", "sessao: "+sessao.isFechada());
                if(i == 0 ) {
                    //((BotViewHolder) chatViewHolder).yesButton.setText("modificado");
                    ((BotViewHolder) chatViewHolder).yesButton.setVisibility(View.GONE);
                    ((BotViewHolder) chatViewHolder).noButton.setVisibility(View.GONE);
                    ((BotViewHolder) chatViewHolder).lblYesNoTextView.setVisibility(View.GONE);
                }

                if(i+1 < chats.size()) {
                    ((BotViewHolder) chatViewHolder).yesButton.setEnabled(false);
                    ((BotViewHolder) chatViewHolder).noButton.setEnabled(false);
                }

                ((BotViewHolder) chatViewHolder).mensagem.setText(chats.get(i).getMensagem());
                break;
            case 1:
                ((UserViewHolder) chatViewHolder).mensagem.setText(chats.get(i).getMensagem());
                break;
            case 2:
                ((PopupViewHolder) chatViewHolder).mensagem.setText(chats.get(i).getMensagem());
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

    private String getUltimaPergunta(){
        //for regressivo até achar a ultima pergunta feita pelo usuario

        for (int i = chats.size(); i >= 0; --i ){
            if (chats.get(i-1).getSender() == 1)
                return chats.get(i-1).getMensagem();
        }
        return "x";
    }

    public List<Chat> getChats() {
        return chats;
    }

    public void setChats(List<Chat> chats) {
        this.chats = chats;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Sessao getSessao() {
        return sessao;
    }

    public void setSessao(Sessao sessao) {
        this.sessao = sessao;
    }

    public int getTentativa() {
        return tentativa;
    }

    public void incrementTentativa() {
        this.tentativa ++;
    }
}