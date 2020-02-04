package capstone.summer.project.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import capstone.summer.project.model.ExtractLog;
import capstone.summer.project.model.ExtractProcess;
import capstone.summer.project.model.Kit;
import capstone.summer.project.model.PCRLog;
import capstone.summer.project.model.PCRProcess;
import capstone.summer.project.model.Sample;
import capstone.summer.project.model.System;
import capstone.summer.project.model.User;

public class PCRLogHistoryRecycleAdapter extends RecyclerView.Adapter<PCRLogHistoryRecycleAdapter.PCRLogHistoryViewHolder> {
    private List<PCRLog> pcrLogs;
    private Activity activity;
    private ObjectMapper objectMapper = new ObjectMapper().configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);

    public PCRLogHistoryRecycleAdapter(Activity activity, List<PCRLog> pcrLogs) {
        this.pcrLogs = pcrLogs;
        this.activity = activity;
    }

    @NonNull
    @Override
    public PCRLogHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.pcr_log_item, parent, false);
        return new PCRLogHistoryViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull PCRLogHistoryViewHolder holder, int position) {
        final PCRLog pcrLog = pcrLogs.get(position);
        final PCRProcess pcrProcess;
        final PCRProcess pcrProcessPre;
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(pcrLog.getEditDate());
            holder.editID.setText(pcrLog.getID() + "");
            holder.editDate.setText(localDateTime.toLocalDate() + "");
            holder.editTime.setText(localDateTime.toLocalTime() + "");
//            pcrProcess = objectMapper.readValue(pcrLog.getLog(), PCRProcess.class);
//            pcrProcessPre = objectMapper.readValue(pcrLog.getLogPrevious(), PCRProcess.class);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Dialog dialogViewHistory = new Dialog(activity, R.style.MaterialDialogSheetHistory);
                    dialogViewHistory.setContentView(R.layout.view_history_pcr_layout);
                    dialogViewHistory.getWindow().setGravity(Gravity.BOTTOM);
                    dialogViewHistory.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    dialogViewHistory.show();

                    TextView tvUserPre = dialogViewHistory.findViewById(R.id.userID_old);
                    TextView tvUser = dialogViewHistory.findViewById(R.id.userID_new);
                    tvUserPre.setText(pcrLog.getLogPrevious().getUser().getName());
                    tvUser.setText(pcrLog.getLog().getUser().getName());
                    if (tvUser.getText().equals(tvUserPre.getText())) {
                        tvUserPre.setTextColor(activity.getResources().getColor(R.color.colorGrayDark));
                        tvUser.setTextColor(activity.getResources().getColor(R.color.colorGrayDark));
                    }

                    TextView tvKitPre = dialogViewHistory.findViewById(R.id.kitId_old);
                    TextView tvKit = dialogViewHistory.findViewById(R.id.kitId_new);
                    tvKitPre.setText(pcrLog.getLogPrevious().getKit().getName());
                    tvKit.setText(pcrLog.getLog().getKit().getName());
                    if (tvKit.getText().equals(tvKitPre.getText())) {
                        tvKitPre.setTextColor(activity.getResources().getColor(R.color.colorGrayDark));
                        tvKit.setTextColor(activity.getResources().getColor(R.color.colorGrayDark));
                    }

                     TextView tvSamplePre = dialogViewHistory.findViewById(R.id.sampleId_old);
                    TextView tvSample = dialogViewHistory.findViewById(R.id.sampleId_new);
                    tvSamplePre.setText(pcrLog.getLogPrevious().getSample().getName());
                    tvSample.setText(pcrLog.getLog().getSample().getName());
                    if (tvSample.getText().equals(tvSamplePre.getText())) {
                        tvSamplePre.setTextColor(activity.getResources().getColor(R.color.colorGrayDark));
                        tvSample.setTextColor(activity.getResources().getColor(R.color.colorGrayDark));
                    }

                     TextView tvSystemExtractPre = dialogViewHistory.findViewById(R.id.systemID_old);
                    TextView tvSystemExtract = dialogViewHistory.findViewById(R.id.systemID_new);
                    tvSystemExtractPre.setText(pcrLog.getLogPrevious().getSystem().getName());
                    tvSystemExtract.setText(pcrLog.getLog().getSystem().getName());
                    if (tvSystemExtract.getText().equals(tvSystemExtractPre.getText())) {
                        tvSystemExtractPre.setTextColor(activity.getResources().getColor(R.color.colorGrayDark));
                        tvSystemExtract.setTextColor(activity.getResources().getColor(R.color.colorGrayDark));
                    }

                    String startDate = pcrLog.getLog().getStartDate();
                    String endDate = pcrLog.getLog().getEndDate();
                    String startDatePre = pcrLog.getLogPrevious().getStartDate();
                    String endDatePre = pcrLog.getLogPrevious().getEndDate();
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
                    tvNotePre.setText(pcrLog.getLogPrevious().getNote());
                    tvNote.setText(pcrLog.getLog().getNote());
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
        } catch (Exception e) {
            Log.e("LogAdapter", e.getMessage());
        }
//
//                    ((TextView)dialogViewHistory.findViewById(R.id.amount_old)).setText(extractProcess.getAmount());
//                    ((TextView)dialogViewHistory.findViewById(R.id.amount_new)).setText(extractProcessCur.getAmount());
//

