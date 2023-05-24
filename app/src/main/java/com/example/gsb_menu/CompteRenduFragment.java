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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gsb_menu.Parametre;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CompteRenduFragment extends Fragment {

    private static final String TAG = "CompteRenduFragment";
    private static final String URL = "https://hugo-rivaux.fr/API/afficherCR.php";
    private RequestQueue mRequestQueue;
    private TableLayout mTableLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_compte_, container, false);
        mTableLayout = view.findViewById(R.id.table_layout);
        mRequestQueue = Volley.newRequestQueue(requireContext());

        Log.i(TAG, "ID de l'utilisateur : " + Parametre.userID);

        fetchDataFromApi();
        return view;
    }

    private void fetchDataFromApi() {
        int userId = Parametre.userID;

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, // Utilise la méthode POST
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            // Vérifier si la réponse contient des données
                            if (jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

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

                                    TableRow tableRow = new TableRow(requireContext());

                                    TextView textViewId = new TextView(requireContext());
                                    textViewId.setText(String.valueOf(id));
                                    textViewId.setPadding(8, 8, 8, 8);
                                    tableRow.addView(textViewId);

                                    TextView textViewIdVisiteur = new TextView(requireContext());
                                    textViewIdVisiteur.setText(String.valueOf(idVisiteur));
                                    textViewIdVisiteur.setPadding(8, 8, 8, 8);
                                    tableRow.addView(textViewIdVisiteur);

                                    TextView textViewIdMedecin = new TextView(requireContext());
                                    textViewIdMedecin.setText(String.valueOf(idMedecin));
                                    textViewIdMedecin.setPadding(8, 8, 8, 8);
                                    tableRow.addView(textViewIdMedecin);

                                    TextView textViewDate = new TextView(requireContext());
                                    textViewDate.setText(date);
                                    textViewDate.setPadding(8, 8, 8, 8);
                                    tableRow.addView(textViewDate);

                                    TextView textViewIdEchantillon = new TextView(requireContext());
                                    textViewIdEchantillon.setText(String.valueOf(idEchantillon));
                                    textViewIdEchantillon.setPadding(8, 8, 8, 8);
                                    tableRow.addView(textViewIdEchantillon);

                                    TextView textViewNouvelleVisite = new TextView(requireContext());
                                    textViewNouvelleVisite.setText(nouvelleVisite);
                                    textViewNouvelleVisite.setPadding(8, 8, 8, 8);
                                    tableRow.addView(textViewNouvelleVisite);

                                    TextView textViewCompteRendu = new TextView(requireContext());
                                    textViewCompteRendu.setText(compteRendu);
                                    textViewCompteRendu.setPadding(8, 8, 8, 8);
                                    tableRow.addView(textViewCompteRendu);

                                    TextView textViewAvis = new TextView(requireContext());
                                    if (avis.equals("0")) {
                                        textViewAvis.setText("Défavorable");
                                    } else {
                                        textViewAvis.setText("Favorable");
                                    }
                                    textViewAvis.setPadding(8, 8, 8, 8);
                                    tableRow.addView(textViewAvis);

                                    TextView textViewEtat = new TextView(requireContext());
                                    if (etat.equals("0")) {
                                        textViewEtat.setText("En cours");
                                    } else {
                                        textViewEtat.setText("Terminé");
                                    }
                                    textViewEtat.setPadding(8, 8, 8, 8);
                                    tableRow.addView(textViewEtat);

                                    TextView textViewIdMotif = new TextView(requireContext());
                                    textViewIdMotif.setText(String.valueOf(idMotif));
                                    textViewIdMotif.setPadding(8, 8, 8, 8);
                                    tableRow.addView(textViewIdMotif);

                                    mTableLayout.addView(tableRow);
                                }
                            } else {
                                // Aucun compte rendu trouvé pour cet utilisateur
                                Log.i(TAG, "Aucun compte rendu trouvé pour cet utilisateur.");
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
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id_user", String.valueOf(userId));
                return params;
            }
        };

        mRequestQueue.add(stringRequest);
    }
}
