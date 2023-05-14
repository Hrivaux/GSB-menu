package com.example.gsb_menu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class homeFragment extends Fragment {

    private SquareView squareView1;
    private SquareView squareView2;
    private SquareView squareView3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Récupération des vues carrées
        squareView1 = view.findViewById(R.id.squareView1);
        squareView2 = view.findViewById(R.id.squareView2);
        squareView3 = view.findViewById(R.id.squareView3);

        // Ajout du chiffre au centre de chaque carré
        squareView1.setText("je");
        squareView2.setText("fais");
        squareView3.setText("test");

        return view;
    }
}
