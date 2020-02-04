package capstone.summer.project.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import capstone.summer.R;
import java.util.List;

import capstone.summer.project.activity.ElectrophoresisStepActivity;
import capstone.summer.project.activity.ExtractStepActivity;
import capstone.summer.project.activity.PCRStepActivity;
import capstone.summer.project.activity.PurifyStepActivity;
import capstone.summer.project.activity.ResultActivity;
import capstone.summer.project.model.Process;

public class ProcessRecycleAdapter extends RecyclerView.Adapter<ProcessRecycleAdapter.ViewHolder> {

    private List<Process> mListProcess;
    private Activity activity;
    public ProcessRecycleAdapter(List<Process> listProcesses, Activity activity){
        this.mListProcess = listProcesses;
        this.activity=activity;
    }
    public OnItemClickListener mListener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.process_item,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        try {
            holder.mTxtProcessName.setText(mListProcess.get(position).getProcessName());
            final String sttIndex = mListProcess.get(position).getProcessStatus();
            if(sttIndex.equals("0")){
                if(position == 0){
                    holder.mImgLine.setVisibility(View.GONE);
                }
                holder.mImgLine.setImageResource(R.mipmap.ic_unfinish_line);
                holder.mImgCircle.setImageResource(R.mipmap.ic_unfinish_circle);
            }else if(sttIndex.equals("2")){
                if(position == 0){
                    holder.mImgLine.setVisibility(View.GONE);
                }
                holder.mImgLine.setImageResource(R.mipmap.ic_finish_line);
                holder.mImgCircle.setImageResource(R.mipmap.ic_on_going_circle);
            }else if(sttIndex.equals("1")){
                if(position == 0){
                    holder.mImgLine.setVisibility(View.GONE);
                }
                holder.mImgLine.setImageResource(R.mipmap.ic_finish_line);
                holder.mImgCircle.setImageResource(R.mipmap.ic_finish_circle);
            }
            holder.mTxtProcessName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = null;
                    if(!sttIndex.equals("0")) {
                        switch (position) {
                            case 0:
                                intent = new Intent(activity, ExtractStepActivity.class);
                                break;
                            case 1:
                                intent = new Intent(activity, PCRStepActivity.class);
                                break;
                            case 2:
                                intent = new Intent(activity, ElectrophoresisStepActivity.class);
                                break;
                            case 3:
                                intent = new Intent(activity, PurifyStepActivity.class);
                                break;
                            case 4:
                                intent = new Intent(activity, ResultActivity.class);
                                break;

                        }
                        intent.putExtra("SampleID",mListProcess.get(position).getSampleID());
                        activity.startActivity(intent);
                        activity.overridePendingTransition(R.anim.left_to_right, R.anim.left_out);

                    }
                }
            });
        }catch (Exception e){
            Toast.makeText(activity, "Lỗi hệ thống hoặc mạng !!!", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public int getItemCount() {
        int count = ( mListProcess != null)? mListProcess.size() : 0;
        return count;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView mImgLine,mImgCircle;
        private TextView mTxtProcessName,mTxtProcessStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImgLine = itemView.findViewById(R.id.img_line);
            mImgCircle = itemView.findViewById(R.id.img_circle);
            mTxtProcessName = itemView.findViewById(R.id.txt_process_name);
//            mTxtProcessStatus = itemView.findViewById(R.id.txt_process_status);
        }
    }

    public interface OnItemClickListener{
        void onItemClickListener(int postion);
    }

    public void setOnItemProcessClickListener(OnItemClickListener mOnItemClickListener){
        this.mListener = mOnItemClickListener;
    }

}
