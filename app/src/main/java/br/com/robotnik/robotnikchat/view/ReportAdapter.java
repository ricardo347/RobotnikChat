package br.com.robotnik.robotnikchat.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import br.com.robotnik.robotnikchat.R;

public class ReportAdapter extends RecyclerView.Adapter <ReportAdapter.ReportViewHolder> {

    private List<Report> reports;
    private Context context;

    public ReportAdapter(List<Report> reports, Context context){
        this.reports = reports;
        this.context = context;
        //Log.v("ReportAdapter","Sessao " +reports.get(0).getSessao());
        //Log.v("ReportAdapter ","Usuario " +reports.get(1).getResolvido());
    }

    public class ReportViewHolder extends RecyclerView.ViewHolder{
        private TextView sessaoTextView;
        private TextView usuarioTextView;
        private TextView inicioTextView;
        private TextView fimTextView;
        private TextView pergutaTextView;
        private TextView respostaTextView;
        private TextView resolvidoTextView;
        private LinearLayout reportLinearLayout;

        public ReportViewHolder(View v){

            super(v);
            sessaoTextView = v.findViewById(R.id.rptSessaoTextView);
            usuarioTextView = v.findViewById(R.id.rptUsuarioTextView);
            inicioTextView = v.findViewById(R.id.rptInicioTextView);
            fimTextView = v.findViewById(R.id.rptFimTextView);
            pergutaTextView = v.findViewById(R.id.rptPerguntaTextView);
            respostaTextView = v.findViewById(R.id.rptRespostaTextView);
            resolvidoTextView = v.findViewById(R.id.rptTentativaTextView);
            reportLinearLayout = v.findViewById(R.id.rptLinearLayout);

        }
    }


    @Override
    public ReportViewHolder onCreateViewHolder(ViewGroup viewGroup, int type){
        View raiz = null;
        LayoutInflater inflater;

        inflater = LayoutInflater.from(context);
        raiz = inflater.inflate(R.layout.item_report_view, viewGroup, false);
        return new ReportViewHolder(raiz);

    }

    @Override
    public void onBindViewHolder(ReportViewHolder reportViewHolder, int i) {

        reportViewHolder.sessaoTextView.setText(String.valueOf(reports.get(i).getSessao()));
        reportViewHolder.usuarioTextView.setText(reports.get(i).getUsuario());
        reportViewHolder.inicioTextView.setText(reports.get(i).getInicio());
        reportViewHolder.fimTextView.setText(reports.get(i).getFim());
        reportViewHolder.pergutaTextView.setText(reports.get(i).getPergunta());
        reportViewHolder.respostaTextView.setText(reports.get(i).getResposta());
        reportViewHolder.resolvidoTextView.setText(reports.get(i).getResolvido() > 0 ? String.valueOf(reports.get(i).getResolvido()) : "N/A");
        if (reports.get(i).getResolvido() > 0) {
            reportViewHolder.reportLinearLayout.setBackgroundResource(R.color.Resolvido);
        } else {
            reportViewHolder.reportLinearLayout.setBackgroundResource(R.color.NaoResolvido);
        }

    }

    @Override

    public int getItemCount() {
        return reports.size();
    }

}
