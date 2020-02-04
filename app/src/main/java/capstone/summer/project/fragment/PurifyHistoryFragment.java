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
import capstone.summer.project.Service.PurifyLogService;
import capstone.summer.project.Service.PurifyProcessService;
import capstone.summer.project.Service.VolleyCallback;
import capstone.summer.project.adapter.ExtractLogHistoryRecycleAdapter;
import capstone.summer.project.adapter.PurifyLogHistoryRecycleAdapter;
import capstone.summer.project.model.ExtractLog;
import capstone.summer.project.model.ExtractProcess;
import capstone.summer.project.model.PurifyLog;
import capstone.summer.project.model.PurifyProcess;

public class PurifyHistoryFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<PurifyLog> purifyLogs = new ArrayList<>();
    private PurifyLogHistoryRecycleAdapter adapter;
    private ObjectMapper om = new ObjectMapper();
    private LinearLayout linearLayout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_purify_history,container,false);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        linearLayout = getActivity().findViewById(R.id.notEdit);
        final Intent intent = getActivity().getIntent();
        String sampleID=intent.getStringExtra("SampleID");
        final int[] purifyProcessID = new int[1];
        final PurifyProcess[] purifyProcesses = new PurifyProcess[1];
        PurifyProcessService.getPurifyProcessByID(getActivity(), sampleID, new VolleyCallback() {
            @Override
            public void onSuccess(Object data) {
                purifyProcesses[0] = om.convertValue(data, PurifyProcess.class);
                purifyProcessID[0] = purifyProcesses[0].getID();
            }

            @Override
            public void onFail(Object data) {

            }
        });
        recyclerView = getActivity().findViewById(R.id.purify_log_history_list);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                PurifyLogService.getPurifyLogByID(getActivity(), purifyProcessID[0], new VolleyCallback() {
                    @Override
                    public void onSuccess(Object data) {
                        linearLayout.setVisibility(View.GONE);
                        purifyLogs = (List<PurifyLog>) data;
                        adapter = new PurifyLogHistoryRecycleAdapter(getActivity(), purifyLogs);
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
