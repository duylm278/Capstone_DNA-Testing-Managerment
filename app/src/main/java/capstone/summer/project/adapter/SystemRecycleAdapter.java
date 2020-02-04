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
import capstone.summer.project.model.System;

public class SystemRecycleAdapter extends RecyclerView.Adapter<SystemRecycleAdapter.SystemViewHolder>{
    private List<System> systems;
    private Activity activity;

    public SystemRecycleAdapter(Activity activity, List<System> systems) {
        this.systems = systems;
        this.activity = activity;
    }

    @NonNull
    @Override
    public SystemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.system_item, parent, false);
        return new SystemViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull SystemViewHolder holder, int position) {
        final System system = systems.get(position);
        holder.sysID.setText(system.getID()+"");
        holder.sysName.setText(system.getName());
        holder.sysCategory.setText(system.getCategory());
    }

    @Override
    public int getItemCount() {
        return systems == null ? 0 : systems.size();
    }

    class SystemViewHolder extends RecyclerView.ViewHolder  {

        private TextView sysID;
        private TextView sysName;
        private TextView sysCategory;

        public SystemViewHolder(@NonNull View itemView) {
            super(itemView);
            sysID = itemView.findViewById(R.id.system_ID);
            sysName = itemView.findViewById(R.id.system_name);
            sysCategory = itemView.findViewById(R.id.system_category);
        }
    }
}
