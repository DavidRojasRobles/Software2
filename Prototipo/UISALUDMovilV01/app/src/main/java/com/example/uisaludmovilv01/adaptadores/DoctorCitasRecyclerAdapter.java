package com.example.uisaludmovilv01.adaptadores;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.uisaludmovilv01.R;
import com.example.uisaludmovilv01.modelos.Doctor;
import com.example.uisaludmovilv01.modelos.Procedimiento;
import com.example.uisaludmovilv01.modelos.Usuario;

import java.util.ArrayList;

public class DoctorCitasRecyclerAdapter extends RecyclerView.Adapter<DoctorCitasRecyclerAdapter.ViewHolder> {

    private static final String TAG = "DoctorCitasRecyclerAdap";

    private ArrayList<Procedimiento> citas = new ArrayList<>();
    private ArrayList<Usuario> usuarios = new ArrayList<>();
    private OnCitaListener onCitaListener;

    public DoctorCitasRecyclerAdapter(ArrayList<Procedimiento> citas, ArrayList<Usuario> usuarios, OnCitaListener onCitaListener) {
        this.citas = citas;
        this.usuarios = usuarios;
        this.onCitaListener = onCitaListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.layout_cita_lista_item, viewGroup, false);
        return new ViewHolder(view, onCitaListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.cita_fecha.setText(citas.get(i).getFecha().toString());
        viewHolder.cita_hora.setText(citas.get(i).getHora().toString());
        viewHolder.cita_usuario.setText(usuarios.get(i).getNombre());
    }

    @Override
    public int getItemCount() {
        return citas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView cita_fecha, cita_hora, cita_usuario;
        OnCitaListener onCitaListener;


        public ViewHolder(@NonNull View itemView, OnCitaListener onCitaListener) {
            super(itemView);
            cita_fecha = itemView.findViewById(R.id.cita_fecha);
            cita_hora = itemView.findViewById(R.id.cita_hora);
            cita_usuario = itemView.findViewById(R.id.cita_usuario);

            this.onCitaListener = onCitaListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            Log.d(TAG, "onClick: " + getAdapterPosition());
            onCitaListener.onCitaClick(getAdapterPosition());
        }
    }

    public interface OnCitaListener {
        void onCitaClick(int position);
    }
}
