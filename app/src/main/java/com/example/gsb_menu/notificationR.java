package com.example.gsb_menu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


public class notificationR extends Fragment {


    private EditText objetEditText;
    private EditText messageEditText;
    private EditText destinataireEditText;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inscription_r, container, false);

        //Récupération des textView
        objetEditText = view.findViewById(R.id.objet_edit_text);
        messageEditText = view.findViewById(R.id.message_edit_text);
        destinataireEditText = view.findViewById(R.id.destinataire_edit_text);


        //Récupération des valeurs passées par l'activité précédente
        Intent intent = getActivity().getIntent();
        String nom = intent.getStringExtra("objet");
        String prenom = intent.getStringExtra("message");
        String visiteur = intent.getStringExtra("destinataire");

        // Affichage des valeur récupérées dans les TextViews
        objetEditText.setText((CharSequence) objetEditText);
        messageEditText.setText((CharSequence) messageEditText);
        destinataireEditText.setText((CharSequence) destinataireEditText);


        return view;
    }
}


