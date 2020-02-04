package capstone.summer.project.adapter;

import android.app.Activity;
import android.content.Intent;
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
import capstone.summer.project.model.Litigant;
import capstone.summer.project.model.Sample;

public class LitigantRecycleAdapter extends RecyclerView.Adapter<LitigantRecycleAdapter.LitigantViewHolder>{
    private List<Litigant> listLitigants;
    private Activity activity;

    public LitigantRecycleAdapter(Activity activity, List<Litigant> listLitigants) {
        this.listLitigants = listLitigants;
        this.activity = activity;
    }

    @NonNull
    @Override
    public LitigantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.litigant_item, parent, false);
        return new LitigantViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull LitigantViewHolder holder, int position) {
        final Litigant litigant = listLitigants.get(position);
        holder.litigantName.setText(litigant.getName());
        holder.litigantHomeTown.setText(litigant.getHomeTown());
        holder.litigantIdentifyCard.setText(litigant.getIdentifyCard());
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(activity, ProcessActivity.class);
//                intent.putExtra("SampleID",sample.getId()+"");
//                activity.startActivity(intent);
//                activity.overridePendingTransition(R.anim.left_to_right,R.anim.left_out);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return listLitigants == null ? 0 : listLitigants.size();
    }

    class LitigantViewHolder extends RecyclerView.ViewHolder  {

        private TextView litigantName;
        private TextView litigantHomeTown;
        private TextView litigantIdentifyCard;

        public LitigantViewHolder(@NonNull View itemView) {
            super(itemView);
            litigantName = itemView.findViewById(R.id.litigant_name);
            litigantHomeTown = itemView.findViewById(R.id.litigant_home_town);
            litigantIdentifyCard = itemView.findViewById(R.id.litigant_identify_card);
        }
    }
}
