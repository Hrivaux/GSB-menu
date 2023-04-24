package com.example.gsb_menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class list_medecins_Fragment extends Fragment {

        List<TextView> prenomTextViews;
        List<TextView> adresseTextViews;
        List<TextView> nomTextViews;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
                View view = inflater.inflate(R.layout.fragment_list_medecins_, container, false);

                prenomTextViews = new ArrayList<>();
                prenomTextViews.add((TextView) view.findViewById(R.id.Prenom0));
                prenomTextViews.add((TextView) view.findViewById(R.id.Prenom1));
                prenomTextViews.add((TextView) view.findViewById(R.id.Prenom2));
                prenomTextViews.add((TextView) view.findViewById(R.id.Prenom3));
                prenomTextViews.add((TextView) view.findViewById(R.id.Prenom4));
                prenomTextViews.add((TextView) view.findViewById(R.id.Prenom5));
                prenomTextViews.add((TextView) view.findViewById(R.id.Prenom6));
                prenomTextViews.add((TextView) view.findViewById(R.id.Prenom7));
                prenomTextViews.add((TextView) view.findViewById(R.id.Prenom8));

                adresseTextViews = new ArrayList<>();
                adresseTextViews.add((TextView) view.findViewById(R.id.Adresse0));
                adresseTextViews.add((TextView) view.findViewById(R.id.Adresse1));
                adresseTextViews.add((TextView) view.findViewById(R.id.Adresse2));
                adresseTextViews.add((TextView) view.findViewById(R.id.Adresse3));
                adresseTextViews.add((TextView) view.findViewById(R.id.Adresse4));
                adresseTextViews.add((TextView) view.findViewById(R.id.Adresse5));
                adresseTextViews.add((TextView) view.findViewById(R.id.Adresse6));
                adresseTextViews.add((TextView) view.findViewById(R.id.Adresse7));
                adresseTextViews.add((TextView) view.findViewById(R.id.Adresse8));

                nomTextViews = new ArrayList<>();
                nomTextViews.add((TextView) view.findViewById(R.id.Nom0));
                nomTextViews.add((TextView) view.findViewById(R.id.Nom1));
                nomTextViews.add((TextView) view.findViewById(R.id.Nom2));
                nomTextViews.add((TextView) view.findViewById(R.id.Nom3));
                nomTextViews.add((TextView) view.findViewById(R.id.Nom4));
                nomTextViews.add((TextView) view.findViewById(R.id.Nom5));
                nomTextViews.add((TextView) view.findViewById(R.id.Nom6));
                nomTextViews.add((TextView) view.findViewById(R.id.Nom7));
                nomTextViews.add((TextView) view.findViewById(R.id.Nom8));
                fetchMedecins();

                return view;
        }


        private void fetchMedecins() {
                String url = "https://hugo-rivaux.fr/API/medecin.php";
                RequestQueue queue = Volley.newRequestQueue(requireContext());

                StringRequest request = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                        try {
                                                JSONArray jsonArray = new JSONArray(response);
                                                List<String> prenoms = new ArrayList<>();
                                                List<String> adresses = new ArrayList<>();
                                                List<String> noms = new ArrayList<>();



                                                // Parcours du tableau de résultats
                                                for (int i = 0; i < jsonArray.length(); i++) {
                                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                                        prenoms.add(jsonObject.getString("prenom"));
                                                        adresses.add(jsonObject.getString("adresse"));
                                                        noms.add(jsonObject.getString("nom"));


                                                }

                                                // Affichage des prénoms et adresses dans les TextView correspondants
                                                for (int i = 0; i < prenomTextViews.size(); i++) {
                                                        if (i < prenoms.size()) {
                                                                prenomTextViews.get(i).setText(prenoms.get(i));
                                                                adresseTextViews.get(i).setText(adresses.get(i));
                                                                nomTextViews.get(i).setText(noms.get(i));

                                                        } else {
                                                                prenomTextViews.get(i).setText("");
                                                                adresseTextViews.get(i).setText("");
                                                                nomTextViews.get(i).setText("");

                                                        }
                                                }
                                        } catch (JSONException e) {
                                                e.printStackTrace();
                                        }
                                }
                        },
                        new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                        error.printStackTrace();
                                }
                        });

                queue.add(request);
        }
}
