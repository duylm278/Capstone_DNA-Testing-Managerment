package capstone.summer.project.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.Handler;

import java.util.ArrayList;

import capstone.summer.R;
import capstone.summer.project.adapter.PaginationScrollListener;
import capstone.summer.project.adapter.PostRecyclerAdapter;
import capstone.summer.project.model.PostItem;

public class TestPagination extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "MainActivity";
    RecyclerView mRecyclerView;
    SwipeRefreshLayout swipeRefresh;
    private PostRecyclerAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    public static final int PAGE_START = 1;
    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private int totalPage = 10;
    private boolean isLoading = false;
    int itemCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_pagination);
        swipeRefresh = findViewById(R.id.swipeRefresh);
        mRecyclerView = findViewById(R.id.mRecyclerView);
        swipeRefresh.setOnRefreshListener(this);

        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new PostRecyclerAdapter(new ArrayList<PostItem>());
        mRecyclerView.setAdapter(mAdapter);
        preparedListItem();
        /**
         * add scroll listener while user reach in bottom load more will call
         */
        mRecyclerView.addOnScrollListener(new PaginationScrollListener(mLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage++;
                preparedListItem();

            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
    }

    private void preparedListItem() {
        final ArrayList<PostItem> items = new ArrayList<>();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    itemCount++;
                    PostItem postItem = new PostItem();
                    postItem.setTitle("Fake Android Apps With Over 50,000 " + itemCount);
                    postItem.setDescription("Fake Android Apps With Over 50,000 Installations Found on Google Play, Quick Heal Claims");
                    items.add(postItem);

                }
                if (currentPage != PAGE_START) mAdapter.removeLoading();
                mAdapter.addAll(items);
                swipeRefresh.setRefreshing(false);
                if (currentPage < totalPage) mAdapter.addLoading();
                else isLastPage = true;
                isLoading = false;

            }
        }, 1500);
    }

    @Override
    public void onRefresh() {
        itemCount = 0;
        currentPage = PAGE_START;
        isLastPage = false;
        mAdapter.clear();
        preparedListItem();


    }
}
