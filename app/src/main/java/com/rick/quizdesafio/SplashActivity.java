package com.rick.quizdesafio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

public class SplashActivity extends AppCompatActivity {

    private final String keyFirstLogin = "FIRST_TIME_SPLASH";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashScreenTimer();
    }

    private void redirectActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    // Essa função adiciona um delay apos o loading do aplicativo, para redirecionar para a proxima activity
    private void splashScreenTimer() {

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                redirectActivity();
                finish();
            }
        }, getSplashTimer());
    }

    // Retorna um numero do tipo long dependendo de uma condição que é adiquirida atravez do SharedPreferences
    // Caso for a primeira vez executando o aplicativo, ira retornar true para a condição firstTime com isso
    // ira retornar o tempo de 5000 milisegundos, caso não seja a primeira vez abrindo o aplicativo, ira retornar 1000 milisegundos
    private long getSplashTimer() {
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        // Acessa sharedPreferences e pega o valor do tipo boolean de uma sharedPreference com chave FIRST_TIME_SPLASH, caso não exista
        // retorna true por default;
        Boolean firstTime = sharedPreferences.getBoolean(keyFirstLogin, true);

        // Se for a primeira vez abrindo o aplicativo
        if (firstTime) {
            // Alterar o valor do boolean com chave FIRST_TIME_SPLASH para false, para que na proxima vez não entre nessa condição.
            editor.putBoolean(keyFirstLogin, false);
            editor.commit();
            final int i = 1000;
            return i;
        }
        // Retorna esse valor quando firstTime retornar false, porque náo é a primeira vez que esta logando ou abrindo o aplicativo
        return 0;
    }
}