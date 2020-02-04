package capstone.summer.project.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import capstone.summer.R;
import capstone.summer.project.model.SampleType;
import capstone.summer.project.model.System;

public class SampleTypeRecycleAdapter extends RecyclerView.Adapter<SampleTypeRecycleAdapter.SampleTypeViewHolder>{
    private List<SampleType> sampleTypes;
    private Activity activity;

    public SampleTypeRecycleAdapter(Activity activity, List<SampleType> sampleTypes) {
        this.sampleTypes = sampleTypes;
        this.activity = activity;
    }

    @NonNull
    @Override
    public SampleTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.sampletype_item, parent, false);
        return new SampleTypeViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull SampleTypeViewHolder holder, int position) {
        final SampleType sampleType = sampleTypes.get(position);
        holder.samTypeID.setText(sampleType.getID()+"");
        holder.samTypeSignal.setText(sampleType.getSignal());
        holder.samtypeName.setText(sampleType.getName());
    }

    @Override
    public int getItemCount() {
        return sampleTypes == null ? 0 : sampleTypes.size();
    }

    class SampleTypeViewHolder extends RecyclerView.ViewHolder  {

        private TextView samTypeID;
        private TextView samTypeSignal;
        private TextView samtypeName;

        public SampleTypeViewHolder(@NonNull View itemView) {
            super(itemView);
            samTypeID = itemView.findViewById(R.id.sample_type_ID);
            samTypeSignal = itemView.findViewById(R.id.sample_type_signal);
            samtypeName = itemView.findViewById(R.id.sample_type_name);
        }
    }
}
