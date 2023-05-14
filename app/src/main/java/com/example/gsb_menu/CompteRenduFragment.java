package com.example.gsb_menu;

import static com.example.gsb_menu.Parametre.userID;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CompteRenduFragment extends Fragment {

    private static final String TAG = "TableDataActivity";
    private static final String URL = "https://gsb.gabin-tournier.fr/API/afficherCR.php";
    private RequestQueue mRequestQueue;
    private TableLayout mTableLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_compte_, container, false);
        mTableLayout = view.findViewById(R.id.table_layout);
        mRequestQueue = Volley.newRequestQueue(getActivity());
        fetchDataFromApi();
        return view;
    }

    private void fetchDataFromApi() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);

                                int id = jsonObject.optInt("id", 0);

                                String idVisiteur = jsonObject.optString("nom_visiteur", "");

                                String idMedecin = jsonObject.optString("nom_medecin", "");

                                String date = jsonObject.optString("date", "");
                                String idEchantillon = jsonObject.optString("nom_medicament", "");
                                String nouvelleVisite = jsonObject.optString("nouvelle_visite", "");
                                String compteRendu = jsonObject.optString("compte_rendu", "");
                                String avis = jsonObject.optString("avis", "");
                                String etat = jsonObject.optString("etat", "");
                                String idMotif = jsonObject.optString("libelle_motif", "");

                                TableRow tableRow = new TableRow(getActivity());

                                TextView textViewId = new TextView(getActivity());
                                textViewId.setText(String.valueOf(id));
                                textViewId.setPadding(8, 8, 8, 8);
                                tableRow.addView(textViewId);

                                TextView textViewIdVisiteur = new TextView(getActivity());
                                textViewIdVisiteur.setText(String.valueOf(idVisiteur));
                                textViewIdVisiteur.setPadding(8, 8, 8, 8);
                                tableRow.addView(textViewIdVisiteur);

                                TextView textViewIdMedecin = new TextView(getActivity());
                                textViewIdMedecin.setText(String.valueOf(idMedecin));
                                textViewIdMedecin.setPadding(8, 8, 8, 8);
                                tableRow.addView(textViewIdMedecin);

                                TextView textViewDate = new TextView(getActivity());
                                textViewDate.setText(date);
                                textViewDate.setPadding(8, 8, 8, 8);
                                tableRow.addView(textViewDate);

                                TextView textViewIdEchantillon = new TextView(getActivity());
                                textViewIdEchantillon.setText(String.valueOf(idEchantillon));
                                textViewIdEchantillon.setPadding(8, 8, 8, 8);
                                tableRow.addView(textViewIdEchantillon);

                                TextView textViewNouvelleVisite = new TextView(getActivity());
                                textViewNouvelleVisite.setText(nouvelleVisite);
                                textViewNouvelleVisite.setPadding(8, 8, 8, 8);
                                tableRow.addView(textViewNouvelleVisite);

                                TextView textViewCompteRendu = new TextView(getActivity());
                                textViewCompteRendu.setText(compteRendu);
                                textViewCompteRendu.setPadding(8, 8, 8, 8);
                                tableRow.addView(textViewCompteRendu);

                                TextView textViewAvis = new TextView(getActivity());
                                if (etat.equals("0")) {
                                    textViewAvis.setText("Défavorable");
                                } else {
                                    textViewAvis.setText("Favorable");
                                }
                                textViewAvis.setPadding(8, 8, 8, 8);
                                tableRow.addView(textViewAvis);

                                TextView textViewEtat = new TextView(getActivity());
                                if (etat.equals("0")) {
                                    textViewEtat.setText("En cours");
                                } else {
                                    textViewEtat.setText("Terminé");
                                }
                                textViewEtat.setPadding(8, 8, 8, 8);
                                tableRow.addView(textViewEtat);

                                TextView textViewIdMotif = new TextView(getActivity());
                                textViewIdMotif.setText(String.valueOf(idMotif));
                                textViewIdMotif.setPadding(8, 8, 8, 8);
                                tableRow.addView(textViewIdMotif);

                                mTableLayout.addView(tableRow);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG, "Error while parsing JSON response: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.e(TAG, "Error while fetching data from API: " + error.getMessage());
                    }

                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id_user", String.valueOf(userID));

                return params;
            }
        };
}}

/* public class MyFragment extends Fragment {

    private static final String URL = "https://example.com/api.php";
    private int userID;

    public MyFragment(int userID) {
        this.userID = userID;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container, false);

        // Récupération de la table
        TableLayout tableLayout = view.findViewById(R.id.table_layout);

        // Création de la requête POST
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            // Ajout des données de la réponse à la table
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                // Création d'une nouvelle ligne
                                TableRow tableRow = new TableRow(getContext());

                                // Ajout de chaque donnée dans une cellule
                                TextView idTextView = new TextView(getContext());
                                idTextView.setText(jsonObject.getString("id"));
                                tableRow.addView(idTextView);

                                TextView visiteurTextView = new TextView(getContext());
                                visiteurTextView.setText(jsonObject.getString("nom_visiteur"));
                                tableRow.addView(visiteurTextView);

                                TextView medecinTextView = new TextView(getContext());
                                medecinTextView.setText(jsonObject.getString("nom_medecin"));
                                tableRow.addView(medecinTextView);

                                TextView dateTextView = new TextView(getContext());
                                dateTextView.setText(jsonObject.getString("date"));
                                tableRow.addView(dateTextView);

                                TextView echantillonTextView = new TextView(getContext());
                                echantillonTextView.setText(jsonObject.getString("nom_medicament"));
                                tableRow.addView(echantillonTextView);

                                TextView nouvelleVisiteTextView = new TextView(getContext());
                                nouvelleVisiteTextView.setText(jsonObject.getString("nouvelle_visite"));
                                tableRow.addView(nouvelleVisiteTextView);

                                TextView compteRenduTextView = new TextView(getContext());
                                compteRenduTextView.setText(jsonObject.getString("compte_rendu"));
                                tableRow.addView(compteRenduTextView);

                                TextView avisTextView = new TextView(getContext());
                                avisTextView.setText(jsonObject.getString("avis"));
                                tableRow.addView(avisTextView);

                                TextView etatTextView = new TextView(getContext());
                                etatTextView.setText(jsonObject.getString("etat"));
                                tableRow.addView(etatTextView);

                                TextView motifTextView = new TextView(getContext());
                                motifTextView.setText(jsonObject.getString("libelle_motif"));
                                tableRow.addView(motifTextView);

                                // Ajout de la ligne à la table
                                tableLayout.addView(tableRow);
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
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id_user", String.valueOf(userID));

                return params;
            }
        };

        // Ajout de la requête à la queue de Volley
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);

        return view;
    }
}*/
