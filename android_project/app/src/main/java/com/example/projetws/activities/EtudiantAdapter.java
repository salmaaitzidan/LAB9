package com.example.projetws.activities;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projetws.R;
import com.example.projetws.beans.Etudiant;
import java.util.ArrayList;
import java.util.List;

public class EtudiantAdapter extends RecyclerView.Adapter<EtudiantAdapter.ViewHolder> {

    private List<Etudiant> etudiants;
    private List<Etudiant> etudiantsFull;
    private final Context context;
    private final OnEtudiantActionListener listener;

    public interface OnEtudiantActionListener {
        void onEdit(Etudiant etudiant);
        void onDelete(Etudiant etudiant);
    }

    public EtudiantAdapter(Context context, List<Etudiant> etudiants, OnEtudiantActionListener listener) {
        this.context       = context;
        this.etudiants     = etudiants;
        this.etudiantsFull = new ArrayList<>(etudiants);
        this.listener      = listener;
    }

    public void updateData(List<Etudiant> newList) {
        this.etudiants     = newList;
        this.etudiantsFull = new ArrayList<>(newList);
        notifyDataSetChanged();
    }

    /** Filtre la liste selon une requête de recherche */
    public void filter(String query) {
        List<Etudiant> filtered = new ArrayList<>();
        if (query == null || query.trim().isEmpty()) {
            filtered.addAll(etudiantsFull);
        } else {
            String q = query.toLowerCase().trim();
            for (Etudiant e : etudiantsFull) {
                if (e.getNom().toLowerCase().contains(q)
                        || e.getPrenom().toLowerCase().contains(q)
                        || e.getVille().toLowerCase().contains(q)) {
                    filtered.add(e);
                }
            }
        }
        etudiants = filtered;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_etudiant, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Etudiant e = etudiants.get(position);

        // Initiale de l'avatar
        String initial = e.getNom().isEmpty() ? "?" : String.valueOf(e.getNom().charAt(0)).toUpperCase();
        holder.tvInitial.setText(initial);

        // Couleur avatar selon sexe
        boolean isHomme = "homme".equalsIgnoreCase(e.getSexe());
        Drawable bg = ContextCompat.getDrawable(context,
                isHomme ? R.drawable.avatar_male_bg : R.drawable.avatar_female_bg);
        holder.avatarBg.setBackground(bg);

        holder.tvNomPrenom.setText(e.getNom() + " " + e.getPrenom());
        holder.tvVille.setText(e.getVille());
        holder.tvSexe.setText(e.getSexe());

        holder.btnEdit.setOnClickListener(v -> listener.onEdit(e));
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(e));
    }

    @Override
    public int getItemCount() {
        return etudiants.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout avatarBg;
        TextView     tvInitial, tvNomPrenom, tvVille, tvSexe;
        ImageButton  btnEdit, btnDelete;

        ViewHolder(View itemView) {
            super(itemView);
            avatarBg    = itemView.findViewById(R.id.avatarBg);
            tvInitial   = itemView.findViewById(R.id.tvInitial);
            tvNomPrenom = itemView.findViewById(R.id.tvNomPrenom);
            tvVille     = itemView.findViewById(R.id.tvVille);
            tvSexe      = itemView.findViewById(R.id.tvSexe);
            btnEdit     = itemView.findViewById(R.id.btnEdit);
            btnDelete   = itemView.findViewById(R.id.btnDelete);
        }
    }
}
