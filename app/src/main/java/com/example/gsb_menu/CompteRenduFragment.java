package com.example.gsb_menu;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CompteRenduFragment extends Fragment {

    private static final String TAG = "TableDataActivity";
    private static final String URL = "https://hugo-rivaux.fr/API/afficherCR.php";
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

                                int id = jsonObject.optInt("C.id", 0);

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
                }
        );

        mRequestQueue.add(jsonArrayRequest);
    }
}

