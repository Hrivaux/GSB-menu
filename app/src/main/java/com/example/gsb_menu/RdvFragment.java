package com.example.gsb_menu;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RdvFragment extends Fragment {
    private Spinner medecinIdSpinner;
    private Spinner echantillonIdSpinner;
    private DatePicker datePicker;

    private Button organiserVisiteButton;
    private RequestQueue mRequestQueue;

    private static final String URL_MEDECINS = "https://gsb-sciencesu.alwaysdata.net/API/spinner_med.php";
    private static final String URL_ECHANTILLONS = "https://gsb-sciencesu.alwaysdata.net/API/spinner_echan.php";

    ArrayList<String> medecinsNoms = new ArrayList<>();
    ArrayList<Integer> medecinsIds = new ArrayList<>();

    ArrayList<String> echantillonsNoms = new ArrayList<>();
    ArrayList<Integer> echantillonsIds = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rdv, container, false);
        TextView loginText = view.findViewById(R.id.loginText);
        loginText.setText("Prise de rendez-vous");

        medecinIdSpinner = view.findViewById(R.id.medecin_id);
        echantillonIdSpinner = view.findViewById(R.id.echantillon_id);
        datePicker = view.findViewById(R.id.datePicker);

        organiserVisiteButton = view.findViewById(R.id.button_organiser_visite);
        mRequestQueue = Volley.newRequestQueue(requireActivity());

        fetchDataFromApi();

        organiserVisiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDataToServer();
            }
        });

        return view;
    }

    private void sendDataToServer() {
        int medecinId = medecinsIds.get(medecinIdSpinner.getSelectedItemPosition());
        int echantillonId = echantillonsIds.get(echantillonIdSpinner.getSelectedItemPosition());

        int userId = Parametre.userID;

        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date(year - 1900, month, day));

        String url = "http://gsb-sciencesu.alwaysdata.net/API/rdv.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getActivity(), "Rendez-vous ajouté avec succès !", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorMessage = "Une erreur s'est produite lors de l'ajout du rendez-vous. Veuillez réessayer plus tard.";

                        if (error.networkResponse != null) {
                            int statusCode = error.networkResponse.statusCode;
                            errorMessage += "\nErreur HTTP " + statusCode;
                        }

                        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("date_visite", date);
                params.put("medecin_id", String.valueOf(medecinId));
                params.put("echantillon_id", String.valueOf(echantillonId));
                params.put("visiteur_id", String.valueOf(userId));
                return params;
            }
        };

        mRequestQueue.add(stringRequest);
    }

    private void fetchDataFromApi() {
        int userId = Parametre.userID;

        StringRequest medecinsRequest = new StringRequest(
                Request.Method.POST,
                URL_MEDECINS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject medecin = jsonArray.getJSONObject(i);
                                String prenom = medecin.getString("prenom");
                                int id = medecin.getInt("id");
                                medecinsNoms.add(prenom);
                                medecinsIds.add(id);
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, medecinsNoms);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            medecinIdSpinner.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Une erreur s'est produite lors de la récupération des médecins. Veuillez réessayer plus tard.", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id_user", String.valueOf(userId));
                return params;
            }
        };

        mRequestQueue.add(medecinsRequest);

        JsonArrayRequest echantillonRequest = new JsonArrayRequest(Request.Method.GET,
                URL_ECHANTILLONS, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject medecin = response.getJSONObject(i);
                                String nom_medicament = medecin.getString("nom_medicament");
                                int id = medecin.getInt("id");
                                echantillonsNoms.add(nom_medicament);
                                echantillonsIds.add(id);
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, echantillonsNoms);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            echantillonIdSpinner.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Une erreur s'est produite lors de la récupération des échantillons. Veuillez réessayer plus tard.", Toast.LENGTH_SHORT).show();
                    }
                });

        mRequestQueue.add(echantillonRequest);
    }
}