//
//                    ((TextView)dialogViewHistory.findViewById(R.id.gdgSignal_old)).setText(extractProcess.getGDGSignal());
//                    ((TextView)dialogViewHistory.findViewById(R.id.gdgSignal_new)).setText(extractProcessCur.getGDGSignal());
//
//                    KitService.getKitByID(activity, extractProcess.getKitID(), new VolleyCallback() {
//                        @Override
//                        public void onSuccess(Object data) {
//                            Kit kit = (Kit)data;
//                            ((TextView)dialogViewHistory.findViewById(R.id.kitId_old)).setText(kit.getName());
//                        }
//
//                        @Override
//                        public void onFail(Object data) {
//
//                        }
//                    });
//                    KitService.getKitByID(activity, extractProcessCur.getKitID(), new VolleyCallback() {
//                        @Override
//                        public void onSuccess(Object data) {
//                            Kit kit = (Kit)data;
//                            ((TextView)dialogViewHistory.findViewById(R.id.kitId_new)).setText(kit.getName());
//                        }
//
//                        @Override
//                        public void onFail(Object data) {
//
//                        }
//                    });
//
//                    SampleService.getSampleByID(activity, extractProcess.getSamplesID(), new VolleyCallback() {
//                        @Override
//                        public void onSuccess(Object data) {
//                            Sample sample = (Sample)data;
//                            ((TextView)dialogViewHistory.findViewById(R.id.sampleId_old)).setText(sample.getName());
//                        }
//
//                        @Override
//                        public void onFail(Object data) {
//
//                        }
//                    });
//                    SampleService.getSampleByID(activity, extractProcessCur.getSamplesID(), new VolleyCallback() {
//                        @Override
//                        public void onSuccess(Object data) {
//                            Sample sample = (Sample)data;
//                            ((TextView)dialogViewHistory.findViewById(R.id.sampleId_new)).setText(sample.getName());
//                        }
//
//                        @Override
//                        public void onFail(Object data) {
//
//                        }
//                    });
//
//                    SystemService.getSystemByID(activity, extractProcess.getSystemIDADN(), new VolleyCallback() {
//                        @Override
//                        public void onSuccess(Object data) {
//                            System system = (System)data;
//                            ((TextView)dialogViewHistory.findViewById(R.id.systemIDADN_old)).setText(system.getName());
//                        }
//
//                        @Override
//                        public void onFail(Object data) {
//
//                        }
//                    });
//                    SystemService.getSystemByID(activity, extractProcessCur.getSystemIDADN(), new VolleyCallback() {
//                        @Override
//                        public void onSuccess(Object data) {
//                            System system = (System)data;
//                            ((TextView)dialogViewHistory.findViewById(R.id.systemIDADN_new)).setText(system.getName());
//                        }
//
//                        @Override
//                        public void onFail(Object data) {
//
//                        }
//                    });
//
//                    SystemService.getSystemByID(activity, extractProcess.getSystemIDExtract(), new VolleyCallback() {
//                        @Override
//                        public void onSuccess(Object data) {
//                            System system = (System)data;
//                            ((TextView)dialogViewHistory.findViewById(R.id.systemIDExtract_old)).setText(system.getName());
//                        }
//
//                        @Override
//                        public void onFail(Object data) {
//
//                        }
//                    });
//                    SystemService.getSystemByID(activity, extractProcessCur.getSystemIDExtract(), new VolleyCallback() {
//                        @Override
//                        public void onSuccess(Object data) {
//                            System system = (System)data;
//                            ((TextView)dialogViewHistory.findViewById(R.id.systemIDExtract_new)).setText(system.getName());
//                        }
//
//                        @Override
//                        public void onFail(Object data) {
//
//                        }
//                    });
//
//
////                    LocalDateTime startDateLocate = LocalDateTime.parse(extractProcess.getStartDate());
////                    LocalDateTime endDateLocate = LocalDateTime.parse(extractProcess.getEndDate());
//                    LocalDateTime startDateLocateCur = LocalDateTime.parse(extractProcessCur.getStartDate());
//                    LocalDateTime endDateLocateCur = LocalDateTime.parse(extractProcessCur.getEndDate());
//                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");
//
//                    ((TextView)dialogViewHistory.findViewById(R.id.startDate_old)).setText(extractProcess.getStartDate());
//                    ((TextView)dialogViewHistory.findViewById(R.id.startDate_new)).setText(startDateLocateCur.format(formatter));
//
//                    ((TextView)dialogViewHistory.findViewById(R.id.endDate_old)).setText(extractProcess.getEndDate());
//                    ((TextView)dialogViewHistory.findViewById(R.id.endDate_new)).setText(endDateLocateCur.format(formatter));
//
//                    ((TextView)dialogViewHistory.findViewById(R.id.note_old)).setText(extractProcess.getNote());
//                    ((TextView)dialogViewHistory.findViewById(R.id.note_new)).setText(extractProcessCur.getNote());
//
//                    dialogViewHistory.findViewById(R.id.allout).setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            dialogViewHistory.dismiss();
//                        }
//                    });
//                    dialogViewHistory.getWindow().setGravity(Gravity.BOTTOM);
//                    dialogViewHistory.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//                    dialogViewHistory.show();
//                }
//            });
//        }catch (Exception ex){
////            Log.e(TAG, "onResponse: " + response);
//        }
//

    }

    @Override
    public int getItemCount() {
        return pcrLogs == null ? 0 : pcrLogs.size();
    }

    class PCRLogHistoryViewHolder extends RecyclerView.ViewHolder {

        private TextView editID;
        private TextView editDate;
        private TextView editTime;

        public PCRLogHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            editID = itemView.findViewById(R.id.edit_id);
            editDate = itemView.findViewById(R.id.edit_date);
            editTime = itemView.findViewById(R.id.edit_time);
        }
    }
}
