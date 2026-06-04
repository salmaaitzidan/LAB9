package com.example.projetws.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class AddEtudiantActivity extends AppCompatActivity {

    // ⚠️ Remplacer 10.0.2.2 par l'IP de votre PC si vous testez sur un vrai appareil
    private static final String INSERT_URL = "http://10.0.2.2/projet/ws/createEtudiant.php";

    private EditText     etNom, etPrenom;
    private Spinner      spinnerVille;
    private RadioButton  radioHomme, radioFemme;
    private Button       btnAjouter;
    private ProgressBar  progressBar;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_etudiant);

        etNom        = findViewById(R.id.etNom);
        etPrenom     = findViewById(R.id.etPrenom);
        spinnerVille = findViewById(R.id.spinnerVille);
        radioHomme   = findViewById(R.id.radioHomme);
        radioFemme   = findViewById(R.id.radioFemme);
        btnAjouter   = findViewById(R.id.btnAjouter);
        progressBar  = findViewById(R.id.progressBar);

        requestQueue = Volley.newRequestQueue(this);

        btnAjouter.setOnClickListener(v -> {
            if (validerFormulaire()) {
                envoyerEtudiant();
            }
        });
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

    private void envoyerEtudiant() {
        btnAjouter.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);

        StringRequest request = new StringRequest(Request.Method.POST, INSERT_URL,
                response -> {
                    progressBar.setVisibility(View.GONE);
                    btnAjouter.setEnabled(true);
                    Log.d("ADD", response);
                    Toast.makeText(this, getString(R.string.msg_success_add), Toast.LENGTH_SHORT).show();
                    finish(); // retourne à la liste
                },
                error -> {
                    progressBar.setVisibility(View.GONE);
                    btnAjouter.setEnabled(true);
                    Log.e("ADD", "Erreur : " + error.getMessage());
                    Toast.makeText(this, getString(R.string.msg_error), Toast.LENGTH_LONG).show();
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                String sexe = radioHomme.isChecked() ? "homme" : "femme";
                Map<String, String> params = new HashMap<>();
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
