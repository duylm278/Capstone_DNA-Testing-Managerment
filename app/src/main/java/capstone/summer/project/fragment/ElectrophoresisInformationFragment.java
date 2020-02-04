package capstone.summer.project.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import capstone.summer.R;
import capstone.summer.project.Service.ElectrophoresisProcessService;
import capstone.summer.project.Service.SampleService;
import capstone.summer.project.Service.SystemService;
import capstone.summer.project.Service.UserService;
import capstone.summer.project.Service.VolleyCallback;
import capstone.summer.project.model.ElectrophoresisProcess;
import capstone.summer.project.model.Sample;
import capstone.summer.project.model.System;
import capstone.summer.project.model.User;

public class ElectrophoresisInformationFragment extends Fragment {

    private TextView id, startDate, endDate, gelName, sampleIDName, userID, systemID;
    private TextInputLayout note;
    private ObjectMapper om = new ObjectMapper();
    private InputMethodManager imm;
    final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
    private LinearLayout linearLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_electrophoresis_information, container, false);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        innitView();
        Intent intent = getActivity().getIntent();
        String sampleID = intent.getStringExtra("SampleID");
        ElectrophoresisProcessService.getElectrophoresisProcessByID(getActivity(), sampleID, new VolleyCallback() {
            @Override
            public void onSuccess(Object data) {
                ElectrophoresisProcess electrophoresisProcess = om.convertValue(data, ElectrophoresisProcess.class);
                id.setText(electrophoresisProcess.getID() + "");
                LocalDateTime startDateLocate = LocalDateTime.parse(electrophoresisProcess.getStartDate());
                LocalDateTime endDateLocate = LocalDateTime.parse(electrophoresisProcess.getEndDate());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a");
                startDate.setText(startDateLocate.format(formatter));
                endDate.setText(endDateLocate.format(formatter));
                gelName.setText(electrophoresisProcess.getGelName());
                userID.setText(electrophoresisProcess.getUser().getName());
                sampleIDName.setText(electrophoresisProcess.getSample().getName());
                systemID.setText(electrophoresisProcess.getSystem().getName());
//                UserService.getUserByID(getActivity(), electrophoresisProcess.getUserID(), new VolleyCallback() {
//                    @Override
//                    public void onSuccess(Object data) {
//                        User user = om.convertValue(data, User.class);
//                        userID.setText(user.getUserName());
//                    }
//
//                    @Override
//                    public void onFail(Object data) {
//
//                    }
//                });
//                SampleService.getSampleByID(getActivity(), electrophoresisProcess.getSamplesID(), new VolleyCallback() {
//                    @Override
//                    public void onSuccess(Object data) {
//                        Sample sample = om.convertValue(data, Sample.class);
//                        sampleIDName.setText(sample.getName());
//                    }
//
//                    @Override
//                    public void onFail(Object data) {
//
//                    }
//                });
//                SystemService.getSystemByID(getActivity(), electrophoresisProcess.getSystemID(), new VolleyCallback() {
//                    @Override
//                    public void onSuccess(Object data) {
//                        System system = om.convertValue(data, System.class);
//                        systemID.setText(system.getName());
//                    }
//
//                    @Override
//                    public void onFail(Object data) {
//
//                    }
//                });
            }

            @Override
            public void onFail(Object data) {

            }
        });

//        linearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                imm.hideSoftInputFromWindow(getView().getWindowToken(), flags);
//            }
//        });
    }

    private void innitView() {
        id = getActivity().findViewById(R.id.id);
        startDate = getActivity().findViewById(R.id.startDate);
        endDate = getActivity().findViewById(R.id.endDate);
        gelName = getActivity().findViewById(R.id.gelName);
        linearLayout = getActivity().findViewById(R.id.out);
        userID = getActivity().findViewById(R.id.userID);
        sampleIDName = getActivity().findViewById(R.id.sampleID);
        systemID = getActivity().findViewById(R.id.systemID);
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        imm = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
//
//    }
}
