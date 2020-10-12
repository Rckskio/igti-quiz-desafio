package com.rick.quizdesafio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView verdadeiro;
    private TextView falso;
    private TextView question;
    private final ArrayList<String> perguntas = new ArrayList<>();
    private final ArrayList<String> respostas = new ArrayList<>();
    private final String fileName = "perguntas.txt";
    private int countCorrect = 0;
    final int[] rodada = {0};
    private final String keyFirstLogin = "FIRST_TIME_SPLASH";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verdadeiro = findViewById(R.id.text_true);
        falso = findViewById(R.id.text_false);
        question = findViewById(R.id.text_questions);
        try {
            readFile(StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        rodada[0] = 0;
        countCorrect = 0;

        if (!perguntas.isEmpty()){
            String primeiraPergunta = (String) perguntas.toArray()[rodada[0]];
            question.setText(primeiraPergunta);
        }

        verdadeiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                responder(verdadeiro.getText().toString());
            }
        });

        falso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                responder(falso.getText().toString());
            }
        });
    }

    private boolean responder(String resposta) {
        if (rodada[0] < perguntas.size() - 1) {
            if (verificaResposta(resposta)) {
                countCorrect++;
                Toast displayToast = Toast.makeText(getApplicationContext(), "Acertou!", Toast.LENGTH_SHORT);
                displayToast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                displayToast.show();
            } else {
                Toast displayToast = Toast.makeText(getApplicationContext(), "Errou!", Toast.LENGTH_SHORT);
                displayToast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                displayToast.show();
            }
            rodada[0]++;
            String currentQuestion = (String) perguntas.toArray()[rodada[0]];
            question.setText(currentQuestion);
        } else {
            if (verificaResposta(resposta)) {
                countCorrect++;
                Toast displayToast = Toast.makeText(getApplicationContext(), "Acertou!", Toast.LENGTH_SHORT);
                displayToast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                displayToast.show();
            } else {
                Toast displayToast = Toast.makeText(getApplicationContext(), "Errou!", Toast.LENGTH_SHORT);
                displayToast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                displayToast.show();
            }
            Bundle answers = new Bundle();
            answers.putInt("countCorrect", countCorrect);
            answers.putInt("size", perguntas.size());
            Intent intent = new Intent(this, RespostasActivity.class);
            intent.putExtras(answers);
            startActivity(intent);
        }

        return false;
    }

    private boolean verificaResposta (String resposta) {
        if (resposta.equals(respostas.toArray()[rodada[0]])){
            return true;
        }
        return false;
    }

    public void readFile(Charset encoding) throws IOException {
        String[] leitura;
        StringBuilder sb = new StringBuilder();
        InputStream fileStream = getAssets().open(fileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(fileStream, encoding));

        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line + System.lineSeparator());
            leitura = line.split("; ");
            perguntas.add(leitura[0]);
            respostas.add(leitura[1]);
        }
    }
}