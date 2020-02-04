package capstone.summer.project.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import capstone.summer.R;
import capstone.summer.project.Service.ElectrophoresisLogService;
import capstone.summer.project.Service.ElectrophoresisProcessService;
import capstone.summer.project.Service.ExtractLogService;
import capstone.summer.project.Service.ExtractProcessService;
import capstone.summer.project.Service.VolleyCallback;
import capstone.summer.project.adapter.ElectrophoresisLogHistoryRecycleAdapter;
import capstone.summer.project.adapter.PCRLogHistoryRecycleAdapter;
import capstone.summer.project.model.ElectrophoresisLog;
import capstone.summer.project.model.ElectrophoresisProcess;
import capstone.summer.project.model.ExtractLog;
import capstone.summer.project.model.ExtractProcess;

public class ElectrophoresisHistoryFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<ElectrophoresisLog> electrophoresisLogs = new ArrayList<>();
    private ElectrophoresisLogHistoryRecycleAdapter adapter;
    private ObjectMapper om = new ObjectMapper();
    private LinearLayout linearLayout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_electrophoresis_history,container,false);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        linearLayout = getActivity().findViewById(R.id.notEdit);
        final Intent intent = getActivity().getIntent();
        String sampleID=intent.getStringExtra("SampleID");
        final ElectrophoresisProcess[] electrophoresisProcess = new ElectrophoresisProcess[1];
        final int[] electrophoresisProcessID = new int[1];
        ElectrophoresisProcessService.getElectrophoresisProcessByID(getActivity(), sampleID, new VolleyCallback() {
            @Override
            public void onSuccess(Object data) {
                electrophoresisProcess[0] = om.convertValue(data, ElectrophoresisProcess.class);
                electrophoresisProcessID[0] = electrophoresisProcess[0].getID();
            }

            @Override
            public void onFail(Object data) {

            }
        });
        recyclerView = getActivity().findViewById(R.id.electrophoresis_log_history_list);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ElectrophoresisLogService.getElectrophoresisLogByID(getActivity(), electrophoresisProcessID[0], new VolleyCallback() {
                    @Override
                    public void onSuccess(Object data) {
                        linearLayout.setVisibility(View.GONE);
                        electrophoresisLogs = (List<ElectrophoresisLog>) data;
                        adapter = new ElectrophoresisLogHistoryRecycleAdapter(getActivity(), electrophoresisLogs);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onFail(Object data) {

                    }
                });
            }
        }, 1000);

    }
}
