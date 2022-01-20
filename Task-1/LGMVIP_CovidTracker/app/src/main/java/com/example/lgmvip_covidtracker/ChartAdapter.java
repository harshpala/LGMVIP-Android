package com.example.lgmvip_covidtracker;


import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;
import java.util.List;

public class ChartAdapter extends RecyclerView.Adapter<ChartAdapter.ChartFragment> {

    List<Model> modelList;


    public ChartAdapter(List<Model> modelList) {
        this.modelList = modelList;

    }


    @NonNull
    @Override
    public ChartFragment onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chartview,parent,false);
        ChartFragment holder = new ChartFragment(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChartFragment holder, int position) {
        Model data = modelList.get(position);
        holder.LocNameView.setText(data.getStateName());
        holder.ActiveCasesView.setText(Integer.toString(data.getActive()));
        holder.RecoveredCasesView.setText(Integer.toString(data.getRecovered()));
        holder.DeathCasesView.setText(Integer.toString(data.getDeceased()));
        holder.TotalCasesView.setText(Integer.toString(data.getConfirmed()));

        Log.i("TAG", String.valueOf(data.getActive()));

//        int totalActive=0;
//        int totalconfirmed =0;
//        int totaldeceased =0;
//        int totalrecovered =0;
//
//        totalActive = totalActive+data.getActive();
//        totalconfirmed += data.getConfirmed();
//        totaldeceased += data.getDeceased();
//        totalrecovered += data.getRecovered();
//
//        float activper=(float)totalActive/(float)totalconfirmed;

        float activper=(float)data.getActive()/data.getConfirmed();
        float recper=(float)data.getRecovered()/data.getConfirmed();
        float decper=(float)data.getDeceased()/data.getConfirmed();
        System.out.println(activper+" /// "+recper+" /// "+decper);
        holder.pieChart.addPieSlice(new PieModel("Active",activper, Color.parseColor("#3F51B5")));
        holder.pieChart.addPieSlice(new PieModel("Recovered",recper, Color.parseColor("#2E7D32")));
        holder.pieChart.addPieSlice(new PieModel("Dead",decper, Color.parseColor("#DD2C00")));
        holder.pieChart.startAnimation();
    }

    @Override
    public int getItemCount() {
        if(modelList==null)
            modelList=new ArrayList<>();
        return modelList.size();
    }

    class ChartFragment extends RecyclerView.ViewHolder{
        PieChart pieChart;
        TextView LocNameView;
        TextView ActiveCasesView;
        TextView RecoveredCasesView;
        TextView DeathCasesView;
        TextView TotalCasesView;
        public ChartFragment(@NonNull View itemView) {
            super(itemView);
            pieChart=itemView.findViewById(R.id.piechart);
            LocNameView=itemView.findViewById(R.id.LocationName);
            ActiveCasesView=itemView.findViewById(R.id.ActiveCasesFrag);
            TotalCasesView = itemView.findViewById(R.id.TotalCasesFrag);
            RecoveredCasesView=itemView.findViewById(R.id.RecoveredFrag);;
            DeathCasesView=itemView.findViewById(R.id.DeathsFrag);;
        }

    }

}
