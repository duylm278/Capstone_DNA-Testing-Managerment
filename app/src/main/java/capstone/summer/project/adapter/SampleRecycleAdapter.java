package capstone.summer.project.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import capstone.summer.R;
import capstone.summer.project.activity.ProcessActivity;
import capstone.summer.project.model.Sample;

public class SampleRecycleAdapter extends RecyclerView.Adapter<SampleRecycleAdapter.SampleViewHolder>{
    private List<Sample> listSample;
    private Activity activity;

    public SampleRecycleAdapter(Activity activity, List<Sample> listSample) {
        this.listSample = listSample;
        this.activity = activity;
    }

    @NonNull
    @Override
    public SampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_item, parent, false);
        return new SampleViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull SampleViewHolder holder, int position) {
        final Sample sample = listSample.get(position);
        holder.sampleName.setText(sample.getName());
        holder.sampleAmount.setText(sample.getGDGSignal());
        if(sample.getStatus()==1){
            holder.sampleStatus.setText("Hoàn Thành");
            holder.sampleStatus.setTextColor(Color.parseColor("#28c76f"));
        }else if (sample.getStatus() == 2){
            holder.sampleStatus.setText("Đang Xử Lý");
            holder.sampleStatus.setTextColor(Color.parseColor("#ff9f43"));
        }else if (sample.getStatus() == 0){
            holder.sampleStatus.setText("Chưa Xử Lý");
            holder.sampleStatus.setTextColor(Color.parseColor("#cc0000"));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ProcessActivity.class);
                intent.putExtra("SampleID",sample.getId()+"");
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.left_to_right,R.anim.left_out);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listSample == null ? 0 : listSample.size();
    }

    class SampleViewHolder extends RecyclerView.ViewHolder  {

        private TextView sampleName;
        private TextView sampleAmount;
        private TextView sampleStatus;

        public SampleViewHolder(@NonNull View itemView) {
            super(itemView);
            sampleName = itemView.findViewById(R.id.sample_name);
            sampleAmount = itemView.findViewById(R.id.sample_amount);
            sampleStatus = itemView.findViewById(R.id.sample_status);
        }
    }
}
