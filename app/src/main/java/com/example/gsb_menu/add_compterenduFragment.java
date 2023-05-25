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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class add_compterenduFragment extends Fragment {
    private Spinner medecinIdSpinner;
    private Spinner echantillonIdSpinner;
    private Spinner motifIdSpinner;
    private DatePicker datePicker;
    private RadioGroup radioGroup;
    private RadioButton malPasseRadioButton;
    private RadioButton bienPasseRadioButton;
    private Button createCrButton;
    private RequestQueue mRequestQueue;
    private EditText commentEditText;

    private ArrayList<String> medecinsNoms = new ArrayList<>();
    private ArrayList<Integer> medecinsIds = new ArrayList<>();

    private ArrayList<String> echantillonsNoms = new ArrayList<>();
    private ArrayList<Integer> echantillonsIds = new ArrayList<>();

    private ArrayList<String> motifsNoms = new ArrayList<>();
    private ArrayList<Integer> motifsIds = new ArrayList<>();

    private static final String URL_MEDECINS = "https://gsb-sciencesu.alwaysdata.net/API/spinner_med.php";
    private static final String URL_ECHANTILLONS = "https://gsb-sciencesu.alwaysdata.net/API/spinner_echan.php";
    private static final String URL_MOTIFS = "https://gsb-sciencesu.alwaysdata.net/API/spinner_motif.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_compte_rendu, container, false);

        mRequestQueue = Volley.newRequestQueue(requireActivity());

        medecinIdSpinner = view.findViewById(R.id.medecinIdSpinner);
        echantillonIdSpinner = view.findViewById(R.id.echantillonIdSpinner);
        motifIdSpinner = view.findViewById(R.id.motifIdSpinner);
        datePicker = view.findViewById(R.id.datePicker);
        radioGroup = view.findViewById(R.id.radio_group);
        malPasseRadioButton = view.findViewById(R.id.id_avis);
        bienPasseRadioButton = view.findViewById(R.id.id_avis2);
        createCrButton = view.findViewById(R.id.createCrButton);
        commentEditText = view.findViewById(R.id.commentEditText);

        createCrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDataToServer();
            }
        });

        fetchDataFromApi();

        return view;
    }


    private void sendDataToServer() {
        int id_visiteur = Parametre.userID;
        int id_medecin =  medecinsIds.get(medecinIdSpinner.getSelectedItemPosition());
        int id_echantillon = echantillonsIds.get(echantillonIdSpinner.getSelectedItemPosition());
        String compteRendu = commentEditText.getText().toString();
        int id_motif = motifsIds.get(motifIdSpinner.getSelectedItemPosition());
        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        int avis;
        if (bienPasseRadioButton.isChecked()) {
            avis = 2; // Bien passé
        } else {
            avis = 3; // Mal passé
        }
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date(year - 1900, month, day));

        Log.d("Data", "id_visiteur: " + id_visiteur);
        Log.d("Data", "id_medecin: " + id_medecin);
        Log.d("Data", "Date: " + date);
        Log.d("Data", "id_motif: " + id_motif);
        Log.d("Data", "id_echantillon: " + id_echantillon);
        Log.d("Data", "compteRendu: " + compteRendu);
        Log.d("Data", "avis: " + avis);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://gsb-sciencesu.alwaysdata.net/API/redactCR.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response", response);
                if (response.equals("success")) {
                    Toast.makeText(getActivity(), "Compte rendu créé avec succès", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Erreur lors de la création du compte rendu", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Une erreur s'est produite lors de l'envoi des données", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id_visiteur", String.valueOf(id_visiteur));
                params.put("id_medecin", String.valueOf(id_medecin));
                params.put("date_visite", date);
                params.put("id_echantillon", String.valueOf(id_echantillon));
                params.put("id_motif", String.valueOf(id_motif));
                params.put("compterendu", compteRendu);
                params.put("avis", String.valueOf(avis));
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

        StringRequest motifsRequest = new StringRequest(
                Request.Method.POST,
                URL_MOTIFS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject motif = jsonArray.getJSONObject(i);
                                String libelle_motif = motif.getString("libelle_motif");
                                int id = motif.getInt("id");
                                motifsNoms.add(libelle_motif);
                                motifsIds.add(id);
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, motifsNoms);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            motifIdSpinner.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Une erreur s'est produite lors de la récupération des motifs. Veuillez réessayer plus tard.", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id_user", String.valueOf(userId));
                return params;
            }
        };

        mRequestQueue.add(motifsRequest);
    }
}

