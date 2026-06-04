package com.example.projetws.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.projetws.R;
import com.example.projetws.beans.Etudiant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListEtudiantActivity extends AppCompatActivity
        implements EtudiantAdapter.OnEtudiantActionListener {

    // ⚠️ Remplacer 10.0.2.2 par l'IP de votre PC si vous testez sur un vrai appareil
    private static final String BASE_URL    = "http://10.0.2.2/projet/ws/";
    private static final String LOAD_URL   = BASE_URL + "loadEtudiant.php";
    private static final String DELETE_URL = BASE_URL + "deleteEtudiant.php";

    private RecyclerView     recyclerView;
    private EtudiantAdapter  adapter;
    private List<Etudiant>   etudiantList = new ArrayList<>();
    private TextView         tvCount;
    private View             emptyView;
    private RequestQueue     requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_etudiant);

        recyclerView  = findViewById(R.id.recyclerView);
        tvCount       = findViewById(R.id.tvCount);
        emptyView     = findViewById(R.id.emptyView);
        EditText etSearch = findViewById(R.id.etSearch);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EtudiantAdapter(this, etudiantList, this);
        recyclerView.setAdapter(adapter);

        requestQueue = Volley.newRequestQueue(this);

        // Barre de recherche
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        // FAB → activité d'ajout
        findViewById(R.id.fabAdd).setOnClickListener(v ->
                startActivity(new Intent(this, AddEtudiantActivity.class))
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        chargerEtudiants();
    }

    /** Charge la liste depuis le web service */
    private void chargerEtudiants() {
        StringRequest request = new StringRequest(Request.Method.GET, LOAD_URL,
                response -> {
                    Log.d("LIST", response);
                    Type type = new TypeToken<Collection<Etudiant>>(){}.getType();
                    Collection<Etudiant> col = new Gson().fromJson(response, type);
                    etudiantList.clear();
                    if (col != null) etudiantList.addAll(col);
                    adapter.updateData(etudiantList);
                    tvCount.setText(etudiantList.size() + " étudiant(s)");
                    emptyView.setVisibility(etudiantList.isEmpty() ? View.VISIBLE : View.GONE);
                    recyclerView.setVisibility(etudiantList.isEmpty() ? View.GONE : View.VISIBLE);
                },
                error -> {
                    Log.e("LIST", "Erreur : " + error.getMessage());
                    Toast.makeText(this, getString(R.string.msg_error), Toast.LENGTH_LONG).show();
                }
        );
        requestQueue.add(request);
    }

    /** Callback → bouton Modifier */
    @Override
    public void onEdit(Etudiant etudiant) {
        Intent intent = new Intent(this, EditEtudiantActivity.class);
        intent.putExtra("id",     etudiant.getId());
        intent.putExtra("nom",    etudiant.getNom());
        intent.putExtra("prenom", etudiant.getPrenom());
        intent.putExtra("ville",  etudiant.getVille());
        intent.putExtra("sexe",   etudiant.getSexe());
        startActivity(intent);
    }

    /** Callback → bouton Supprimer */
    @Override
    public void onDelete(Etudiant etudiant) {
        new AlertDialog.Builder(this)
                .setTitle("Confirmation")
                .setMessage(getString(R.string.confirm_delete))
                .setPositiveButton("Oui", (dialog, which) -> supprimerEtudiant(etudiant))
                .setNegativeButton("Annuler", null)
                .show();
    }

    private void supprimerEtudiant(Etudiant etudiant) {
        StringRequest request = new StringRequest(Request.Method.POST, DELETE_URL,
                response -> {
                    Toast.makeText(this, getString(R.string.msg_success_delete), Toast.LENGTH_SHORT).show();
                    chargerEtudiants();
                },
                error -> Toast.makeText(this, getString(R.string.msg_error), Toast.LENGTH_LONG).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> p = new HashMap<>();
                p.put("id", String.valueOf(etudiant.getId()));
                return p;
            }
        };
        requestQueue.add(request);
    }
}
