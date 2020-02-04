package capstone.summer.project.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import capstone.summer.R;
import capstone.summer.project.adapter.ViewPagerAdapter;
import capstone.summer.project.fragment.PurifyHistoryFragment;
import capstone.summer.project.fragment.PurifyInformationFragment;

public class PurifyStepActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int selectedTab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purify_step);
        viewPager = findViewById(R.id.userViewPaper);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(selectedTab).select();

    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new PurifyInformationFragment(), "Thông Tin");
        adapter.addFrag(new PurifyHistoryFragment(), "Lịch Sử");
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
