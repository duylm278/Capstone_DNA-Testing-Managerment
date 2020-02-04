package capstone.summer.project.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.tabs.TabLayout;

import capstone.summer.R;
import capstone.summer.project.Service.ElectrophoresisProcessService;
import capstone.summer.project.Service.ExtractProcessService;
import capstone.summer.project.Service.PCRProcessService;
import capstone.summer.project.Service.VolleyCallback;
import capstone.summer.project.adapter.ViewPagerAdapter;
import capstone.summer.project.adapter.ViewPagerAdapterPCR;
import capstone.summer.project.model.ElectrophoresisProcess;
import capstone.summer.project.model.ExtractProcess;
import capstone.summer.project.model.PCRProcess;

public class PCRFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int selectedTab;
    private ImageView imageStatus;
    private ImageView imageStatusRight;
    private ImageView imageStatusLeft;
    private ImageView imageLeft;
    private ImageView imageRight;
    private ObjectMapper om = new ObjectMapper();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pcr, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        viewPager = getActivity().findViewById(R.id.pcrViewPaper);
        setupViewPager(viewPager);

        tabLayout = getActivity().findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(selectedTab).select();

        imageStatus = getActivity().findViewById(R.id.status_pcr_process);
        imageStatusRight = getActivity().findViewById(R.id.status_pcr_electrophoresis_process);
        imageStatusLeft = getActivity().findViewById(R.id.status_pcr_extract_process);
        imageRight = getActivity().findViewById(R.id.line_pcr_right);
        imageLeft = getActivity().findViewById(R.id.line_pcr_left);
        Intent intent = getActivity().getIntent();
        final String sampleID = intent.getStringExtra("SampleID");
        PCRProcessService.getPCRProcessByID(getActivity(), sampleID, new VolleyCallback() {
            @Override
            public void onSuccess(Object data) {
                PCRProcess pcrProcess = om.convertValue(data, PCRProcess.class);
                switch (pcrProcess.getStatus()) {
                    case 0:
                        imageStatus.setImageResource(R.mipmap.ic_unfinish_circle);
                        break;
                    case 1:
                        imageLeft.setBackgroundResource(R.mipmap.ic_finish_line);
                        imageStatus.setImageResource(R.mipmap.ic_finish_circle);
                        break;
                    case 2:
                        imageLeft.setBackgroundResource(R.mipmap.ic_finish_line);
                        imageStatus.setImageResource(R.mipmap.ic_on_going_circle);
                        break;
                }
            }


            @Override
            public void onFail(Object data) {
            }
        });
        ExtractProcessService.getExtractProcessByID(getActivity(), sampleID, new VolleyCallback() {
            @Override
            public void onSuccess(Object data) {
                ExtractProcess extractProcess = om.convertValue(data, ExtractProcess.class);
                switch (extractProcess.getStatus()) {
                    case 0:
                        imageStatusLeft.setImageResource(R.mipmap.ic_unfinish_circle);
                        break;
                    case 1:
                        imageStatusLeft.setImageResource(R.mipmap.ic_finish_circle);
                        break;
                    case 2:
                        imageStatusLeft.setImageResource(R.mipmap.ic_on_going_circle);
                        break;
                }
            }


            @Override
            public void onFail(Object data) {
            }
        });
        ElectrophoresisProcessService.getElectrophoresisProcessByID(getActivity(), sampleID, new VolleyCallback() {
            @Override
            public void onSuccess(Object data) {
                ElectrophoresisProcess electrophoresisProcess = om.convertValue(data, ElectrophoresisProcess.class);
                switch (electrophoresisProcess.getStatus()) {
                    case 0:
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
        ViewPagerAdapterPCR adapter = new ViewPagerAdapterPCR(getChildFragmentManager());
        adapter.addFrag(new PCRInformationFragment(), "Thông Tin");
        adapter.addFrag(new PCRHistoryFragment(), "Lịch Sử");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        selectedTab= tabLayout.getSelectedTabPosition();
    }
}
