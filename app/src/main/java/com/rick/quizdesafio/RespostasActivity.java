package com.rick.quizdesafio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RespostasActivity extends AppCompatActivity {
    private int countCorrect;
    private int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_respostas);

        Bundle answers = getIntent().getExtras();
        countCorrect = answers.getInt("countCorrect");
        size = answers.getInt("size");

        float result = calculaPercentage(countCorrect, size);

        TextView view = findViewById(R.id.textView);
        view.setText(String.format("%.0f%% \nde Acertos!", result));

        Button btnRestart = findViewById(R.id.btn_restart);
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private float calculaPercentage (int countCorrect, int size) {
        return countCorrect * 100 / size;
    }
}