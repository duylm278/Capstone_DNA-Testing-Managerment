package capstone.summer.project.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import capstone.summer.R;
import capstone.summer.project.model.Kit;
import capstone.summer.project.model.Notify;

public class NotifyRecycleAdapter extends RecyclerView.Adapter<NotifyRecycleAdapter.NotifyViewHolder>{
    private List<Notify> notifies;
    private Activity activity;

    public NotifyRecycleAdapter(Activity activity, List<Notify> notifies) {
        this.notifies = notifies;
        this.activity = activity;
    }

    @NonNull
    @Override
    public NotifyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notify, parent, false);
        return new NotifyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull NotifyViewHolder holder, int position) {
        final Notify notify = notifies.get(position);
        holder.txtContent.setText(notify.getContent());
        holder.txtSenderName.setText(notify.getSenderName()+"");
        holder.time.setText(notify.getCreateAt()+"");

        if (!notify.isReaded()){
            holder.outLine.setBackgroundColor(activity.getResources().getColor(R.color.colorGrayLight));
        }
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Toast.makeText(activity, notify.isReaded()+"", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return notifies == null ? 0 : notifies.size();
    }

    class NotifyViewHolder extends RecyclerView.ViewHolder  {

        private TextView txtContent;
        private TextView txtSenderName;
        private TextView time;
        private LinearLayout outLine;

        public NotifyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtContent = itemView.findViewById(R.id.textContent);
            txtSenderName = itemView.findViewById(R.id.textSenderName);
            time = itemView.findViewById(R.id.time);
            outLine = itemView.findViewById(R.id.outLine);
        }
    }
}
