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

public class add_compterenduFragment extends Fragment {

    private EditText etNomMedecin;
    private EditText etDateCR;
    private EditText etEchantillonTest;
    private EditText etMotifVisite;
    private EditText etAvis;
    private EditText etEtat;
    private EditText etNouvelleVisite;
    private EditText etCommentaire;

    private Button btnAjoutCR;
    private RequestQueue mRequestQueue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_compte_rendu, container, false);

        etNomMedecin = rootView.findViewById(R.id.nom_medEditText);
        etDateCR = rootView.findViewById(R.id.date_crEditText);
        etEchantillonTest = rootView.findViewById(R.id.echantillon_testEditText);
        etMotifVisite = rootView.findViewById(R.id.motif_visiteEditText);
        etAvis = rootView.findViewById(R.id.avisEditText);
        etEtat = rootView.findViewById(R.id.etatEditText);
        etNouvelleVisite = rootView.findViewById(R.id.nouvelle_visiteEditText);
        etCommentaire = rootView.findViewById(R.id.commentaireEditText);
        btnAjoutCR = rootView.findViewById(R.id.button_ajouter_cr);

        mRequestQueue = Volley.newRequestQueue(requireContext());
        btnAjoutCR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomUtilisateur = etNomMedecin.getText().toString();
                String prenomUtilisateur = etDateCR.getText().toString();
                String emailUtilisateur = etEchantillonTest.getText().toString();
                String motDePasse = etMotifVisite.getText().toString();
                String adresseUtilisateur = etAvis.getText().toString();
                String ville = etEtat.getText().toString();
                String code_postal = etNouvelleVisite.getText().toString();
                String commentaire = etCommentaire.getText().toString();

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
