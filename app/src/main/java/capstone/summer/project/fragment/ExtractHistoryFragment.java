package capstone.summer.project.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

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
import capstone.summer.project.Service.SampleService;
import capstone.summer.project.Service.VolleyCallback;
import capstone.summer.project.activity.SampleActivity;
import capstone.summer.project.adapter.ExtractLogHistoryRecycleAdapter;
import capstone.summer.project.adapter.SampleRecycleAdapter;
import capstone.summer.project.model.ExtractLog;
import capstone.summer.project.model.ExtractProcess;
import capstone.summer.project.model.Sample;

public class ExtractHistoryFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<ExtractLog> extractLogs = new ArrayList<>();
    private ExtractLogHistoryRecycleAdapter adapter;
    private ObjectMapper om = new ObjectMapper();
    private LinearLayout linearLayout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_extract_history,container,false);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        linearLayout = getActivity().findViewById(R.id.notEdit);
        final Intent intent = getActivity().getIntent();
        String sampleID=intent.getStringExtra("SampleID");
        final ExtractProcess[] extractProcess = new ExtractProcess[1];
        final int[] extractProcessID = new int[1];
        ExtractProcessService.getExtractProcessByID(getActivity(), sampleID, new VolleyCallback() {
                    @Override
                    public void onSuccess(Object data) {
                         extractProcess[0] = om.convertValue(data, ExtractProcess.class);
                        extractProcessID[0] = extractProcess[0].getID();
                    }

            @Override
            public void onFail(Object data) {

            }
        });
        recyclerView = getActivity().findViewById(R.id.extract_log_history_list);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ExtractLogService.getExtractLogByID(getActivity(), extractProcessID[0], new VolleyCallback() {
                    @Override
                    public void onSuccess(Object data) {
                        linearLayout.setVisibility(View.GONE);
                        extractLogs = (List<ExtractLog>) data;
                        adapter = new ExtractLogHistoryRecycleAdapter(getActivity(), extractLogs);
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
