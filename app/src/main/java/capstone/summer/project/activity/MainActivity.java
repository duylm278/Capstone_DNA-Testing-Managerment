package capstone.summer.project.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import capstone.summer.R;
import capstone.summer.project.adapter.ViewPagerAdapter;
import capstone.summer.project.fragment.AnalysisFragment;
import capstone.summer.project.fragment.HomeFragment;
import capstone.summer.project.fragment.ManagementFragment;
import capstone.summer.project.fragment.ProfileFragment;
import cn.pedant.SweetAlert.SweetAlertDialog;


public class MainActivity extends AppCompatActivity{



    private MenuItem prevMenuItem;
    private BottomNavigationView bottomNavigation;
    private ViewPager viewPager;

    private DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();

        setBottomNavigationConfig();
        setupViewPager(viewPager);
    }


    private void addControls() {
        bottomNavigation = findViewById(R.id.bottom_navigation);
        viewPager = findViewById(R.id.viewpager);
    }


    private void addEvents() {

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                setBottomNavigationSelectedItem(item);
                switch (item.getItemId()) {
//                    case R.id.nav_home:
//                        viewPager.setCurrentItem(0);
//                        break;
//                    case R.id.nav_analysis:
//                        viewPager.setCurrentItem(0);
//                        break;
                    case R.id.nav_testing:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.nav_user:
                        viewPager.setCurrentItem(1);
                        break;
                }
                return true;
            }
        });


        //nhận event khi đổi page
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0){
                    drawer = findViewById(R.id.drawer_layout);
                    if (drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.closeDrawer(GravityCompat.START);
                    }
                }

                if (prevMenuItem != null)
                    prevMenuItem.setChecked(false);
                else
                    bottomNavigation.getMenu().getItem(0).setChecked(false);

                MenuItem item = bottomNavigation.getMenu().getItem(position);
                setBottomNavigationSelectedItem(item);
                item.setChecked(true);
                prevMenuItem = item;
                item.setCheckable(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }


    private void setBottomNavigationSelectedItem(@NonNull MenuItem item) {

        //set icon cơ bản cho mỗi fragment
        Menu menu = bottomNavigation.getMenu();
//        menu.getItem(0).setIcon(R.mipmap.ic_nav_home);
//        menu.getItem(0).setIcon(R.mipmap.ic_nav_home);
        menu.getItem(0).setIcon(R.mipmap.ic_nav_checklist);
        menu.getItem(1).setIcon(R.mipmap.ic_nav_user);

        //chuyển fragment là đổi icon
        switch (item.getItemId()) {
//            case R.id.nav_home:
//                item.setIcon(R.mipmap.ic_nav_home_selected);
//                break;
//            case R.id.nav_analysis:
//                item.setIcon(R.mipmap.ic_nav_home_selected);
//                break;
            case R.id.nav_testing:
                item.setIcon(R.mipmap.ic_nav_checklist_selected);
                break;
            case R.id.nav_user:
                item.setIcon(R.mipmap.ic_nav_user_selected);
                break;
        }

    }

    private void setBottomNavigationConfig() {
        bottomNavigation.setItemIconTintList(null);
        bottomNavigation.getMenu().getItem(0).setIcon(R.mipmap.ic_nav_checklist_selected);
    }

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        //khởi tạo fragment
//        Fragment homeFragment = new HomeFragment();
//        Fragment analysisFragment = new AnalysisFragment();
        Fragment managementFragment = new ManagementFragment();
        Fragment profileFragment = new ProfileFragment();

        //add fragment vào viewpaper
//        adapter.addFrag(homeFragment, "Trang Chủ");
//        adapter.addFrag(analysisFragment, "Báo Cáo");
        adapter.addFrag(managementFragment, "Quản Lý");
        adapter.addFrag(profileFragment, "Người Dùng");

        viewPager.setAdapter(adapter);
    }


//    @Override
//    public void onResume() {
//        super.onResume();
//        overridePendingTransition(R.anim.zoom_in,R.anim.zoom_out);
//    }
@Override
public void onBackPressed() {

    final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
    pDialog.getProgressHelper().setBarColor(Color.parseColor("#56baed"));
    pDialog.setTitleText("Thoát ...");
    pDialog.setCanceledOnTouchOutside(false);
    pDialog.setConfirmText("Ok").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.showCancelButton(false)

                                .setCancelClickListener(null)
                                .setConfirmClickListener(null)
                                .changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
                        sDialog.getButton(SweetAlertDialog.BUTTON_CONFIRM).setVisibility(View.GONE);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                pDialog.dismissWithAnimation();
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
                            }
                        }, 1000);
//                        sDialog.dismissWithAnimation();
                    }
                });
    pDialog.setCancelText("Cancel").setCancelClickListener( new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {

                        sDialog.dismissWithAnimation();
                    }
                });
    pDialog.show();

}

}
