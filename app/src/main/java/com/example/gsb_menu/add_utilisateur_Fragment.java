package com.example.gsb_menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class add_utilisateur_Fragment extends Fragment {
        private EditText etNomUtilisateur;
        private EditText etMotDePasse;
        private EditText etVille;
        private EditText etPrenomUtilisateur;
        private EditText etEmailUtilisateur;
        private EditText etAdresseUtilisateur;
        private EditText etCodepostal;

        private Button btnInscription;
        private RequestQueue mRequestQueue;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                View rootView = inflater.inflate(R.layout.fragment_add_utilisateur_, container, false);

                etNomUtilisateur = rootView.findViewById(R.id.nomEditText);
                etPrenomUtilisateur = rootView.findViewById(R.id.prenomEditText);
                etEmailUtilisateur = rootView.findViewById(R.id.emailEditText);
                etMotDePasse = rootView.findViewById(R.id.passwordEditText);
                etAdresseUtilisateur = rootView.findViewById(R.id.adresseEditText);
                etVille = rootView.findViewById(R.id.villeEditText);
                etCodepostal = rootView.findViewById(R.id.codePostalEditText);
                btnInscription = rootView.findViewById(R.id.button_ouvrir_compte);

                mRequestQueue = Volley.newRequestQueue(requireContext());
                btnInscription.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                String nomUtilisateur = etNomUtilisateur.getText().toString();
                                String prenomUtilisateur = etPrenomUtilisateur.getText().toString();
                                String emailUtilisateur = etEmailUtilisateur.getText().toString();
                                String motDePasse = etMotDePasse.getText().toString();
                                String adresseUtilisateur = etAdresseUtilisateur.getText().toString();
                                String ville = etVille.getText().toString();
                                String code_postal = etCodepostal.getText().toString();

                                String url = "https://hugo-rivaux.fr/API/inscription.php";

                                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                        new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                        if (response.equals("success")) {
                                                                Toast.makeText(getContext(), "Inscription réussie!", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                                Toast.makeText(getContext(), "Erreur lors de l'inscription", Toast.LENGTH_SHORT).show();
                                                        }
                                                }
                                        }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                                Toast.makeText(getContext(), "Erreur lors de l'inscription", Toast.LENGTH_SHORT).show();
                                        }
                                }) {
                                        @Override
                                        protected Map<String, String> getParams() {
                                                Map<String, String> params = new HashMap<>();
                                                params.put("nom_utilisateur", nomUtilisateur);
                                                params.put("prenom_utilisateur", prenomUtilisateur);
                                                params.put("email_utilisateur", emailUtilisateur);
                                                params.put("mot_de_passe", motDePasse);
                                                params.put("adresse_utilisateur", adresseUtilisateur);
                                                params.put("ville", ville);
                                                params.put("code_postal", code_postal);
                                                return params;
                                        }
                                };
                                mRequestQueue.add(stringRequest);
                        }
                });

                return rootView;
        }
}
