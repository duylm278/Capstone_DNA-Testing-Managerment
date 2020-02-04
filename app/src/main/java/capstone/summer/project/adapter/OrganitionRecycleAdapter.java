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
import capstone.summer.project.model.Organization;

public class OrganitionRecycleAdapter extends RecyclerView.Adapter<OrganitionRecycleAdapter.OrganizationViewHolder>{
    private List<Organization> organizations;
    private Activity activity;

    public OrganitionRecycleAdapter(Activity activity, List<Organization> organizations) {
        this.organizations = organizations;
        this.activity = activity;
    }

    @NonNull
    @Override
    public OrganizationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.organization_item, parent, false);
        return new OrganizationViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull OrganizationViewHolder holder, int position) {
        final Organization organization = organizations.get(position);
        holder.organizationName.setText(organization.getName()+"");
        holder.organizationLocation.setText(organization.getLocation());
        holder.organizationPhoneNumber.setText(organization.getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return organizations == null ? 0 : organizations.size();
    }

    class OrganizationViewHolder extends RecyclerView.ViewHolder  {

        private TextView organizationName;
        private TextView organizationLocation;
        private TextView organizationPhoneNumber;

        public OrganizationViewHolder(@NonNull View itemView) {
            super(itemView);
            organizationName = itemView.findViewById(R.id.organization_name);
            organizationLocation = itemView.findViewById(R.id.organization_location);
            organizationPhoneNumber = itemView.findViewById(R.id.organization_phonenumber);
        }
    }
}
