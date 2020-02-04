package capstone.summer.project.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.tabs.TabLayout;

import java.util.HashMap;
import java.util.Map;

import capstone.summer.R;
import capstone.summer.project.Service.ExtractProcessService;
import capstone.summer.project.Service.PCRProcessService;
import capstone.summer.project.Service.VolleyCallback;
import capstone.summer.project.adapter.ViewPagerAdapter;
import capstone.summer.project.adapter.ViewPagerAdapterExtract;
import capstone.summer.project.model.ExtractProcess;
import capstone.summer.project.model.PCRProcess;

public class ExtractFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int selectedTab;
    private ImageView imageStatus;
    private ImageView imageStatusRight;
    private ImageView imageLeft;
    private ImageView imageRight;
    private ObjectMapper om = new ObjectMapper();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_extract, container, false);
        // Inflate the layout for this fragment
        viewPager = view.findViewById(R.id.extractViewPaper);
        setupViewPager(viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(selectedTab).select();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        imageStatus = getActivity().findViewById(R.id.status_extract_process);
        imageStatusRight = getActivity().findViewById(R.id.status_extract_pcr_process);
        imageRight = getActivity().findViewById(R.id.line_extract_right);

        Intent intent = getActivity().getIntent();
        final String sampleID = intent.getStringExtra("SampleID");
        ExtractProcessService.getExtractProcessByID(getActivity(), sampleID, new VolleyCallback() {
            @Override
            public void onSuccess(Object data) {
                ExtractProcess extractProcess = om.convertValue(data, ExtractProcess.class);
                switch (extractProcess.getStatus()) {
                    case 0:
                        imageStatus.setImageResource(R.mipmap.ic_unfinish_circle);
                        break;
                    case 1:
                        imageStatus.setImageResource(R.mipmap.ic_finish_circle);
                        break;
                    case 2:
                        imageStatus.setImageResource(R.mipmap.ic_on_going_circle);
                        break;
                }
            }


            @Override
            public void onFail(Object data) {
            }
        });
        PCRProcessService.getPCRProcessByID(getActivity(), sampleID, new VolleyCallback() {
            @Override
            public void onSuccess(Object data) {
                PCRProcess pcrProcess = om.convertValue(data, PCRProcess.class);
                switch (pcrProcess.getStatus()) {
                    case 0:
//                        imageRight.setImageResource(R.mipmap.ic_finish_line);
                        imageStatusRight.setImageResource(R.mipmap.ic_unfinish_circle);
                        break;
                    case 1:
                        imageRight.setBackgroundResource(R.mipmap.ic_finish_line);
                        imageStatusRight.setImageResource(R.mipmap.ic_finish_circle);
                        break;
                    case 2:
                        imageRight.setBackgroundResource(R.mipmap.ic_finish_line);
                        imageStatusRight.setImageResource(R.mipmap.ic_on_going_circle);
                        break;
                }
            }

            @Override
            public void onFail(Object data) {
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapterExtract adapter = new ViewPagerAdapterExtract(getChildFragmentManager());
        adapter.addFrag(new ExtractInformationFragment(), "Thông Tin");
        adapter.addFrag(new ExtractHistoryFragment(), "Lịch Sử");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        selectedTab = tabLayout.getSelectedTabPosition();
    }

}
