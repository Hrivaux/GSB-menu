package com.example.gsb_menu;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

public class loginActivity extends AppCompatActivity {

    EditText username, password;
    Button login;
    String userVar, passVar;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        username = findViewById(R.id.usernameEditText);
        password = findViewById(R.id.passwordEditText);
        login = findViewById(R.id.loginButton);

        progressDialog = new ProgressDialog(loginActivity.this);
        progressDialog.setMessage("Connexion en cours... Veuillez patienter");
        progressDialog.setCancelable(false);

        requestQueue = Volley.newRequestQueue(loginActivity.this);

        login.setOnClickListener(view -> {
            userVar = username.getText().toString();
            passVar = password.getText().toString();
            if (userVar.equals("")) {
                Toast.makeText(getApplicationContext(), "Votre mail n'est pas renseigné", Toast.LENGTH_SHORT).show();
            } else if (passVar.equals("")) {
                Toast.makeText(getApplicationContext(), "Votre mot de passe n'est pas renseigné", Toast.LENGTH_SHORT).show();
            } else {
                progressDialog.show();
                loginRequest();
            }
        });

    }


    private void loginRequest() {
        String loginUrl = "https://hugo-rivaux.fr/API/login.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, loginUrl,
                response -> {
                    progressDialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        int status = jsonObject.getInt("status");
                        if (status == HttpURLConnection.HTTP_OK) {
                            username.setText("");
                            password.setText("");
                            Intent intentAccueil = new Intent(loginActivity.this, MainActivity.class);

                            startActivity(intentAccueil);
                        } else {
                            Toast.makeText(loginActivity.this, "Email ou mot de passe inccorect ", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(loginActivity.this, "Échec de l'analyse de la réponse du serveur", Toast.LENGTH_LONG).show();
                    }
                },
                error -> {
                    progressDialog.dismiss();
                    error.printStackTrace();
                    Toast.makeText(loginActivity.this, "Échec de connexion au serveur. S'il vous plaît, vérifiez votre connexion à internet et réessayez.", Toast.LENGTH_LONG).show();
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", userVar);
                params.put("password", passVar);
                return params;
            }
        };

        progressDialog.show();
        requestQueue.add(stringRequest);
    }

}