//package capstone.summer.project.activity;
//
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.drawerlayout.widget.DrawerLayout;
//import androidx.viewpager.widget.ViewPager;
//
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//
//import capstone.summer.R;
//import capstone.summer.project.adapter.ViewPagerAdapter;
//import capstone.summer.project.fragment.ElectrophoresisFragment;
//import capstone.summer.project.fragment.ElectrophoresisInformationFragment;
//import capstone.summer.project.fragment.ExtractFragment;
//import capstone.summer.project.fragment.ExtractInformationFragment;
//import capstone.summer.project.fragment.PCRFragment;
//import capstone.summer.project.fragment.PCRInformationFragment;
//import capstone.summer.project.fragment.PurifyFragment;
//import capstone.summer.project.fragment.PurifyInformationFragment;
//import capstone.summer.project.fragment.ResultFragment;
//import capstone.summer.project.fragment.ResultInformationFragment;
//public class ProcessActivity extends AppCompatActivity {
//
//    private MenuItem prevMenuItemProcess;
//    private BottomNavigationView bottomNavigationProcess;
//    private ViewPager viewPagerProcess;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_process);
//        addControls();
//        addEvents();
//
//        setBottomNavigationConfig();
//        setupViewPager(viewPagerProcess);
//    }
//
//
//    private void addControls() {
//        bottomNavigationProcess = findViewById(R.id.bottom_navigation_process);
//        viewPagerProcess = findViewById(R.id.viewpager_process);
//    }
//
//
//    private void addEvents() {
//
//        bottomNavigationProcess.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//                setBottomNavigationSelectedItem(item);
//                switch (item.getItemId()) {
//                    case R.id.extract:
//                        viewPagerProcess.setCurrentItem(0);
//                        break;
//                    case R.id.pcr:
//                        viewPagerProcess.setCurrentItem(1);
//                        break;
//                    case R.id.electrophoresis:
//                        viewPagerProcess.setCurrentItem(2);
//                        break;
//                    case R.id.purify:
//                        viewPagerProcess.setCurrentItem(3);
//                        break;
//                    case R.id.result:
//                        viewPagerProcess.setCurrentItem(4);
//                        break;
//                }
//                return true;
//            }
//        });
//
//
//        //nhận event khi đổi page
//        viewPagerProcess.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//                if (prevMenuItemProcess != null)
//                    prevMenuItemProcess.setChecked(false);
//                else
//                    bottomNavigationProcess.getMenu().getItem(0).setChecked(false);
//
//                MenuItem item = bottomNavigationProcess.getMenu().getItem(position);
//                setBottomNavigationSelectedItem(item);
//                item.setChecked(true);
//                prevMenuItemProcess = item;
//                item.setCheckable(true);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//            }
//        });
//    }
//
//
//    private void setBottomNavigationSelectedItem(@NonNull MenuItem item) {
//
//        //set icon cơ bản cho mỗi fragment
//        Menu menu = bottomNavigationProcess.getMenu();
//        menu.getItem(0).setIcon(R.mipmap.ic_extract);
//        menu.getItem(1).setIcon(R.mipmap.ic_pcr);
//        menu.getItem(2).setIcon(R.mipmap.ic_electrophoresis);
//        menu.getItem(3).setIcon(R.mipmap.ic_purify);
//        menu.getItem(4).setIcon(R.mipmap.ic_result);
//
//        //chuyển fragment là đổi icon
//        switch (item.getItemId()) {
//            case R.id.extract:
//                item.setIcon(R.mipmap.ic_extract_selected);
//                break;
//            case R.id.pcr:
//                item.setIcon(R.mipmap.ic_pcr_selected);
//                break;
//            case R.id.electrophoresis:
//                item.setIcon(R.mipmap.ic_electrophoresis_selected);
//                break;
//            case R.id.purify:
//                item.setIcon(R.mipmap.ic_purify_selected);
//                break;
//            case R.id.result:
//                item.setIcon(R.mipmap.ic_result_selected);
//                break;
//        }
//
//    }
//
//    private void setBottomNavigationConfig() {
//        bottomNavigationProcess.setItemIconTintList(null);
//        bottomNavigationProcess.getMenu().getItem(0).setIcon(R.mipmap.ic_extract_selected);
//    }
//
//    private void setupViewPager(ViewPager viewPager) {
//
//        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
//        adapter.addFrag(new ExtractFragment(), "Ly Trích");
//        adapter.addFrag(new PCRFragment(), "PCR");
//        adapter.addFrag(new ElectrophoresisFragment(), "Điện Di");
//        adapter.addFrag(new PurifyFragment(), "T. Sạch");
//        adapter.addFrag(new ResultFragment(), "Kết Quả");
//
//        viewPager.setAdapter(adapter);
//    }
//
//
////    @Override
////    public void onResume() {
////        super.onResume();
////        overridePendingTransition(R.anim.zoom_in,R.anim.zoom_out);
////    }
//
//
////        initialView();
//////        initialData();
//////        initialRepository();
////    }
////
////    private void initialRepository() {
////        mRepository = new CustomerRepositoryImpl();
////    }
////
////    private void createCustomer(Customer cus) {
////        mRepository.createCustomer(this, new CallBackData<Boolean>() {
////            @Override
////            public void onSuccess(Boolean aBoolean) {
////                if (aBoolean == true) {
////
////                } else {
////
////                }
////            }
////
////            @Override
////            public void onFail(String message) {
////
////            }
////        }, cus);
////    }
////
////    private void initialView() {
////        mRecyclerViewProcess = findViewById(R.id.recyclerview_Process);
////        mRecyclerViewProcess.setHasFixedSize(true);
////        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
////        mRecyclerViewProcess.setLayoutManager(mLayoutManager);
////    }
////
////    private void initialData() {
////        final List<Process> mList = new ArrayList<>();
////        Intent intent = getIntent();
////        final Map<String,Process> map = new HashMap<>();
////        final String sampleID = intent.getStringExtra("SampleID");
////        ExtractProcessService.getExtractProcessByID(this, sampleID, new VolleyCallback() {
////            @Override
////            public void onSuccess(Object data) {
////                ExtractProcess extractProcess = om.convertValue(data, ExtractProcess.class);
////                map.put("1",Process.builder().processName("Ly Trích").processStatus(extractProcess.getStatus()+"").sampleID(sampleID).build());
////
////            }
////
////            @Override
////            public void onFail(Object data) {
////                map.put("1",Process.builder().processName("Ly Trích").processStatus("0").build());
////            }
////        });
////        PCRProcessService.getPCRProcessByID(this, sampleID, new VolleyCallback() {
////            @Override
////            public void onSuccess(Object data) {
////                PCRProcess pcrProcess = om.convertValue(data, PCRProcess.class);
////                map.put("2",Process.builder().processName("PCR").processStatus(pcrProcess.getStatus()+"").sampleID(sampleID).build());
////            }
////
////            @Override
////            public void onFail(Object data) {
////                map.put("2",Process.builder().processName("PCR").processStatus("0").build());
////            }
////        });
////        ElectrophoresisProcessService.getElectrophoresisProcessByID(this, sampleID, new VolleyCallback() {
////            @Override
////            public void onSuccess(Object data) {
////                ElectrophoresisProcess electrophoresisProcess = om.convertValue(data, ElectrophoresisProcess.class);
////                map.put("3",Process.builder().processName("Điện Di").processStatus(electrophoresisProcess.getStatus()+"").sampleID(sampleID).build());
////            }
////
////            @Override
////            public void onFail(Object data) {
////                map.put("3",Process.builder().processName("Điện Di").processStatus("0").build());
////            }
////        });
////        PurifyProcessService.getPurifyProcessByID(this, sampleID, new VolleyCallback() {
////            @Override
////            public void onSuccess(Object data) {
////                PurifyProcess purifyProcess = om.convertValue(data, PurifyProcess.class);
////                map.put("4",Process.builder().processName("Thanh Lọc").processStatus(purifyProcess.getStatus()+"").sampleID(sampleID).build());
////            }
////
////            @Override
////            public void onFail(Object data) {
////                map.put("4",Process.builder().processName("Thanh Lọc").processStatus("0").build());
////            }
////        });
////        ResultService.ResultByID(this, sampleID, new VolleyCallback() {
////            @Override
////            public void onSuccess(Object data) {
////                Result result = om.convertValue(data, Result.class);
////                map.put("5",Process.builder().processName("Kết Quả").processStatus(result.getStatus()+"").sampleID(sampleID).build());
////            }
////
////            @Override
////            public void onFail(Object data) {
////                map.put("5",Process.builder().processName("Kết Quả").processStatus("0").build());
////            }
////        });
////
////        final Handler handler = new Handler();
////        handler.postDelayed(new Runnable() {
////            @Override
////            public void run() {
////                mList.add(map.get("1"));
////                mList.add(map.get("2"));
////                mList.add(map.get("3"));
////                mList.add(map.get("4"));
////                mList.add(map.get("5"));
////                if (map.get("1").getProcessName()==null){
////                    Toast.makeText(ProcessActivity.this, "Lỗi hệ thống hoặc mạng", Toast.LENGTH_SHORT).show();
////                    onBackPressed();
////                    return;
////                }
////                if (map.get("2").getProcessName()==null){
////                    Toast.makeText(ProcessActivity.this, "Lỗi hệ thống hoặc mạng", Toast.LENGTH_SHORT).show();
////                    onBackPressed();
////                    return;
////                }
////                if (map.get("3").getProcessName()==null){
////                    Toast.makeText(ProcessActivity.this, "Lỗi hệ thống hoặc mạng", Toast.LENGTH_SHORT).show();
////                    onBackPressed();
////                    return;
////                }
////                if (map.get("4").getProcessName()==null){
////                    Toast.makeText(ProcessActivity.this, "Lỗi hệ thống hoặc mạng", Toast.LENGTH_SHORT).show();
////                    onBackPressed();
////                    return;
////                }
////                if (map.get("5").getProcessName()==null){
////                    Toast.makeText(ProcessActivity.this, "Lỗi hệ thống hoặc mạng", Toast.LENGTH_SHORT).show();
////                    onBackPressed();
////                    return;
////                }
////                mAdapter = new ProcessRecycleAdapter(mList,ProcessActivity.this);
////                mRecyclerViewProcess.setAdapter(mAdapter);
////            }
////        }, 1000);
////
////
////    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        overridePendingTransition(R.anim.right_to_left, R.anim.right_out);
//    }
//
//}
package capstone.summer.project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import capstone.summer.R;
import capstone.summer.project.Service.ElectrophoresisProcessService;
import capstone.summer.project.Service.ExtractProcessService;
import capstone.summer.project.Service.PCRProcessService;
import capstone.summer.project.Service.PurifyProcessService;
import capstone.summer.project.Service.ResultService;
import capstone.summer.project.Service.VolleyCallback;
import capstone.summer.project.model.Customer;
import capstone.summer.project.adapter.ProcessRecycleAdapter;
import capstone.summer.project.model.ElectrophoresisProcess;
import capstone.summer.project.model.ExtractProcess;
import capstone.summer.project.model.PCRProcess;
import capstone.summer.project.model.Process;
import capstone.summer.project.model.PurifyProcess;
import capstone.summer.project.model.Result;
import capstone.summer.project.serviceRepository.CustomerRepository;
import capstone.summer.project.serviceRepository.CustomerRepositoryImpl;
import capstone.summer.project.utils.CallBackData;

