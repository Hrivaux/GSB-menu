package com.example.gsb_menu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class notificationsFragment extends Fragment {

    private EditText objetEditText;
    private EditText messageEditText;
    private EditText destinataireEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        // Récupérer les références des vues de vos champs d'entrée
        objetEditText = view.findViewById(R.id.objet_edit_text);
        messageEditText = view.findViewById(R.id.message_edit_text);
        destinataireEditText = view.findViewById(R.id.destinataire_edit_text);

        Button envoyerButton = view.findViewById(R.id.envoyer_button);
        envoyerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Vérifier si les champs sont remplis ou non
                try {
                    if (objetEditText.getText().toString().isEmpty()) {
                        objetEditText.setError("Veuillez remplir ce champ");
                        return;
                    }
                    if (messageEditText.getText().toString().isEmpty()) {
                        messageEditText.setError("Veuillez remplir ce champ");
                        return;
                    }
                    if (destinataireEditText.getText().toString().isEmpty()) {
                        destinataireEditText.setError("Veuillez remplir ce champ");
                        return;
                    }

                    // Envoyer le formulaire
                    // ...
                } catch (Exception e) {
                    Log.e("notificationsFragment", "Erreur lors de l'envoi du formulaire", e);
                    Toast.makeText(getContext(), "Erreur lors de l'envoi du formulaire", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}
