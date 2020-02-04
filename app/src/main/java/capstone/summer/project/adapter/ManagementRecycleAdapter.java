package capstone.summer.project.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import capstone.summer.R;
import capstone.summer.project.Service.OrganizationService;
import capstone.summer.project.Service.VolleyCallback;
import capstone.summer.project.activity.ProcessActivity;
import capstone.summer.project.activity.SampleActivity;
import capstone.summer.project.activity.TestPagination;
import capstone.summer.project.model.Case;
import capstone.summer.project.model.Organization;

public class ManagementRecycleAdapter extends RecyclerView.Adapter<ManagementRecycleAdapter.ManagementViewHolder> implements Filterable {

    private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    private boolean isLoaderVisible = false;


    private List<Case> listCase;
    private List<Case> listCaseFull;
    private Activity activity;
    private ObjectMapper om = new ObjectMapper();
    public  ManagementRecycleAdapter(Activity activity, List<Case> listCase) {
        this.listCase = listCase;
        this.activity = activity;
        listCaseFull=new ArrayList<>(listCase);
    }

    @NonNull
    @Override
    public ManagementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.management_item, parent, false);
        return new ManagementViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull final ManagementViewHolder holder, int position) {
        final Case caseItem = listCase.get(position);
        OrganizationService.getOrganizationByID(activity, caseItem.getOrganizationID(), new VolleyCallback() {
            @Override
            public void onSuccess(Object data) {
                Organization organization = om.convertValue(data,Organization.class);

                holder.itemOrganization.setText(organization.getName());
            }

            @Override
            public void onFail(Object data) {

            }
        });
//        holder.itemOrganization.setText("Hình Sự");
        holder.itemCodeCA.setText(caseItem.getCode());
        holder.itemQDTCNumber.setText(caseItem.getQDTCNumber());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, SampleActivity.class);
                intent.putExtra("CaseID", caseItem.getID()+"");
                intent.putExtra("CodeCA", caseItem.getCode());
                intent.putExtra("QDTCNumber", caseItem.getQDTCNumber());
                intent.putExtra("QDTCReceiveDate", caseItem.getQDTCReceiveDate());
                intent.putExtra("QDTCSignDate", caseItem.getQDTCSignDate()+"");
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.left_to_right,R.anim.left_out);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listCase == null ? 0 : listCase.size();
    }



    @Override
    public Filter getFilter() {
        return filterData;
    }
    private Filter filterData = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Case> filterCaseList = new ArrayList<>();

            if(constraint==null||constraint.length()==0){
                filterCaseList.addAll(listCaseFull);
            }else{
                String filter = constraint.toString().toLowerCase().trim();
                for (Case cases: listCaseFull) {
                    if(cases.getCode().toLowerCase().contains(filter)){
                        filterCaseList.add(cases);
                    }
                }
            }
            FilterResults results =new FilterResults();
            results.values = filterCaseList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listCase.clear();
            listCase.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

    class ManagementViewHolder extends RecyclerView.ViewHolder {

        private TextView itemCodeCA;
        private TextView itemQDTCNumber;
        private TextView itemOrganization;

        public ManagementViewHolder(@NonNull View itemView) {
            super(itemView);
            itemCodeCA = itemView.findViewById(R.id.item_management_codeca);
            itemQDTCNumber = itemView.findViewById(R.id.item_management_qdtcnumber);
            itemOrganization = itemView.findViewById(R.id.item_management_organization);
        }
    }
}
