package com.example.uisaludmovilv01.adaptadores;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uisaludmovilv01.R;
import com.example.uisaludmovilv01.modelos.Orden;
import com.example.uisaludmovilv01.modelos.OrdenMedicamento;
import com.example.uisaludmovilv01.modelos.OrdenProcedimiento;
import com.example.uisaludmovilv01.modelos.Procedimiento;

import java.util.ArrayList;

public class OrdenesRecyclerAdapter extends RecyclerView.Adapter<OrdenesRecyclerAdapter.ViewHolder> {

    private static final String TAG = "OrdenesRecyclerAdapter";

    private ArrayList<Orden> ordenes = new ArrayList<>();
    private OnOrdenListener onOrdenListener;

    public OrdenesRecyclerAdapter(ArrayList<Orden> ordenes, OnOrdenListener onOrdenListener) {
        this.ordenes = ordenes;
        this.onOrdenListener = onOrdenListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.layout_orden_lista_item, viewGroup, false);
        return new ViewHolder(view, onOrdenListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.orden_fecha.setText(ordenes.get(i).getCita().getFecha().toString());

        if (ordenes.get(i).getClass() == OrdenMedicamento.class) {
            viewHolder.orden_tipo.setText("Medicina");
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView orden_fecha, orden_tipo, orden_doctor, orden_obs, orden_vigencia;
        ImageView orden_icon;

        OnOrdenListener onOrdenListener;

        public ViewHolder(@NonNull View itemView, OnOrdenListener onOrdenListener) {
            super(itemView);
            orden_fecha = itemView.findViewById(R.id.orden_fecha);
            orden_tipo = itemView.findViewById(R.id.orden_tipo);
            orden_icon = itemView.findViewById(R.id.ordenIcon);
            orden_doctor = itemView.findViewById(R.id.orden_doctor);
            orden_obs = itemView.findViewById(R.id.orden_obs);
            orden_vigencia = itemView.findViewById(R.id.orden_vigencia);

            this.onOrdenListener = onOrdenListener;

            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {

            Log.d(TAG, "onClick: " + getAdapterPosition());
            onOrdenListener.onOrdenClick(getAdapterPosition());
        }
    }

    public interface OnOrdenListener {
        void onOrdenClick(int position);
    }
}
