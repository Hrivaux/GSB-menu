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

                                if (!jsonObject.has("id")) {
                                    Log.e(TAG, "Error while parsing JSON response: No value for id");
                                    continue;
                                }
                                int id = jsonObject.getInt("id");

                                if (!jsonObject.has("id_visiteur")) {
                                    Log.e(TAG, "Error while parsing JSON response: No value for id_visiteur");
                                    continue;
                                }
                                int idVisiteur = jsonObject.getInt("id_visiteur");

                                if (!jsonObject.has("id_medecin")) {
                                    Log.e(TAG, "Error while parsing JSON response: No value for id_medecin");
                                    continue;
                                }
                                int idMedecin = jsonObject.getInt("id_medecin");

                                if (!jsonObject.has("date")) {
                                    Log.e(TAG, "Error while parsing JSON response: No value for date");
                                    continue;
                                }
                                String date = jsonObject.getString("date");

                                if (!jsonObject.has("id_echantillon")) {
                                    Log.e(TAG, "Error while parsing JSON response: No value for id_echantillon");
                                    continue;
                                }
                                int idEchantillon = jsonObject.getInt("id_echantillon");

                                if (!jsonObject.has("nouvelle_visite")) {
                                    Log.e(TAG, "Error while parsing JSON response: No value for nouvelle_visite");
                                    continue;
                                }
                                String nouvelleVisite = jsonObject.getString("nouvelle_visite");

                                if (!jsonObject.has("compterendu")) {
                                    Log.e(TAG, "Error while parsing JSON response: No value for compterendu");
                                    continue;
                                }
                                String compteRendu = jsonObject.getString("compterendu");

                                if (!jsonObject.has("avis")) {
                                    Log.e(TAG, "Error while parsing JSON response: No value for avis");
                                    continue;
                                }
                                String avis = jsonObject.getString("avis");

                                if (!jsonObject.has("etat")) {
                                    Log.e(TAG, "Error while parsing JSON response: No value for etat");
                                    continue;
                                }
                                String etat = jsonObject.getString("etat");
                                int idMotif = jsonObject.getInt("id_motif");

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
                                textViewAvis.setText(avis);
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

