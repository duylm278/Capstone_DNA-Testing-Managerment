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
import capstone.summer.project.Service.ExtractLogService;
import capstone.summer.project.Service.ExtractProcessService;
import capstone.summer.project.Service.PCRLogService;
import capstone.summer.project.Service.PCRProcessService;
import capstone.summer.project.Service.VolleyCallback;
import capstone.summer.project.adapter.ExtractLogHistoryRecycleAdapter;
import capstone.summer.project.adapter.PCRLogHistoryRecycleAdapter;
import capstone.summer.project.model.ExtractLog;
import capstone.summer.project.model.ExtractProcess;
import capstone.summer.project.model.PCRLog;
import capstone.summer.project.model.PCRProcess;

public class PCRHistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<PCRLog> pcrLogs = new ArrayList<>();
    private PCRLogHistoryRecycleAdapter adapter;
    private ObjectMapper om = new ObjectMapper();
    private LinearLayout linearLayout;
//private RecyclerView recyclerView;
//    private List<ExtractLog> extractLogs = new ArrayList<>();
//    private PCRLogHistoryRecycleAdapter adapter;
//    private ObjectMapper om = new ObjectMapper();
//    private LinearLayout linearLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pcr_history,container,false);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        linearLayout = getActivity().findViewById(R.id.notEdit);
        final Intent intent = getActivity().getIntent();
        String sampleID=intent.getStringExtra("SampleID");
        final PCRProcess[] pcrProcess = new PCRProcess[1];
        final int[] pcrProcessID = new int[1];
        PCRProcessService.getPCRProcessByID(getActivity(), sampleID, new VolleyCallback() {
            @Override
            public void onSuccess(Object data) {
                pcrProcess[0] = om.convertValue(data, PCRProcess.class);
                pcrProcessID[0] = pcrProcess[0].getID();
            }

            @Override
            public void onFail(Object data) {

            }
        });
        recyclerView = getActivity().findViewById(R.id.pcr_log_history_list);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                PCRLogService.getPCRLogByID(getActivity(), pcrProcessID[0], new VolleyCallback() {
                    @Override
                    public void onSuccess(Object data) {
                        linearLayout.setVisibility(View.GONE);
                        pcrLogs = (List<PCRLog>) data;
                        adapter = new PCRLogHistoryRecycleAdapter(getActivity(), pcrLogs);
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

