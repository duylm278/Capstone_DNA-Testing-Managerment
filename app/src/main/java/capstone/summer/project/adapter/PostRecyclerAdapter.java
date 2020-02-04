package capstone.summer.project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import capstone.summer.R;
import capstone.summer.project.model.PostItem;

public class PostRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    private boolean isLoaderVisible = false;

    private List<PostItem> mPostItems;

    public PostRecyclerAdapter(List<PostItem> postItems) {
        this.mPostItems = postItems;
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false));
            case VIEW_TYPE_LOADING:
                return new FooterHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (isLoaderVisible) {
            return position == mPostItems.size() - 1 ? VIEW_TYPE_LOADING : VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_NORMAL;
        }
    }

    @Override
    public int getItemCount() {
        return mPostItems == null ? 0 : mPostItems.size();
    }

    public void add(PostItem response) {
        mPostItems.add(response);
        notifyItemInserted(mPostItems.size() - 1);
    }

    public void addAll(List<PostItem> postItems) {
        for (PostItem response : postItems) {
            add(response);
        }
    }


    private void remove(PostItem postItems) {
        int position = mPostItems.indexOf(postItems);
        if (position > -1) {
            mPostItems.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void addLoading() {
        isLoaderVisible = true;
        add(new PostItem());
    }

    public void removeLoading() {
        isLoaderVisible = false;
        int position = mPostItems.size() - 1;
        PostItem item = getItem(position);
        if (item != null) {
            mPostItems.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    PostItem getItem(int position) {
        return mPostItems.get(position);
    }


    public class ViewHolder extends BaseViewHolder {
        TextView textViewTitle;
        TextView textViewDescription;


        ViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDescription=itemView.findViewById(R.id.textViewDescription);
//            ButterKnife.bind(this, itemView);
        }

        protected void clear() {

        }

        public void onBind(int position) {
            super.onBind(position);
            PostItem item = mPostItems.get(position);

            textViewTitle.setText(item.getTitle());
            textViewDescription.setText(item.getDescription());
        }
    }

    public class FooterHolder extends BaseViewHolder {

        ProgressBar mProgressBar;


        FooterHolder(View itemView) {
            super(itemView);
//            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void clear() {

        }

    }

}