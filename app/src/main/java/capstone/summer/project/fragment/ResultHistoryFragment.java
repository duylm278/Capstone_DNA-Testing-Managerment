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
import capstone.summer.project.Service.ResultLogService;
import capstone.summer.project.Service.ResultService;
import capstone.summer.project.Service.VolleyCallback;
import capstone.summer.project.adapter.ExtractLogHistoryRecycleAdapter;
import capstone.summer.project.adapter.PCRLogHistoryRecycleAdapter;
import capstone.summer.project.adapter.ResultLogHistoryRecycleAdapter;
import capstone.summer.project.model.ExtractLog;
import capstone.summer.project.model.ExtractProcess;
import capstone.summer.project.model.PCRLog;
import capstone.summer.project.model.PCRProcess;
import capstone.summer.project.model.Result;
import capstone.summer.project.model.ResultLog;

public class ResultHistoryFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<ResultLog> resultLogs = new ArrayList<>();
    private ResultLogHistoryRecycleAdapter adapter;
    private ObjectMapper om = new ObjectMapper();
    private LinearLayout linearLayout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_result_history,container,false);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        linearLayout = getActivity().findViewById(R.id.notEdit);
        final Intent intent = getActivity().getIntent();
        String sampleID=intent.getStringExtra("SampleID");
        final Result[] results = new Result[1];
        final int[] ResultID = new int[1];
        ResultService.ResultByID(getActivity(), sampleID, new VolleyCallback() {
            @Override
            public void onSuccess(Object data) {
                results[0] = om.convertValue(data, Result.class);
                ResultID[0] = results[0].getID();
            }

            @Override
            public void onFail(Object data) {

            }
        });
        recyclerView = getActivity().findViewById(R.id.result_log_history_list);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ResultLogService.getResultLogByID(getActivity(), ResultID[0], new VolleyCallback() {
                    @Override
                    public void onSuccess(Object data) {
                        linearLayout.setVisibility(View.GONE);
                        resultLogs = (List<ResultLog>) data;
                        adapter = new ResultLogHistoryRecycleAdapter(getActivity(), resultLogs);
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
