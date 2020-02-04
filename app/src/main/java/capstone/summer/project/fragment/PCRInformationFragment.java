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
import capstone.summer.project.Service.KitService;
import capstone.summer.project.Service.PCRProcessService;
import capstone.summer.project.Service.SampleService;
import capstone.summer.project.Service.SystemService;
import capstone.summer.project.Service.UserService;
import capstone.summer.project.Service.VolleyCallback;
import capstone.summer.project.model.Kit;
import capstone.summer.project.model.PCRProcess;
import capstone.summer.project.model.Sample;
import capstone.summer.project.model.System;
import capstone.summer.project.model.User;

public class PCRInformationFragment extends Fragment {

    private TextView id,startDate,endDate,sampleIDName,kitID,userID,systemID;
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
        View rootView = inflater.inflate(R.layout.fragment_pcr_information,container,false);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        innitView();

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imm.hideSoftInputFromWindow(getView().getWindowToken(), flags);
            }
        });
        Intent intent = getActivity().getIntent();
        String sampleID=intent.getStringExtra("SampleID");
        PCRProcessService.getPCRProcessByID(getActivity(), sampleID, new VolleyCallback() {
            @Override
            public void onSuccess(Object data) {
                PCRProcess pcrProcess=om.convertValue(data,PCRProcess.class);
                id.setText(pcrProcess.getID()+"");
                LocalDateTime startDateLocate = LocalDateTime.parse(pcrProcess.getStartDate());
                LocalDateTime endDateLocate = LocalDateTime.parse(pcrProcess.getEndDate());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a");
                startDate.setText(startDateLocate.format(formatter));
                endDate.setText(endDateLocate.format(formatter));

                note.getEditText().setText(pcrProcess.getNote());
                userID.setText(pcrProcess.getUser().getName());
                kitID.setText(pcrProcess.getKit().getName());
                sampleIDName.setText(pcrProcess.getSample().getName());
                systemID.setText(pcrProcess.getSystem().getName());
//                UserService.getUserByID(getActivity(), pcrProcess.getUserID(), new VolleyCallback() {
//                    @Override
//                    public void onSuccess(Object data) {
//                        User user = om.convertValue(data,User.class);
//                        userID.setText(user.getUserName());
//                    }
//
//                    @Override
//                    public void onFail(Object data) {
//
//                    }
//                });
//                KitService.getKitByID(getActivity(), pcrProcess.getKitID(), new VolleyCallback() {
//                    @Override
//                    public void onSuccess(Object data) {
//                        Kit kit = om.convertValue(data,Kit.class);
//                        kitID.setText(kit.getName());
//                    }
//
//                    @Override
//                    public void onFail(Object data) {
//
//                    }
//                });
//                SampleService.getSampleByID(getActivity(), pcrProcess.getSamplesID(), new VolleyCallback() {
//                    @Override
//                    public void onSuccess(Object data) {
//                        Sample sample = om.convertValue(data,Sample.class);
//                        sampleIDName.setText(sample.getName());
//                    }
//
//                    @Override
//                    public void onFail(Object data) {
//
//                    }
//                });
//                SystemService.getSystemByID(getActivity(), pcrProcess.getSystemID(), new VolleyCallback() {
//                    @Override
//                    public void onSuccess(Object data) {
//                        System system = om.convertValue(data,System.class);
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

//        viewHistory = getActivity().findViewById(R.id.viewHistory);
//        dialogViewHistory = new Dialog(getContext(), R.style.MaterialDialogSheetHistory);
//        dialogViewHistory.setContentView(R.layout.view_history_extract_layout);
//        dialogViewHistory.findViewById(R.id.allout).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialogViewHistory.dismiss();
//            }
//        });
//        dialogViewHistory.getWindow().setGravity(Gravity.BOTTOM);
//        dialogViewHistory.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        viewHistory.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialogViewHistory.show();
//            }
//        });

    }
    private void innitView(){
        id=getActivity().findViewById(R.id.id);
        startDate=getActivity().findViewById(R.id.startDate);
        endDate=getActivity().findViewById(R.id.endDate);
        linearLayout=getActivity().findViewById(R.id.out);
        note = getActivity().findViewById(R.id.note);
        userID = getActivity().findViewById(R.id.userID);
        kitID = getActivity().findViewById(R.id.kitID);
        sampleIDName = getActivity().findViewById(R.id.sampleID);
        systemID = getActivity().findViewById(R.id.systemID);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imm = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);

    }

}
