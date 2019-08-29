package com.example.uisaludmovilv01.adaptadores;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.uisaludmovilv01.R;
import com.example.uisaludmovilv01.modelos.Evolucion;

import java.util.ArrayList;

public class HistoriaRecyclerAdapter extends RecyclerView.Adapter<HistoriaRecyclerAdapter.ViewHolder> {

    private static final String TAG = "HistoriaRecyclerAdapter";

    private ArrayList<Evolucion> historia = new ArrayList<>();

    public HistoriaRecyclerAdapter(ArrayList<Evolucion> historia) {
        Log.i(TAG, "HistoriaRecyclerAdapter: called i.");
        this.historia = historia;
        Log.i(TAG, "HistoriaRecyclerAdapter: succesfully created i.");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.i(TAG, "onCreateViewHolder: called i.");
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.layout_historia_item, viewGroup, false);
        Log.i(TAG, "onCreateViewHolder: succesfully inflated i.");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.i(TAG, "onBindViewHolder: called i.");

        viewHolder.historia_item.setText(historia.get(i).toString());

        Log.i(TAG, "onBindViewHolder: succesfully set i.");
    }

    @Override
    public int getItemCount() {
        Log.i(TAG, "getItemCount: called i");
        return historia.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView historia_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.i(TAG, "ViewHolder: called i.");
            historia_item = itemView.findViewById(R.id.historia_item);
            Log.i(TAG, "ViewHolder: historia_item found by id i.");
        }
    }
}
