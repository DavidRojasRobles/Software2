package com.example.uisaludmovilv01.adaptadores;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uisaludmovilv01.R;
import com.example.uisaludmovilv01.modelos.Orden;
import com.example.uisaludmovilv01.modelos.Procedimiento;

import java.util.ArrayList;

public class OrdenesRecyclerAdapter extends RecyclerView.Adapter<OrdenesRecyclerAdapter.ViewHolder> {

    private ArrayList<Orden> ordenes = new ArrayList<>();

    public OrdenesRecyclerAdapter(ArrayList<Orden> ordenes) {
        this.ordenes = ordenes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.layout_orden_lista_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.orden_fecha.setText(ordenes.get(i).getCita().getFecha().toString());
        if (ordenes.get(i).getClass().equals("OrdenMedicamento")) {
            viewHolder.orden_tipo.setText("Medicamento");
            viewHolder.orden_icon.setImageResource(R.drawable.ic_healing_black_24dp);
        } else {
            viewHolder.orden_tipo.setText("Remisión");
            viewHolder.orden_icon.setImageResource(R.drawable.ic_content_paste_black_24dp);

        }
        viewHolder.orden_doctor.setText(ordenes.get(i).getCita().getDoctor().getNombre());
        viewHolder.orden_obs.setText(ordenes.get(i).getObservaciones());
        viewHolder.orden_vigencia.setText(ordenes.get(i).getFechaVigencia().toString());
    }

    @Override
    public int getItemCount() {
        return ordenes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView orden_fecha, orden_tipo, orden_doctor, orden_obs, orden_vigencia;
        ImageView orden_icon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orden_fecha = itemView.findViewById(R.id.orden_fecha);
            orden_tipo = itemView.findViewById(R.id.orden_tipo);
            orden_icon = itemView.findViewById(R.id.ordenIcon);
            orden_doctor = itemView.findViewById(R.id.orden_doctor);
            orden_obs = itemView.findViewById(R.id.orden_obs);
            orden_vigencia = itemView.findViewById(R.id.orden_vigencia);
        }
    }
}
