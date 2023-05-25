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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VisitesFragment extends Fragment {

    private static final String TAG = "VisitesFragment";
    private static final String URL = "http://gsb-sciencesu.alwaysdata.net/API/afficherVisite.php";
    private RequestQueue mRequestQueue;
    private TableLayout mTableLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_visites, container, false);
        mTableLayout = view.findViewById(R.id.table_layout_visite);
        mRequestQueue = Volley.newRequestQueue(getActivity());
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

                                    String idVisiteur = jsonObject.optString("nom_visiteur", "");
                                    String idMedecin = jsonObject.optString("nom_medecin", "");
                                    String date = jsonObject.optString("date", "");
                                    String idEchantillon = jsonObject.optString("nom_medicament", "");

                                    TableRow tableRow = new TableRow(getActivity());

                                    TextView textViewIdVisiteur = new TextView(getActivity());
                                    textViewIdVisiteur.setText(idVisiteur);
                                    textViewIdVisiteur.setPadding(8, 8, 8, 8);
                                    tableRow.addView(textViewIdVisiteur);

                                    TextView textViewIdMedecin = new TextView(getActivity());
                                    textViewIdMedecin.setText(idMedecin);
                                    textViewIdMedecin.setPadding(8, 8, 8, 8);
                                    tableRow.addView(textViewIdMedecin);

                                    TextView textViewIdEchantillon = new TextView(getActivity());
                                    textViewIdEchantillon.setText(idEchantillon);
                                    textViewIdEchantillon.setPadding(8, 8, 8, 8);
                                    tableRow.addView(textViewIdEchantillon);

                                    TextView textViewDate = new TextView(getActivity());
                                    textViewDate.setText(date);
                                    textViewDate.setPadding(8, 8, 8, 8);
                                    tableRow.addView(textViewDate);

                                    mTableLayout.addView(tableRow);
                                }
                            } else {
                                // Aucun compte rendu trouvé pour cet utilisateur
                                TextView textViewNoData = new TextView(getActivity());
                                textViewNoData.setText("Aucun compte rendu trouvé pour cet utilisateur");
                                textViewNoData.setPadding(8, 8, 8, 8);
                                TableRow tableRow = new TableRow(getActivity());
                                tableRow.addView(textViewNoData);
                                mTableLayout.addView(tableRow);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG, "Erreur lors de l'analyse de la réponse JSON : " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.e(TAG, "Erreur lors de la récupération des données de l'API : " + error.getMessage());
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