public class ProcessActivity extends AppCompatActivity {

    private ProcessRecycleAdapter mAdapter;
    private RecyclerView mRecyclerViewProcess;
    private CustomerRepository mRepository;
    private ObjectMapper om = new ObjectMapper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process);
        initialView();
        initialData();
        initialRepository();
    }

    private void initialRepository() {
        mRepository = new CustomerRepositoryImpl();
    }

    private void createCustomer(Customer cus) {
        mRepository.createCustomer(this, new CallBackData<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                if (aBoolean == true) {

                } else {

                }
            }

            @Override
            public void onFail(String message) {

            }
        }, cus);
    }

    private void initialView() {
        mRecyclerViewProcess = findViewById(R.id.recyclerview_Process);
        mRecyclerViewProcess.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerViewProcess.setLayoutManager(mLayoutManager);
    }

    private void initialData() {
        final List<Process> mList = new ArrayList<>();
        Intent intent = getIntent();
        final Map<String,Process> map = new HashMap<>();
        final String sampleID = intent.getStringExtra("SampleID");
        ExtractProcessService.getExtractProcessByID(this, sampleID, new VolleyCallback() {
            @Override
            public void onSuccess(Object data) {
                ExtractProcess extractProcess = om.convertValue(data, ExtractProcess.class);
                map.put("1",Process.builder().processName("Ly Trích").processStatus(extractProcess.getStatus()+"").sampleID(sampleID).build());

            }

            @Override
            public void onFail(Object data) {
                map.put("1",Process.builder().processName("Ly Trích").processStatus("0").build());
            }
        });
        PCRProcessService.getPCRProcessByID(this, sampleID, new VolleyCallback() {
            @Override
            public void onSuccess(Object data) {
                PCRProcess pcrProcess = om.convertValue(data, PCRProcess.class);
                map.put("2",Process.builder().processName("PCR").processStatus(pcrProcess.getStatus()+"").sampleID(sampleID).build());
            }

            @Override
            public void onFail(Object data) {
                map.put("2",Process.builder().processName("PCR").processStatus("0").build());
            }
        });
        ElectrophoresisProcessService.getElectrophoresisProcessByID(this, sampleID, new VolleyCallback() {
            @Override
            public void onSuccess(Object data) {
                ElectrophoresisProcess electrophoresisProcess = om.convertValue(data, ElectrophoresisProcess.class);
                map.put("3",Process.builder().processName("Điện Di").processStatus(electrophoresisProcess.getStatus()+"").sampleID(sampleID).build());
            }

            @Override
            public void onFail(Object data) {
                map.put("3",Process.builder().processName("Điện Di").processStatus("0").build());
            }
        });
        PurifyProcessService.getPurifyProcessByID(this, sampleID, new VolleyCallback() {
            @Override
            public void onSuccess(Object data) {
                PurifyProcess purifyProcess = om.convertValue(data, PurifyProcess.class);
                map.put("4",Process.builder().processName("Tinh Sạch").processStatus(purifyProcess.getStatus()+"").sampleID(sampleID).build());
            }

            @Override
            public void onFail(Object data) {
                map.put("4",Process.builder().processName("Tinh Sạch").processStatus("0").build());
            }
        });
        ResultService.ResultByID(this, sampleID, new VolleyCallback() {
            @Override
            public void onSuccess(Object data) {
                Result result = om.convertValue(data, Result.class);
                map.put("5",Process.builder().processName("Kết Quả").processStatus(result.getStatus()+"").sampleID(sampleID).build());
            }

            @Override
            public void onFail(Object data) {
                map.put("5",Process.builder().processName("Kết Quả").processStatus("0").build());
            }
        });

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mList.add(map.get("1"));
                mList.add(map.get("2"));
                mList.add(map.get("3"));
                mList.add(map.get("4"));
                mList.add(map.get("5"));
                mAdapter = new ProcessRecycleAdapter(mList,ProcessActivity.this);
                mRecyclerViewProcess.setAdapter(mAdapter);
            }
        }, 1500);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.right_to_left, R.anim.right_out);
    }

}
