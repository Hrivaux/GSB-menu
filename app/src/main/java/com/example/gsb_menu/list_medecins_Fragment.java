package com.example.gsb_menu;

import android.annotation.SuppressLint;
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

import java.util.ArrayList;
import java.util.List;

public class list_medecins_Fragment extends Fragment {

        private static final String TAG = "TableDataActivity";
        private static final String URL = "http://gsb-sciencesu.alwaysdata.net/API/medecin.php";
        private RequestQueue mRequestQueue;
        private TableLayout mTableLayout;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                View view = inflater.inflate(R.layout.fragment_list_medecins_, container, false);
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

                                                        TableRow tableRow = new TableRow(getActivity());

                                                        TextView TextViewNomMedecin = new TextView(getActivity());
                                                        TextViewNomMedecin.setText(jsonObject.optString("nom", ""));
                                                        TextViewNomMedecin.setPadding(8, 8, 8, 8);
                                                        tableRow.addView(TextViewNomMedecin);

                                                        TextView TextViewPrenomMedecin = new TextView(getActivity());
                                                        TextViewPrenomMedecin.setText(String.valueOf(jsonObject.optString("prenom", "")));
                                                        TextViewPrenomMedecin.setPadding(8, 8, 8, 8);
                                                        tableRow.addView(TextViewPrenomMedecin);

                                                        TextView TextViewAdresseMedecin = new TextView(getActivity());
                                                        TextViewAdresseMedecin.setText(String.valueOf(jsonObject.optString("adresse", "")));
                                                        TextViewAdresseMedecin.setPadding(8, 8, 8, 8);
                                                        tableRow.addView(TextViewAdresseMedecin);

                                                        TextView TextViewMailMedecin = new TextView(getActivity());
                                                        TextViewMailMedecin.setText(String.valueOf(jsonObject.optString("email", "")));
                                                        TextViewMailMedecin.setPadding(8, 8, 8, 8);
                                                        tableRow.addView(TextViewMailMedecin);
                                                        mTableLayout.addView(tableRow); // Ajouter la ligne à la table
                                                }
                                        } catch (JSONException e) {
                                                throw new RuntimeException(e);
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
