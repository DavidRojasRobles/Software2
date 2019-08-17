package com.example.uisaludmovilv01.adaptadores;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.uisaludmovilv01.R;
import com.example.uisaludmovilv01.modelos.Procedimiento;

import java.util.ArrayList;

public class CitasRecyclerAdapter extends RecyclerView.Adapter<CitasRecyclerAdapter.ViewHolder> {

    private static final String TAG = "CitasRecyclerAdapter";
    
    private ArrayList<Procedimiento> citas = new ArrayList<>();
    private OnCitaListener onCitaListener;

    public CitasRecyclerAdapter(ArrayList<Procedimiento> citas, OnCitaListener onCitaListener) {
        this.citas = citas;
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
        viewHolder.cita_esp.setText(citas.get(i).getDoctor().getEspecialidad());
        viewHolder.cita_hora.setText(citas.get(i).getHora().toString());
        viewHolder.cita_doctor.setText(citas.get(i).getDoctor().getNombre());
        viewHolder.cita_consultorio.setText(citas.get(i).getDoctor().getConsultorio());
    }

    @Override
    public int getItemCount() {
        return citas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView cita_fecha, cita_esp, cita_hora, cita_doctor, cita_consultorio;
        OnCitaListener onCitaListener;


        public ViewHolder(@NonNull View itemView, OnCitaListener onCitaListener) {
            super(itemView);
            cita_fecha = itemView.findViewById(R.id.cita_fecha);
            cita_esp = itemView.findViewById(R.id.cita_esp);
            cita_hora = itemView.findViewById(R.id.cita_hora);
            cita_doctor = itemView.findViewById(R.id.cita_doctor);
            cita_consultorio = itemView.findViewById(R.id.cita_consultorio);
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
