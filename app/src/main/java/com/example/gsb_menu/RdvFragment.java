package com.example.gsb_menu;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class RdvFragment extends Fragment {

    private Spinner listeSpinner1;
    private EditText date_rdvEditText;
    private Spinner listeSpinner2;
    private Button button_organiser_visite;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rdv, container, false);

        listeSpinner1 = view.findViewById(R.id.listeSpinner1);
        date_rdvEditText = view.findViewById(R.id.date_rdvEditText);
        listeSpinner2 = view.findViewById(R.id.listeSpinner2);
        button_organiser_visite = view.findViewById(R.id.button_organiser_visite);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(requireContext(),
                R.array.choix_spinner_medecin, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listeSpinner1.setAdapter(adapter1);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(requireContext(),
                R.array.choix_spinner_echantillon, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listeSpinner2.setAdapter(adapter2);

        button_organiser_visite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String choixMedecin = listeSpinner1.getSelectedItem().toString();
                String daterdv = date_rdvEditText.getText().toString();
                String choixEchantillon = listeSpinner2.getSelectedItem().toString();

                // Call the API to save the data to the database
                RequestQueue queue = Volley.newRequestQueue(requireContext());
                String url = "https://gsb-api.000webhostapp.com/API/rdv.php";

                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("choix_medecin", choixMedecin);
                    jsonBody.put("date_rdv", daterdv);
                    jsonBody.put("choix_echantillon", choixEchantillon);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(requireContext(), "Les données ont été ajoutées avec succès.", Toast.LENGTH_SHORT).show();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(requireContext(), "Erreur lors de l'ajout des données.", Toast.LENGTH_SHORT).show();
                    }
                });

                queue.add(jsonObjectRequest);
            }
        });

        return view;
    }
}


/// return inflater.inflate(R.layout.fragment_rdv, container, false);