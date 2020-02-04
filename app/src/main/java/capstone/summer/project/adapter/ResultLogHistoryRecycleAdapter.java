package capstone.summer.project.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
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
import capstone.summer.project.Service.KitService;
import capstone.summer.project.Service.SampleService;
import capstone.summer.project.Service.SystemService;
import capstone.summer.project.Service.UserService;
import capstone.summer.project.Service.VolleyCallback;
import capstone.summer.project.model.Kit;
import capstone.summer.project.model.PCRLog;
import capstone.summer.project.model.PCRProcess;
import capstone.summer.project.model.Result;
import capstone.summer.project.model.ResultLog;
import capstone.summer.project.model.Sample;
import capstone.summer.project.model.System;
import capstone.summer.project.model.User;

public class ResultLogHistoryRecycleAdapter extends RecyclerView.Adapter<ResultLogHistoryRecycleAdapter.ResultLogHistoryViewHolder> {
    private List<ResultLog> resultLogs;
    private Activity activity;
    private ObjectMapper objectMapper = new ObjectMapper().configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);

    public ResultLogHistoryRecycleAdapter(Activity activity, List<ResultLog> resultLogs) {
        this.resultLogs = resultLogs;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ResultLogHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_log_item, parent, false);
        return new ResultLogHistoryViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultLogHistoryViewHolder holder, int position) {
        final ResultLog resultLog = resultLogs.get(position);
        final Result result;
        final Result resultPre;
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(resultLog.getEditDate());
            holder.editID.setText(resultLog.getID() + "");
            holder.editDate.setText(localDateTime.toLocalDate() + "");
            holder.editTime.setText(localDateTime.toLocalTime() + "");
            result = objectMapper.readValue(resultLog.getLog(), Result.class);
            resultPre = objectMapper.readValue(resultLog.getLogPrevious(), Result.class);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Dialog dialogViewHistory = new Dialog(activity, R.style.MaterialDialogSheetHistory);
                    dialogViewHistory.setContentView(R.layout.view_history_result_layout);
                    dialogViewHistory.getWindow().setGravity(Gravity.BOTTOM);
                    dialogViewHistory.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    dialogViewHistory.show();

                    final TextView tvUserPre = dialogViewHistory.findViewById(R.id.userID_old);
                    final TextView tvUser = dialogViewHistory.findViewById(R.id.userID_new);
                    UserService.getUserByID(activity, resultPre.getUserID(), new VolleyCallback() {
                        @Override
                        public void onSuccess(Object data) {
                            User user = (User) data;
                            tvUserPre.setText(user.getName());
                        }

                        @Override
                        public void onFail(Object data) {

                        }
                    });
                    UserService.getUserByID(activity, result.getUserID(), new VolleyCallback() {
                        @Override
                        public void onSuccess(Object data) {
                            User user = (User) data;
                            tvUser.setText(user.getName());
                        }

                        @Override
                        public void onFail(Object data) {

                        }
                    });
                    if (tvUser.getText().equals(tvUserPre.getText())){
                        tvUserPre.setTextColor(activity.getResources().getColor(R.color.colorGrayDark));
                        tvUser.setTextColor(activity.getResources().getColor(R.color.colorGrayDark));
                    }

                    TextView tvSystemPre = dialogViewHistory.findViewById(R.id.systemID_old);
                    TextView tvSystem = dialogViewHistory.findViewById(R.id.systemID_new);
                    tvSystemPre.setText(resultPre.getSoftware());
                    tvSystem.setText(result.getSoftware());
                    if (tvSystem.getText().equals(tvSystemPre.getText())) {
                        tvSystemPre.setTextColor(activity.getResources().getColor(R.color.colorGrayDark));
                        tvSystem.setTextColor(activity.getResources().getColor(R.color.colorGrayDark));
                    }

                    TextView tvExcelPre = dialogViewHistory.findViewById(R.id.excel_old);
                    TextView tvExcel = dialogViewHistory.findViewById(R.id.excel_new);
                    tvExcelPre.setText(resultPre.getExcelName());
                    tvExcel.setText(result.getExcelName());
                    if (tvExcel.getText().equals(tvExcelPre.getText())) {
                        tvExcelPre.setTextColor(activity.getResources().getColor(R.color.colorGrayDark));
                        tvExcel.setTextColor(activity.getResources().getColor(R.color.colorGrayDark));
                    }

                    final TextView tvSamplePre = dialogViewHistory.findViewById(R.id.sampleId_old);
                    final TextView tvSample = dialogViewHistory.findViewById(R.id.sampleId_new);

                    SampleService.getSampleByID(activity, resultPre.getSamplesID(), new VolleyCallback() {
                        @Override
                        public void onSuccess(Object data) {
                            Sample sample = (Sample)data;
                            tvSamplePre.setText(sample.getName());
                        }

                        @Override
                        public void onFail(Object data) {

                        }
                    });
                    SampleService.getSampleByID(activity, result.getSamplesID(), new VolleyCallback() {
                        @Override
                        public void onSuccess(Object data) {
                            Sample sample = (Sample)data;
                            tvSample.setText(sample.getName());

                        }

                        @Override
                        public void onFail(Object data) {

                        }
                    });
                    if (tvSample.getText().equals(tvSamplePre.getText())) {
                        tvSamplePre.setTextColor(activity.getResources().getColor(R.color.colorGrayDark));
                        tvSample.setTextColor(activity.getResources().getColor(R.color.colorGrayDark));
                    }

                    String startDate = result.getStartDate();
                    String endDate = result.getEndDate();
                    String startDatePre = resultPre.getStartDate();
                    String endDatePre = resultPre.getEndDate();
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
                    tvNotePre.setText(resultPre.getNote());
                    tvNote.setText(result.getNote());
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

                }
            });
        } catch (Exception e){
            Log.e("LogAdapter",e.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return resultLogs == null ? 0 : resultLogs.size();
    }

    class ResultLogHistoryViewHolder extends RecyclerView.ViewHolder {

        private TextView editID;
        private TextView editDate;
        private TextView editTime;

        public ResultLogHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            editID = itemView.findViewById(R.id.edit_id);
            editDate = itemView.findViewById(R.id.edit_date);
            editTime = itemView.findViewById(R.id.edit_time);
        }
    }
}
