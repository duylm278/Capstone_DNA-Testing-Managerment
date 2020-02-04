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
import capstone.summer.project.model.Kit;

public class KitRecycleAdapter extends RecyclerView.Adapter<KitRecycleAdapter.KitViewHolder>{
    private List<Kit> kits;
    private Activity activity;

    public KitRecycleAdapter(Activity activity, List<Kit> kits) {
        this.kits = kits;
        this.activity = activity;
    }

    @NonNull
    @Override
    public KitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.kit_item, parent, false);
        return new KitViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull KitViewHolder holder, int position) {
        final Kit kit = kits.get(position);
        holder.kitID.setText(kit.getID()+"");
        holder.kitName.setText(kit.getName());
        holder.kitCategory.setText(kit.getCategory());
    }

    @Override
    public int getItemCount() {
        return kits == null ? 0 : kits.size();
    }

    class KitViewHolder extends RecyclerView.ViewHolder  {

        private TextView kitID;
        private TextView kitName;
        private TextView kitCategory;

        public KitViewHolder(@NonNull View itemView) {
            super(itemView);
            kitID = itemView.findViewById(R.id.kit_id);
            kitName = itemView.findViewById(R.id.kit_name);
            kitCategory = itemView.findViewById(R.id.kit_category);
        }
    }
}
