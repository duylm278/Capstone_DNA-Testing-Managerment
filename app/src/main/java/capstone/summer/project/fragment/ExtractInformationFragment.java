package capstone.summer.project.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import capstone.summer.R;
import capstone.summer.project.Service.ExtractProcessService;
import capstone.summer.project.Service.KitService;
import capstone.summer.project.Service.SampleService;
import capstone.summer.project.Service.SystemService;
import capstone.summer.project.Service.UserService;
import capstone.summer.project.Service.VolleyCallback;
import capstone.summer.project.model.ExtractProcess;
import capstone.summer.project.model.Kit;
import capstone.summer.project.model.Sample;
import capstone.summer.project.model.System;
import capstone.summer.project.model.User;
import capstone.summer.project.utils.ConstantManager;

public class ExtractInformationFragment extends Fragment {
    private TextView id, startDate, endDate, amount, userID, kitID, sampleIDName, systemIDADN, systemIDExtract;
    private TextInputLayout note;
    private ObjectMapper om = new ObjectMapper();
    private InputMethodManager imm;
    private Button updateExtract;
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
        View rootView = inflater.inflate(R.layout.fragment_extract_information, container, false);
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
        final Intent intent = getActivity().getIntent();
        final String sampleID = intent.getStringExtra("SampleID");
        final ExtractProcess[] extractProcess = new ExtractProcess[1];
        ExtractProcessService.getExtractProcessByID(getActivity(), sampleID, new VolleyCallback() {
            @Override
            public void onSuccess(Object data) {
                extractProcess[0] = om.convertValue(data, ExtractProcess.class);
                id.setText(extractProcess[0].getID() + "");
                LocalDateTime startDateLocate = LocalDateTime.parse(extractProcess[0].getStartDate());
                LocalDateTime endDateLocate = LocalDateTime.parse(extractProcess[0].getEndDate());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a");
                startDate.setText(startDateLocate.format(formatter));
                endDate.setText(endDateLocate.format(formatter));
                amount.setText(extractProcess[0].getAmount());
                note.getEditText().setText(extractProcess[0].getNote());
//                UserService.getUserByID(getActivity(), extractProcess[0].getUserID(), new VolleyCallback() {
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
                userID.setText(extractProcess[0].getUser().getName());
//                KitService.getKitByID(getActivity(), extractProcess[0].getKitID(), new VolleyCallback() {
//                    @Override
//                    public void onSuccess(Object data) {
//                        Kit kit = om.convertValue(data, Kit.class);
//                        kitID.setText(kit.getName());
//                    }
//
//                    @Override
//                    public void onFail(Object data) {
//
//                    }
//                });
                kitID.setText(extractProcess[0].getKit().getName());
//                SampleService.getSampleByID(getActivity(), extractProcess[0].getSamplesID(), new VolleyCallback() {
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
                sampleIDName.setText(extractProcess[0].getSample().getName());
                systemIDADN.setText(extractProcess[0].getSystemADN().getName());
                systemIDExtract.setText(extractProcess[0].getSystemExtract().getName());
//                SystemService.getSystemByID(getActivity(), extractProcess[0].getSystemIDADN(), new VolleyCallback() {
//                    @Override
//                    public void onSuccess(Object data) {
//                        System system = om.convertValue(data, System.class);
//                        systemIDADN.setText(system.getName());
//                    }
//
//                    @Override
//                    public void onFail(Object data) {
//
//                    }
//                });
//                SystemService.getSystemByID(getActivity(), extractProcess[0].getSystemIDExtract(), new VolleyCallback() {
//                    @Override
//                    public void onSuccess(Object data) {
//                        System system = om.convertValue(data, System.class);
//                        systemIDExtract.setText(system.getName());
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

        updateExtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressdialog = new ProgressDialog(getActivity());
                progressdialog.setMessage("Please Wait....");
                progressdialog.show();
                extractProcess[0].setNote(note.getEditText().getText() + "");
                SharedPreferences sharedPreferences = getActivity().getApplicationContext().getSharedPreferences(ConstantManager.USER_MANAGER, Context.MODE_PRIVATE);
                ExtractProcessService.UpdateExtract(getActivity(), extractProcess[0], Integer.parseInt(sharedPreferences.getString(ConstantManager.USER_ID, "")), new VolleyCallback() {
                    @Override
                    public void onSuccess(Object data) {
                        progressdialog.dismiss();
                        Toast.makeText(getActivity(), "Xác nhận thành công !!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFail(Object data) {
                        progressdialog.dismiss();
                        Toast.makeText(getActivity(), "Xác nhận thất bại !!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void innitView() {
        id = getActivity().findViewById(R.id.id);
        startDate = getActivity().findViewById(R.id.startDate);
        endDate = getActivity().findViewById(R.id.endDate);
        amount = getActivity().findViewById(R.id.amount);
        note = getActivity().findViewById(R.id.note);
        userID = getActivity().findViewById(R.id.userID);
        kitID = getActivity().findViewById(R.id.kitID);
        sampleIDName = getActivity().findViewById(R.id.sampleID);
        systemIDADN = getActivity().findViewById(R.id.systemIDADN);
        systemIDExtract = getActivity().findViewById(R.id.systemIDExtract);
        linearLayout = getActivity().findViewById(R.id.out);
        updateExtract = getActivity().findViewById(R.id.btbGoToUpdateExtract);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imm = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);

    }
}
