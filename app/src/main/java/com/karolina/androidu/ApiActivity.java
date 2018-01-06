package com.karolina.androidu;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ApiActivity extends AppCompatActivity {

    /*********  dodać uses-permission w pliku manifestu   ********/
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.btnGetHTMLData)
    Button btnHTML;
    @BindView(R.id.textvResult)
    TextView textView;
    @BindView(R.id.btnJsonData)
    Button btnJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api);
        ButterKnife.bind(this);
        progressBar.setVisibility(View.INVISIBLE);

        btnHTML.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ThreadClass().execute("URL", "URL2", "URL3");
            }
        });

        btnJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ThreadClass().execute("URL", "URL2", "URL3");
            }
        });

    }

    private class ThreadClass extends AsyncTask<String, Integer, Float> {
        @Override
        protected void onPreExecute() {      //działa w wątku użytkownika
            progressBar.setVisibility(View.VISIBLE);
         //   textView.setText("" + System.currentTimeMillis());
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            // do metody wpadają wartości wywołane w doInBackground
            progressBar.setProgress(values[0]);
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Float f) {     //metoda wykonuje się po zakończeniu wywołania
            progressBar.setVisibility(View.INVISIBLE);
        //    textView.setText(textView.getText() + "  " + " " + System.currentTimeMillis());
            super.onPostExecute(f);
        }

        @Override
        protected void onCancelled(Float f) {
            // jeżeli w doInBackground zostanie wywołany cancel
            // tutaj trafia częściowy rezultat
            super.onCancelled(f);
        }

        @Override
        protected Float doInBackground(String... params123) {  //zwraca 3 parametr AsyncTask
            String url = params123[0];
            String a = url;
            for (int i = 0; i < 10000; i++) {
                a = a + url;
                if (i > 0 && i % 1000 == 0)
                    publishProgress(i / 100);
            }

            // cancel(true);
            return 0.0F;
        }
    }
}
