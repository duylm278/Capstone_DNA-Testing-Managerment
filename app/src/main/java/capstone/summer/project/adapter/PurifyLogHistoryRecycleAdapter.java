package capstone.summer.project.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import capstone.summer.R;
import capstone.summer.project.Service.KitService;
import capstone.summer.project.Service.SampleService;
import capstone.summer.project.Service.SystemService;
import capstone.summer.project.Service.UserService;
import capstone.summer.project.Service.VolleyCallback;
import capstone.summer.project.model.ExtractLog;
import capstone.summer.project.model.ExtractProcess;
import capstone.summer.project.model.Kit;
import capstone.summer.project.model.PurifyLog;
import capstone.summer.project.model.PurifyProcess;
import capstone.summer.project.model.Sample;
import capstone.summer.project.model.System;
import capstone.summer.project.model.User;
import lombok.NonNull;

public class
PurifyLogHistoryRecycleAdapter extends RecyclerView.Adapter<PurifyLogHistoryRecycleAdapter.PurifyLogHistoryViewHolder> {
    private List<PurifyLog> purifyLogs;
    private Activity activity;
    private ObjectMapper objectMapper = new ObjectMapper().configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);

    public PurifyLogHistoryRecycleAdapter(Activity activity, List<PurifyLog> purifyLogs) {
        this.purifyLogs = purifyLogs;
        this.activity = activity;
    }

    @NonNull
    @Override
    public PurifyLogHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.extract_log_item, parent, false);
        return new PurifyLogHistoryViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull PurifyLogHistoryViewHolder holder, int position) {
        final PurifyLog purifyLog = purifyLogs.get(position);
        final PurifyProcess purifyProcess;
        final PurifyProcess purifyProcessPre;
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(purifyLog.getEditDate());
            holder.editID.setText(purifyLog.getID() + "");
            holder.editDate.setText(localDateTime.toLocalDate() + "");
            holder.editTime.setText(localDateTime.toLocalTime() + "");
//            purifyProcess = objectMapper.readValue(purifyLog.getLog(),PurifyProcess.class);
//            purifyProcessPre = objectMapper.readValue(purifyLog.getLogPrevious(),PurifyProcess.class);
//
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Dialog dialogViewHistory = new Dialog(activity, R.style.MaterialDialogSheetHistory);
                    dialogViewHistory.setContentView(R.layout.view_history_purify_layout);

                    TextView tvUserPre = dialogViewHistory.findViewById(R.id.userID_old);
                    TextView tvUser = dialogViewHistory.findViewById(R.id.userID_new);
                    tvUserPre.setText(purifyLog.getLogPrevious().getUser().getName());
                    tvUser.setText(purifyLog.getLog().getUser().getName());
                    if (tvUser.getText().equals(tvUserPre.getText())) {
                        tvUserPre.setTextColor(activity.getResources().getColor(R.color.colorGrayDark));
                        tvUser.setTextColor(activity.getResources().getColor(R.color.colorGrayDark));
                    }

                    TextView tvKitPre = dialogViewHistory.findViewById(R.id.kitId_old);
                    TextView tvKit = dialogViewHistory.findViewById(R.id.kitId_new);
                    tvKitPre.setText(purifyLog.getLogPrevious().getKit().getName());
                    tvKit.setText(purifyLog.getLog().getKit().getName());
                    if (tvKit.getText().equals(tvKitPre.getText())) {
                        tvKitPre.setTextColor(activity.getResources().getColor(R.color.colorGrayDark));
                        tvKit.setTextColor(activity.getResources().getColor(R.color.colorGrayDark));
                    }

                    TextView tvSamplePre = dialogViewHistory.findViewById(R.id.sampleId_old);
                    TextView tvSample = dialogViewHistory.findViewById(R.id.sampleId_new);
                    tvSamplePre.setText(purifyLog.getLogPrevious().getSample().getName());
                    tvSample.setText(purifyLog.getLog().getSample().getName());
                    if (tvSample.getText().equals(tvSamplePre.getText())) {
                        tvSamplePre.setTextColor(activity.getResources().getColor(R.color.colorGrayDark));
                        tvSample.setTextColor(activity.getResources().getColor(R.color.colorGrayDark));
                    }

                    dialogViewHistory.findViewById(R.id.allout).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogViewHistory.dismiss();
                        }
                    });


                    String startDate = purifyLog.getLog().getStartDate();
                    String endDate = purifyLog.getLog().getEndDate();
                    String startDatePre = purifyLog.getLogPrevious().getStartDate();
                    String endDatePre = purifyLog.getLogPrevious().getEndDate();
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

                    TextView tvNotePre = dialogViewHistory.findViewById(R.id.note_old);
                    TextView tvNote = dialogViewHistory.findViewById(R.id.note_new);
                    tvNotePre.setText(purifyLog.getLogPrevious().getNote());
                    tvNote.setText(purifyLog.getLog().getNote());
                    if (tvNote.getText().equals(tvNotePre.getText())) {
                        tvNotePre.setTextColor(activity.getResources().getColor(R.color.colorGrayDark));
                        tvNote.setTextColor(activity.getResources().getColor(R.color.colorGrayDark));
                    }
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
        return purifyLogs == null ? 0 : purifyLogs.size();
    }

    class PurifyLogHistoryViewHolder extends RecyclerView.ViewHolder {

        private TextView editID;
        private TextView editDate;
        private TextView editTime;

        public PurifyLogHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            editID = itemView.findViewById(R.id.edit_id);
            editDate = itemView.findViewById(R.id.edit_date);
            editTime = itemView.findViewById(R.id.edit_time);
        }
    }
}
