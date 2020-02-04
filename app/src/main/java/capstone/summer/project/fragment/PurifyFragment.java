package capstone.summer.project.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import capstone.summer.R;
import capstone.summer.project.adapter.ViewPagerAdapter;

public class PurifyFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int selectedTab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_purify, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
//        viewPager = getActivity().findViewById(R.id.purifyViewPaper);
//        setupViewPager(viewPager);
//
//        tabLayout = getActivity().findViewById(R.id.tabLayout);
//        tabLayout.setupWithViewPager(viewPager);
//
//        tabLayout.getTabAt(selectedTab).select();
    }
//    private void setupViewPager(ViewPager viewPager) {
//        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
//        adapter.addFrag(new PurifyInformationFragment(), "Thông Tin");
//        adapter.addFrag(new PurifyHistoryFragment(), "Lịch Sử");
//        viewPager.setAdapter(adapter);
//    }

//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        selectedTab= tabLayout.getSelectedTabPosition();
//    }
}
