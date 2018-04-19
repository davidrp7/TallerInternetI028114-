package com.example.aula7.tallerinterneti028114;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aula7.tallerinterneti028114.Http.HttpManager;
import com.example.aula7.tallerinterneti028114.Models.Persons;
import com.example.aula7.tallerinterneti028114.Parser.JsonPersons;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    TextView textView;

    List<Persons> personsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.id_pb_data);
        textView = (TextView) findViewById(R.id.id_tv_data);

        if (isOnLine()) {
            TaskPersons taskPersons = new TaskPersons();
            taskPersons.execute("http://pastoral.iucesmag.edu.co/practica/listar.php");

        } else {
            Toast.makeText(this, "Sin conexion", Toast.LENGTH_SHORT).show();
        }

    }

    public Boolean isOnLine() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null) {
            return true;
        } else {
            return false;
        }
    }

    public void processData() {

        for (Persons prs : personsList) {
            textView.append((prs.getCodigo() + "\n"));
            textView.append((prs.getNombre() + "\n"));
            textView.append((prs.getEdad() + "\n"));
            textView.append((prs.getCorreo() + "\n"));
            textView.append((prs.getPass() + "\n"));

        }
    }

    public class TaskPersons extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {
            String content = null;
            try {
                content = HttpManager.getDataJson(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content;


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                personsList = JsonPersons.getData(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            processData();
            progressBar.setVisibility(View.GONE);

        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }
    }
}
