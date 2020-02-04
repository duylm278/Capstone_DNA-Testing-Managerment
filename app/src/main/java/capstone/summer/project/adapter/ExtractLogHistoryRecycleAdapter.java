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
import capstone.summer.project.model.Sample;
import capstone.summer.project.model.System;
import capstone.summer.project.model.User;
import lombok.NonNull;

public class ExtractLogHistoryRecycleAdapter extends RecyclerView.Adapter<ExtractLogHistoryRecycleAdapter.ExtractLogHistoryViewHolder> {
    private List<ExtractLog> extractLogs;
    private Activity activity;
    private ObjectMapper objectMapper = new ObjectMapper().configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);

    public ExtractLogHistoryRecycleAdapter(Activity activity, List<ExtractLog> extractLogs) {
        this.extractLogs = extractLogs;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ExtractLogHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.extract_log_item, parent, false);
        return new ExtractLogHistoryViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ExtractLogHistoryViewHolder holder, int position) {
        final ExtractLog extractLog = extractLogs.get(position);
        final ExtractProcess extractProcess;
        final ExtractProcess extractProcessPre;
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(extractLog.getEditDate());
            holder.editID.setText(extractLog.getID() + "");
            holder.editDate.setText(localDateTime.toLocalDate() + "");
            holder.editTime.setText(localDateTime.toLocalTime() + "");
//            extractProcess = objectMapper.readValue(extractLog.getLog(), ExtractProcess.class);
//            extractProcessPre = objectMapper.readValue(extractLog.getLogPrevious(), ExtractProcess.class);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Dialog dialogViewHistory = new Dialog(activity, R.style.MaterialDialogSheetHistory);
                    dialogViewHistory.setContentView(R.layout.view_history_extract_layout);

                    TextView tvAmountPre = dialogViewHistory.findViewById(R.id.amount_old);
                    TextView tvAmount = dialogViewHistory.findViewById(R.id.amount_new);
                    tvAmountPre.setText(extractLog.getLogPrevious().getAmount());
                    tvAmount.setText(extractLog.getLog().getAmount());
                    if (tvAmount.getText().equals(tvAmountPre.getText())) {
                        tvAmountPre.setTextColor(activity.getResources().getColor(R.color.colorGrayDark));
                        tvAmount.setTextColor(activity.getResources().getColor(R.color.colorGrayDark));
                    }

                    TextView tvUserPre = dialogViewHistory.findViewById(R.id.userID_old);
                    TextView tvUser = dialogViewHistory.findViewById(R.id.userID_new);
                    tvUserPre.setText(extractLog.getLogPrevious().getUser().getName());
                    tvUser.setText(extractLog.getLog().getUser().getName());
                    if (tvUser.getText().equals(tvUserPre.getText())) {
                        tvUserPre.setTextColor(activity.getResources().getColor(R.color.colorGrayDark));
                        tvUser.setTextColor(activity.getResources().getColor(R.color.colorGrayDark));
                    }

                    TextView tvKitPre = dialogViewHistory.findViewById(R.id.kitId_old);
                    TextView tvKit = dialogViewHistory.findViewById(R.id.kitId_new);
                    tvKitPre.setText(extractLog.getLogPrevious().getKit().getName());
                    tvKit.setText(extractLog.getLog().getKit().getName());
                    if (tvKit.getText().equals(tvKitPre.getText())) {
                        tvKitPre.setTextColor(activity.getResources().getColor(R.color.colorGrayDark));
                        tvKit.setTextColor(activity.getResources().getColor(R.color.colorGrayDark));
                    }


                    TextView tvSamplePre = dialogViewHistory.findViewById(R.id.sampleId_old);
                    TextView tvSample = dialogViewHistory.findViewById(R.id.sampleId_new);
                    tvSamplePre.setText(extractLog.getLogPrevious().getSample().getName());
                    tvSample.setText(extractLog.getLog().getSample().getName());
                    if (tvSample.getText().equals(tvSamplePre.getText())) {
                        tvSamplePre.setTextColor(activity.getResources().getColor(R.color.colorGrayDark));
                        tvSample.setTextColor(activity.getResources().getColor(R.color.colorGrayDark));
                    }

                    TextView tvSystemADNPre = dialogViewHistory.findViewById(R.id.systemIDADN_old);
                    TextView tvSystemADN = dialogViewHistory.findViewById(R.id.systemIDADN_new);
                    tvSystemADNPre.setText(extractLog.getLogPrevious().getSystemADN().getName());
                    tvSystemADN.setText(extractLog.getLog().getSystemADN().getName());
                    if (tvSystemADN.getText().equals(tvSystemADNPre.getText())) {
                        tvSystemADNPre.setTextColor(activity.getResources().getColor(R.color.colorGrayDark));
                        tvSystemADN.setTextColor(activity.getResources().getColor(R.color.colorGrayDark));
                    }


                    TextView tvSystemExtractPre = dialogViewHistory.findViewById(R.id.systemIDExtract_old);
                    TextView tvSystemExtract = dialogViewHistory.findViewById(R.id.systemIDExtract_new);
                    tvSystemExtractPre.setText(extractLog.getLogPrevious().getSystemExtract().getName());
                    tvSystemExtract.setText(extractLog.getLog().getSystemExtract().getName());
                    if (tvSystemExtract.getText().equals(tvSystemExtractPre.getText())) {
                        tvSystemExtractPre.setTextColor(activity.getResources().getColor(R.color.colorGrayDark));
                        tvSystemExtract.setTextColor(activity.getResources().getColor(R.color.colorGrayDark));
                    }

                    String startDate = extractLog.getLog().getStartDate();
                    String endDate = extractLog.getLog().getEndDate();
                    String startDatePre = extractLog.getLogPrevious().getStartDate();
                    String endDatePre = extractLog.getLogPrevious().getEndDate();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");

                    LocalDateTime startDateLocate = LocalDateTime.parse(startDate.split("\\+")[0]);
                    LocalDateTime endDateLocate = LocalDateTime.parse(endDate.split("\\+")[0]);
                    LocalDateTime startDateLocatePre = LocalDateTime.parse(startDatePre.split("\\+")[0]);
                    LocalDateTime endDateLocatePre = LocalDateTime.parse(endDatePre.split("\\+")[0]);

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
                    tvNotePre.setText(extractLog.getLogPrevious().getNote());
                    tvNote.setText(extractLog.getLog().getNote());
                    if (tvNote.getText().equals(tvNotePre.getText())) {
                        tvNotePre.setTextColor(activity.getResources().getColor(R.color.colorGrayDark));
                        tvNote.setTextColor(activity.getResources().getColor(R.color.colorGrayDark));
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
        return extractLogs == null ? 0 : extractLogs.size();
    }

    class ExtractLogHistoryViewHolder extends RecyclerView.ViewHolder {

        private TextView editID;
        private TextView editDate;
        private TextView editTime;

        public ExtractLogHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            editID = itemView.findViewById(R.id.edit_id);
            editDate = itemView.findViewById(R.id.edit_date);
            editTime = itemView.findViewById(R.id.edit_time);
        }
    }
}
