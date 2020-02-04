package capstone.summer.project.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import capstone.summer.R;
import capstone.summer.project.Service.SampleService;
import capstone.summer.project.Service.SystemService;
import capstone.summer.project.Service.UserService;
import capstone.summer.project.Service.VolleyCallback;
import capstone.summer.project.model.ElectrophoresisLog;
import capstone.summer.project.model.ElectrophoresisProcess;
import capstone.summer.project.model.Sample;
import capstone.summer.project.model.System;
import capstone.summer.project.model.User;

public class ElectrophoresisLogHistoryRecycleAdapter extends RecyclerView.Adapter<ElectrophoresisLogHistoryRecycleAdapter.ElectrophoresisLogHistoryViewHolder> {

    private List<ElectrophoresisLog> electrophoresisLogs;
    private Activity activity;
    private ObjectMapper objectMapper = new ObjectMapper().configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);

    public ElectrophoresisLogHistoryRecycleAdapter(Activity activity, List<ElectrophoresisLog> electrophoresisLogs) {
        this.electrophoresisLogs = electrophoresisLogs;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ElectrophoresisLogHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.electrophoresis_log_item, parent, false);
        return new ElectrophoresisLogHistoryViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ElectrophoresisLogHistoryViewHolder holder, int position) {
        final ElectrophoresisLog electrophoresisLog = electrophoresisLogs.get(position);
        final ElectrophoresisProcess electrophoresisProcess;
        final ElectrophoresisProcess electrophoresisProcessPre;
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(electrophoresisLog.getEditDate());
            holder.editID.setText(electrophoresisLog.getID() + "");
            holder.editDate.setText(localDateTime.toLocalDate() + "");
            holder.editTime.setText(localDateTime.toLocalTime() + "");
//            electrophoresisProcess = objectMapper.readValue(electrophoresisLog.getLog(),ElectrophoresisProcess.class);
//            electrophoresisProcessPre = objectMapper.readValue(electrophoresisLog.getLogPrevious(),ElectrophoresisProcess.class);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Dialog dialogViewHistory = new Dialog(activity, R.style.MaterialDialogSheetHistory);
                    dialogViewHistory.setContentView(R.layout.view_history_electrophoresis_layout);

                    TextView tvUserPre = dialogViewHistory.findViewById(R.id.userID_old);
                    TextView tvUser = dialogViewHistory.findViewById(R.id.userID_new);
                    tvUserPre.setText(electrophoresisLog.getLogPrevious().getUser().getName());
                    tvUser.setText(electrophoresisLog.getLog().getUser().getName());
                    if (tvUser.getText().equals(tvUserPre.getText())) {
                        tvUserPre.setTextColor(activity.getResources().getColor(R.color.colorGrayDark));
                        tvUser.setTextColor(activity.getResources().getColor(R.color.colorGrayDark));
                    }

                    TextView tvGelPre = dialogViewHistory.findViewById(R.id.gelName_old);
                    TextView tvGel = dialogViewHistory.findViewById(R.id.gelName_new);
                    tvGelPre.setText(electrophoresisLog.getLogPrevious().getGelName());
                    tvGel.setText(electrophoresisLog.getLog().getGelName());
                    if (tvGel.getText().equals(tvGelPre.getText())) {
                        tvGelPre.setTextColor(activity.getResources().getColor(R.color.colorGrayDark));
                        tvGel.setTextColor(activity.getResources().getColor(R.color.colorGrayDark));
                    }


                    TextView tvSamplePre = dialogViewHistory.findViewById(R.id.sampleId_old);
                    TextView tvSample = dialogViewHistory.findViewById(R.id.sampleId_new);
                    tvSamplePre.setText(electrophoresisLog.getLogPrevious().getSample().getName());
                    tvSample.setText(electrophoresisLog.getLog().getSample().getName());
                    if (tvSample.getText().equals(tvSamplePre.getText())) {
                        tvSamplePre.setTextColor(activity.getResources().getColor(R.color.colorGrayDark));
                        tvSample.setTextColor(activity.getResources().getColor(R.color.colorGrayDark));
                    }

                    TextView tvSystemExtractPre = dialogViewHistory.findViewById(R.id.systemID_old);
                    TextView tvSystemExtract = dialogViewHistory.findViewById(R.id.systemID_new);
                    tvSystemExtractPre.setText(electrophoresisLog.getLogPrevious().getSystem().getName());
                    tvSystemExtract.setText(electrophoresisLog.getLog().getSystem().getName());
                    if (tvSystemExtract.getText().equals(tvSystemExtractPre.getText())) {
                        tvSystemExtractPre.setTextColor(activity.getResources().getColor(R.color.colorGrayDark));
                        tvSystemExtract.setTextColor(activity.getResources().getColor(R.color.colorGrayDark));
                    }



                    String startDate = electrophoresisLog.getLog().getStartDate();
                    String endDate = electrophoresisLog.getLog().getEndDate();
                    String startDatePre = electrophoresisLog.getLogPrevious().getStartDate();
                    String endDatePre = electrophoresisLog.getLogPrevious().getEndDate();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");

                    LocalDateTime startDateLocate = LocalDateTime.parse(startDate.split("\\+")[0]);
                    LocalDateTime endDateLocate = LocalDateTime.parse(endDate.split("\\+")[0]);
                    LocalDateTime startDateLocatePre = LocalDateTime.parse(startDatePre.split("\\+")[0]);
                    LocalDateTime endDateLocatePre = LocalDateTime.parse(endDatePre.split("\\+")[0]);

//                    LocalDateTime startDateLocate = LocalDateTime.parse(startDate);
//                    LocalDateTime endDateLocate = LocalDateTime.parse(endDate);
//                    LocalDateTime startDateLocatePre = LocalDateTime.parse(startDatePre);
//                    LocalDateTime endDateLocatePre = LocalDateTime.parse(endDatePre);
                    TextView tvStartPre = dialogViewHistory.findViewById(R.id.startDate_old);
                    TextView tvStart = dialogViewHistory.findViewById(R.id.startDate_new);
                    tvStart.setText(startDateLocate.format(formatter));
                    tvStartPre.setText(startDateLocatePre.format(formatter));
                    if (tvStart.getText().equals(tvStartPre.getText())) {
                        tvStartPre.setTextColor(activity.getResources().getColor(R.color.colorGrayDark));
                        tvStart.setTextColor(activity.getResources().getColor(R.color.colorGrayDark));
                    }

                    TextView tvEndPre = dialogViewHistory.findViewById(R.id.endDate_old);
                    TextView tvEnd = dialogViewHistory.findViewById(R.id.endDate_new);
                    tvEnd.setText(endDateLocate.format(formatter));
                    tvEndPre.setText(endDateLocatePre.format(formatter));
                    if (tvEnd.getText().equals(tvEndPre.getText())) {
                        tvEndPre.setTextColor(activity.getResources().getColor(R.color.colorGrayDark));
                        tvEnd.setTextColor(activity.getResources().getColor(R.color.colorGrayDark));
                    }
                    dialogViewHistory.findViewById(R.id.allout).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogViewHistory.dismiss();
                        }
                    });
                    dialogViewHistory.getWindow().setGravity(Gravity.BOTTOM);
                    dialogViewHistory.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    dialogViewHistory.show();
                }
            });
        } catch (Exception ex) {
//            Log.e(TAG, "onResponse: " + response);
        }


    }

    @Override
    public int getItemCount() {
        return electrophoresisLogs == null ? 0 : electrophoresisLogs.size();
    }

    class ElectrophoresisLogHistoryViewHolder extends RecyclerView.ViewHolder {

        private TextView editID;
        private TextView editDate;
        private TextView editTime;

        public ElectrophoresisLogHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            editID = itemView.findViewById(R.id.edit_id);
            editDate = itemView.findViewById(R.id.edit_date);
            editTime = itemView.findViewById(R.id.edit_time);
        }
    }
}
