package com.example.gsb_menu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class homeFragment extends Fragment {

    private TextView testData;
    private SquareView squareView2;
    private SquareView squareView3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Récupération des vues carrées
        testData = view.findViewById(R.id.testdata);
        squareView2 = view.findViewById(R.id.squareView2);
        squareView3 = view.findViewById(R.id.squareView3);

        // Requête HTTP pour récupérer le nombre total d'utilisateurs
        // Requête HTTP pour récupérer le tableau JSON contenant les résultats
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "http://gsb-sciencesu.alwaysdata.net/API/get_counts.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Extraction des résultats du tableau JSON
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int count_compterendus = jsonObject.getInt("count_compterendus");
                            int count_utilisateurs = jsonObject.getInt("count_utilisateurs");

                            // Affichage des résultats dans des TextViews différents
                            TextView textView1 = view.findViewById(R.id.testdata);
                            TextView textView2 = view.findViewById(R.id.textview2);
                            textView1.setText("Nombre de comptes rendus : " + count_compterendus);
                            textView2.setText("Nombre d'utilisateurs : " + count_utilisateurs);
                        } catch (JSONException e) {
                            // Gestion des erreurs
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Gestion des erreurs
            }
        });

// Ajout de la requête à la file d'attente
        queue.add(stringRequest);

        // Ajout du chiffre au centre de chaque carré
        squareView2.setText("fais");
        squareView3.setText("test");

        return view;
    }
}
