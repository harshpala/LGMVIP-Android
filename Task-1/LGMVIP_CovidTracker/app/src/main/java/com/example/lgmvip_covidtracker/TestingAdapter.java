package com.example.lgmvip_covidtracker;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TestingAdapter extends RecyclerView.Adapter<TestingAdapter.TestingViewHolder>{

    List<Model> modelList;

    public TestingAdapter(List<Model> mList ) {
        modelList=mList;
    }

    @NonNull
    @Override
    public TestingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.testing,parent,false);
        TestingViewHolder holder = new TestingViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TestingViewHolder holder, int position) {
        Model data = modelList.get(position);
        System.out.println(holder.DistrictNameView);
        holder.DistrictNameView.setText(data.getDistrict());
        holder.ActiveCasesView.setText(Integer.toString(data.getActive()));
        holder.RecoveredCasesView.setText(Integer.toString(data.getRecovered()));
        holder.DeathCasesView.setText(Integer.toString(data.getDeceased()));
        holder.TotalCasesView.setText(Integer.toString(data.getConfirmed()));



    }



    @Override
    public int getItemCount() {

        if(modelList==null)
            modelList=new ArrayList<>();
        return modelList.size();
    }

    class TestingViewHolder extends RecyclerView.ViewHolder{
        TextView DistrictNameView;
        TextView ActiveCasesView;
        TextView RecoveredCasesView;
        TextView DeathCasesView;
        TextView TotalCasesView;


        public TestingViewHolder(@NonNull View itemView) {
            super(itemView);
            DistrictNameView=itemView.findViewById(R.id.DistrictName);
            ActiveCasesView=itemView.findViewById(R.id.ActiveCases);
            TotalCasesView = itemView.findViewById(R.id.TotalCases);
            RecoveredCasesView=itemView.findViewById(R.id.Recovered);;
            DeathCasesView=itemView.findViewById(R.id.Deaths);;
        }
    }
}
