package br.com.robotnik.robotnikchat.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.robotnik.robotnikchat.R;
import br.com.robotnik.robotnikchat.model.Interacao;
import br.com.robotnik.robotnikchat.model.Sessao;
import br.com.robotnik.robotnikchat.model.SessaoDAO;

public class ReportActivity extends AppCompatActivity {

    private List<Report> reports;
   // private List<Sessao> sessoes;
    private RecyclerView reportsRecyclerView;
    private ReportAdapter adapter;
    private TextView rptQtdResolvido1TextView;
    private TextView rptQtdResolvido2TextView;
    private TextView rptQtdResolvido3TextView;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        reports = new ArrayList<>();//getSessoesFiltradasPorData("30-05-2019 00:00:00","31-05-2019 00:00:00");

        adapter = new ReportAdapter(reports, this);
        reportsRecyclerView = findViewById(R.id.reportRecyclerView);
        reportsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        reportsRecyclerView.setAdapter(adapter);


        spinner = findViewById(R.id.rptSpinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("spinner", ""+ parent.getItemAtPosition(position).toString());

                Timestamp ts = new Timestamp(System.currentTimeMillis());
                Calendar cal = Calendar.getInstance();
                //SimpleDateFormat sdf =  new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                String inicio = "";

                switch (position){
                    case 0: //diario

                        cal.setTimeInMillis(ts.getTime());
                        cal.add(Calendar.DAY_OF_MONTH, -1);
                        inicio =  new Timestamp(cal.getTime().getTime()).toString();
                        break;
                    case 1: //semanal

                        cal.setTimeInMillis(ts.getTime());
                        cal.add(Calendar.DAY_OF_MONTH, -7);
                        inicio =  new Timestamp(cal.getTime().getTime()).toString();
                        break;
                    case 2: //mensal

                        cal.setTimeInMillis(ts.getTime());
                        cal.add(Calendar.MONTH, -1);
                        inicio =  new Timestamp(cal.getTime().getTime()).toString();

                        break;
                }
                reports.clear();
                reports = getSessoesFiltradasPorData(inicio, new Timestamp(System.currentTimeMillis()).toString());

                adapter.notifyDataSetChanged();
                Log.v("tempo","inicio "+ new Timestamp(cal.getTime().getTime()).toString() +"fim "+ new Timestamp(System.currentTimeMillis()).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public List<Report> getSessoesFiltradasPorData(String inicio, String fim){

        SessaoDAO sessaoDAO = new SessaoDAO(this);
        List<Sessao> sessoes = sessaoDAO.getSessaoPorData(inicio, fim);
        //List<Report> reports = new ArrayList<>();

        for (Sessao sessao : sessoes){
            Interacao i = sessao.getInteracaoResolvida();
            reports.add(new Report(

                    sessao.getId(),
                    sessao.getUsuario().getNome(),
                    sessao.getInicio(),
                    sessao.getFim(),
                    i == null ? "" : i.getResposta(),
                    sessao.getInteracoes().get(0).getPergunta(),
                    i == null ? 0 : i.getNumtentativa()
            ));
        }

        return reports;
    }

    public int getTotalResovidas(int tentativa){
        int result = 0;
        SessaoDAO sessaoDAO = new SessaoDAO(this);
        List<Sessao> sessoes = sessaoDAO.getTodasSessoes();

        for(Sessao sessao : sessoes){
            for (Interacao i :  sessao.getInteracoes()){
                if(i.getNumtentativa() == tentativa && i.getSatisfatoria() == 1){
                    result ++;
                }
            }
        }

        return result;
    }

}

