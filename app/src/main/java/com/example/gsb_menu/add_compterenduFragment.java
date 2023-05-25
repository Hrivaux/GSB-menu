package com.example.gsb_menu;

import android.os.Bundle;
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

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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
    private Spinner etatIdSpinner;

    private DatePicker datePicker;

    private Button button_creer_cr;
    private RequestQueue mRequestQueue;

    private static final String URL_MEDECINS = "https://gsb-sciencesu.alwaysdata.net/API/spinner_med.php";
    private static final String URL_ECHANTILLONS = "https://gsb-sciencesu.alwaysdata.net/API/spinner_echan.php";
    private static final String URL_MOTIF = "https://gsb-sciencesu.alwaysdata.net/API/spinner_motif.php";

    ArrayList<String> medecinsNoms = new ArrayList<>();
    ArrayList<Integer> medecinsIds = new ArrayList<>();

    ArrayList<String> echantillonsNoms = new ArrayList<>();
    ArrayList<Integer> echantillonsIds = new ArrayList<>();

    ArrayList<String> motiflibelle = new ArrayList<>();
    ArrayList<Integer> motifIds = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_compte_rendu, container, false);
        TextView loginText = view.findViewById(R.id.loginText);
        loginText.setText("Création de compte rendu");

        medecinIdSpinner = view.findViewById(R.id.medecin_id);
        echantillonIdSpinner = view.findViewById(R.id.echantillon_id);
        motifIdSpinner = view.findViewById(R.id.motif_id);
        etatIdSpinner = view.findViewById(R.id.etat_id);
        datePicker = view.findViewById(R.id.datePicker);

        button_creer_cr = view.findViewById(R.id.button_cr);
        mRequestQueue = Volley.newRequestQueue(requireActivity());

        fetchDataFromApi();

        button_creer_cr.setOnClickListener(new View.OnClickListener() {
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
        int motifId = motifIds.get(motifIdSpinner.getSelectedItemPosition());
        int etatId = etatIdSpinner.getSelectedItemPosition() + 1; // On utilise la position du spinner comme ID d'état (1 pour "mal passé" et 2 pour "bien passé")

        int userId = Parametre.userID;

        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date(year - 1900, month, day));

        String url = "http://gsb-sciencesu.alwaysdata.net/API/redactCR.php";

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
                params.put("date", date);
                params.put("id_visiteur", String.valueOf(userId));
                params.put("id_medecin", String.valueOf(medecinId));
                params.put("id_echantillon", String.valueOf(echantillonId));
                params.put("compterendu", "Le compte-rendu ici"); // Remplacez "Le compte-rendu ici" par le texte réel du compte-rendu
                params.put("avis", "1"); // Remplacez "1" par l'ID de l'avis réel
                params.put("etat", String.valueOf(etatId));
                params.put("id_motif", String.valueOf(motifId));
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
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("medecins");
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

        StringRequest echantillonRequest = new StringRequest(
                Request.Method.POST,
                URL_ECHANTILLONS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("echantillons");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject echantillon = jsonArray.getJSONObject(i);
                                String nom = echantillon.getString("nom");
                                int id = echantillon.getInt("id");
                                echantillonsNoms.add(nom);
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
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id_user", String.valueOf(userId));
                return params;
            }
        };

        mRequestQueue.add(echantillonRequest);

        StringRequest motifRequest = new StringRequest(
                Request.Method.POST,
                URL_MOTIF,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("motif");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject motif = jsonArray.getJSONObject(i);
                                String libelle = motif.getString("libelle");
                                int id = motif.getInt("id");
                                motiflibelle.add(libelle);
                                motifIds.add(id);
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, motiflibelle);
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

        mRequestQueue.add(motifRequest);
    }
}
