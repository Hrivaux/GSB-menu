package com.example.gsb_menu;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class inscriptionR extends Fragment {

    public inscriptionR() {}

    private TextView nomTextview;
    private TextView prenomTextview;
    private TextView visiteurTextView;
    private TextView emailTextView;
    private TextView passwordTextView;
    private TextView villeTextView;
    private TextView adresseTextView;
    private TextView codePostalTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inscription_r, container, false);

        //Récupération des textView
        nomTextview = view.findViewById(R.id.nomTextView);
        prenomTextview = view.findViewById(R.id.prenomTextView);
        visiteurTextView = view.findViewById(R.id.visiteurTextView);
        emailTextView = view.findViewById(R.id.emailTextView);
        passwordTextView = view.findViewById(R.id.passwordTextView);
        villeTextView = view.findViewById(R.id.villeTextView);
        adresseTextView = view.findViewById(R.id.adresseTextView);
        codePostalTextView = view.findViewById(R.id.codePostalTextView);

        //Récupération des valeurs passées par l'activité précédente
        Intent intent = getActivity().getIntent();
        String nom = intent.getStringExtra("nom");
        String prenom = intent.getStringExtra("prenom");
        String visiteur = intent.getStringExtra("visiteur");
        String email = intent.getStringExtra("email");
        String password = intent.getStringExtra("password");
        String ville = intent.getStringExtra("ville");
        String adresse = intent.getStringExtra("adresse");
        String codePostal = intent.getStringExtra("codePostal");

        // Affichage des valeur récupérées dans les TextViews
        nomTextview.setText(nom);
        prenomTextview.setText(prenom);
        visiteurTextView.setText(visiteur);
        emailTextView.setText(email);
        passwordTextView.setText(password);
        villeTextView.setText(ville);
        adresseTextView.setText(adresse);
        codePostalTextView.setText(codePostal);

        return view;
    }
}

