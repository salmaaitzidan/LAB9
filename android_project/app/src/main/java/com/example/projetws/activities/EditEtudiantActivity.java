package com.example.projetws.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.projetws.R;
import java.util.HashMap;
import java.util.Map;

public class EditEtudiantActivity extends AppCompatActivity {

    // ⚠️ Remplacer 10.0.2.2 par l'IP de votre PC si vous testez sur un vrai appareil
    private static final String UPDATE_URL = "http://10.0.2.2/projet/ws/updateEtudiant.php";

    private EditText     etNom, etPrenom;
    private Spinner      spinnerVille;
    private RadioButton  radioHomme, radioFemme;
    private Button       btnSave, btnCancel;
    private ProgressBar  progressBar;
    private RequestQueue requestQueue;
    private int          etudiantId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_etudiant);

        etNom        = findViewById(R.id.etNom);
        etPrenom     = findViewById(R.id.etPrenom);
        spinnerVille = findViewById(R.id.spinnerVille);
        radioHomme   = findViewById(R.id.radioHomme);
        radioFemme   = findViewById(R.id.radioFemme);
        btnSave      = findViewById(R.id.btnSave);
        btnCancel    = findViewById(R.id.btnCancel);
        progressBar  = findViewById(R.id.progressBar);

        requestQueue = Volley.newRequestQueue(this);

        // Récupérer les données de l'intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            etudiantId = extras.getInt("id");
            etNom.setText(extras.getString("nom", ""));
            etPrenom.setText(extras.getString("prenom", ""));

            // Pré-sélectionner la ville dans le spinner
            String ville = extras.getString("ville", "");
            String[] villes = getResources().getStringArray(R.array.villes);
            for (int i = 0; i < villes.length; i++) {
                if (villes[i].equalsIgnoreCase(ville)) {
                    spinnerVille.setSelection(i);
                    break;
                }
            }

            // Pré-sélectionner le sexe
            String sexe = extras.getString("sexe", "homme");
            if ("femme".equalsIgnoreCase(sexe)) {
                radioFemme.setChecked(true);
            } else {
                radioHomme.setChecked(true);
            }
        }

        btnSave.setOnClickListener(v -> {
            if (validerFormulaire()) {
                modifierEtudiant();
            }
        });

        btnCancel.setOnClickListener(v -> finish());
    }

    private boolean validerFormulaire() {
        if (etNom.getText().toString().trim().isEmpty()) {
            etNom.setError("Le nom est requis");
            etNom.requestFocus();
            return false;
        }
        if (etPrenom.getText().toString().trim().isEmpty()) {
            etPrenom.setError("Le prénom est requis");
            etPrenom.requestFocus();
            return false;
        }
        return true;
    }

    private void modifierEtudiant() {
        btnSave.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);

        StringRequest request = new StringRequest(Request.Method.POST, UPDATE_URL,
                response -> {
                    progressBar.setVisibility(View.GONE);
                    btnSave.setEnabled(true);
                    Log.d("EDIT", response);
                    Toast.makeText(this, getString(R.string.msg_success_update), Toast.LENGTH_SHORT).show();
                    finish();
                },
                error -> {
                    progressBar.setVisibility(View.GONE);
                    btnSave.setEnabled(true);
                    Log.e("EDIT", "Erreur : " + error.getMessage());
                    Toast.makeText(this, getString(R.string.msg_error), Toast.LENGTH_LONG).show();
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                String sexe = radioHomme.isChecked() ? "homme" : "femme";
                Map<String, String> params = new HashMap<>();
                params.put("id",     String.valueOf(etudiantId));
                params.put("nom",    etNom.getText().toString().trim());
                params.put("prenom", etPrenom.getText().toString().trim());
                params.put("ville",  spinnerVille.getSelectedItem().toString());
                params.put("sexe",   sexe);
                return params;
            }
        };
        requestQueue.add(request);
    }
}
