package capstone.summer.project.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;

import capstone.summer.R;
import capstone.summer.project.adapter.ViewPagerAdapter;
import capstone.summer.project.fragment.ResultHistoryFragment;
import capstone.summer.project.fragment.ResultInformationFragment;

public class ResultActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int selectedTab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        viewPager = findViewById(R.id.userViewPaper);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(selectedTab).select();

    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new ResultInformationFragment(), "Thông Tin");
        adapter.addFrag(new ResultHistoryFragment(), "Lịch Sử");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        selectedTab= tabLayout.getSelectedTabPosition();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.right_to_left, R.anim.right_out);
    }
}
