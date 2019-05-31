package br.com.robotnik.robotnikchat.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import br.com.robotnik.robotnikchat.R;
import br.com.robotnik.robotnikchat.model.Interacao;
import br.com.robotnik.robotnikchat.model.Sessao;
import br.com.robotnik.robotnikchat.model.SessaoDAO;

public class ReportActivity extends AppCompatActivity {

    private List<Report> reports;
    private List<Sessao> sessoes;
    private RecyclerView reportsRecyclerView;
    private ReportAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        reports = new ArrayList<>();

        SessaoDAO sessaoDAO = new SessaoDAO(this);
        sessoes = sessaoDAO.buscaSessaoPorData("","");
        Gson obj = new Gson();
        Log.v("sessoes",""+ obj.toJson(sessoes));

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

        adapter = new ReportAdapter(reports, this);
        reportsRecyclerView = findViewById(R.id.reportRecyclerView);
        reportsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        reportsRecyclerView.setAdapter(adapter);
    }
}

