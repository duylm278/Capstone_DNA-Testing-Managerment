package capstone.summer.project.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;


import capstone.summer.R;
import capstone.summer.project.adapter.ViewPagerAdapter;
import capstone.summer.project.fragment.ExtractHistoryFragment;
import capstone.summer.project.fragment.ExtractInformationFragment;
public class ExtractStepActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int selectedTab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extract_step);

//        LitigantService.getAllLitigant(dialogViewHistory(), new VolleyCallback() {
//            @Override
//            public void onSuccess(Object data) {
//                List<Litigant> litigantList = (List<Litigant>)data ;
//                RecyclerView listLitigants = dialogLitigant.findViewById(R.id.listLitigants);
//                LitigantRecycleAdapter listLitigant = new LitigantRecycleAdapter(getActivity(),litigantList);
//                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
//                listLitigants.setLayoutManager(layoutManager);
//                listLitigants.setAdapter(listLitigant);
//
//            }
//        });
        viewPager = findViewById(R.id.userViewPaper);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(selectedTab).select();
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new ExtractInformationFragment(), "Thông Tin");
        adapter.addFrag(new ExtractHistoryFragment(), "Lịch Sử");

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
