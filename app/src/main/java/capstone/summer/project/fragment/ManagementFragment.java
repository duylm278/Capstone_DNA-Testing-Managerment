package capstone.summer.project.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import capstone.summer.R;
import capstone.summer.project.Service.CaseService;
import capstone.summer.project.Service.KitService;
import capstone.summer.project.Service.LitigantService;
import capstone.summer.project.Service.OrganizationService;
import capstone.summer.project.Service.SampleTypeService;
import capstone.summer.project.Service.SystemService;
import capstone.summer.project.Service.VolleyCallback;
import capstone.summer.project.adapter.KitRecycleAdapter;
import capstone.summer.project.adapter.LitigantRecycleAdapter;
import capstone.summer.project.adapter.ManagementRecycleAdapter;
import capstone.summer.project.adapter.NotifyRecycleAdapter;
import capstone.summer.project.adapter.OrganitionRecycleAdapter;
import capstone.summer.project.adapter.SampleTypeRecycleAdapter;
import capstone.summer.project.adapter.SystemRecycleAdapter;
import capstone.summer.project.model.Case;
import capstone.summer.project.model.Kit;
import capstone.summer.project.model.Litigant;
import capstone.summer.project.model.Notify;
import capstone.summer.project.model.Organization;
import capstone.summer.project.model.SampleType;
import capstone.summer.project.model.System;
import capstone.summer.project.utils.ConstantManager;

public class ManagementFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView listManagement;
    private RecyclerView listNotify;
    private Menu menu;
    private List<Case> listCase = new ArrayList<>();
    private List<Notify> notifys = new ArrayList<>();
    private ManagementRecycleAdapter adapter;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private SearchView searchView;
    private ProgressBar progressBar;
    private LinearLayout linearLayoutProcess;
    private Dialog dialogLitigant;
    private Dialog dialogKit;
    private Dialog dialogSystem;
    private Dialog dialogOrganition;
    private Dialog dialogNotify;
    private Dialog dialogSampleType;
    private ObjectMapper om = new ObjectMapper();
    private SwipeRefreshLayout swipeRefreshLayout;
    private int count = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.manager_drawer, container, false);
        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        innitView();
        notification();
        swipeRefreshLayout.setOnRefreshListener(this);
        dialogLitigant = new Dialog(getActivity(), R.style.MaterialDialogSheet);
        dialogLitigant.setContentView(R.layout.view_litigant_layout);
        dialogLitigant.findViewById(R.id.allout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLitigant.dismiss();
            }
        });
        dialogLitigant.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        LitigantService.getAllLitigant(getActivity(), new VolleyCallback() {
            @Override
            public void onSuccess(Object data) {
                List<Litigant> litigantList = (List<Litigant>) data;
                RecyclerView listLitigants = dialogLitigant.findViewById(R.id.listLitigants);
                LitigantRecycleAdapter listLitigant = new LitigantRecycleAdapter(getActivity(), litigantList);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                listLitigants.setLayoutManager(layoutManager);
                listLitigants.setAdapter(listLitigant);

            }

            @Override
            public void onFail(Object data) {
                Toast.makeText(getActivity(), "Lỗi mạng load đương sự!!!", Toast.LENGTH_SHORT).show();
            }
        });


        dialogKit = new Dialog(getActivity(), R.style.MaterialDialogSheet);
        dialogKit.setContentView(R.layout.view_kit_layout);
        dialogKit.findViewById(R.id.allout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogKit.dismiss();
            }
        });
        dialogKit.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        KitService.getAllKit(getActivity(), new VolleyCallback() {
            @Override
            public void onSuccess(Object data) {
                List<Kit> kits = (List<Kit>) data;
                RecyclerView listKits = dialogKit.findViewById(R.id.listKits);
                KitRecycleAdapter kitRecycleAdapter = new KitRecycleAdapter(getActivity(), kits);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                listKits.setLayoutManager(layoutManager);
                listKits.setAdapter(kitRecycleAdapter);
            }

            @Override
            public void onFail(Object data) {
                Toast.makeText(getActivity(), "Lỗi mạng load kit!!!", Toast.LENGTH_SHORT).show();
            }
        });


        dialogOrganition = new Dialog(getActivity(), R.style.MaterialDialogSheet);
        dialogOrganition.setContentView(R.layout.view_organization_layout);
        dialogOrganition.findViewById(R.id.allout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogOrganition.dismiss();
            }
        });
        dialogOrganition.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        OrganizationService.getAllOrganization(getActivity(), new VolleyCallback() {
            @Override
            public void onSuccess(Object data) {
                List<Organization> organizations = (List<Organization>) data;
                RecyclerView listKits = dialogOrganition.findViewById(R.id.listOrganizations);
                OrganitionRecycleAdapter kitRecycleAdapter = new OrganitionRecycleAdapter(getActivity(), organizations);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                listKits.setLayoutManager(layoutManager);
                listKits.setAdapter(kitRecycleAdapter);
            }

            @Override
            public void onFail(Object data) {
                Toast.makeText(getActivity(), "Lỗi mạng load cơ quan!!!", Toast.LENGTH_SHORT).show();
            }
        });


        dialogSystem = new Dialog(getActivity(), R.style.MaterialDialogSheet);
        dialogSystem.setContentView(R.layout.view_system_layout);
        dialogSystem.findViewById(R.id.allout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSystem.dismiss();
            }
        });
        dialogSystem.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        SystemService.getAllSystem(getActivity(), new VolleyCallback() {
            @Override
            public void onSuccess(Object data) {
                List<System> systems = (List<System>) data;
                RecyclerView listSystems = dialogSystem.findViewById(R.id.listSystems);
                SystemRecycleAdapter sysRecycleAdapter = new SystemRecycleAdapter(getActivity(),systems);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                listSystems.setLayoutManager(layoutManager);
                listSystems.setAdapter(sysRecycleAdapter);
            }

            @Override
            public void onFail(Object data) {
                Toast.makeText(getActivity(), "Lỗi mạng load hệ thống máy!!!", Toast.LENGTH_SHORT).show();
            }
        });

        dialogSampleType = new Dialog(getActivity(), R.style.MaterialDialogSheet);
        dialogSampleType.setContentView(R.layout.view_sample_type_layout);
        dialogSampleType.findViewById(R.id.allout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSampleType.dismiss();
            }
        });
        dialogSampleType.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        SampleTypeService.getAllSampleType(getActivity(), new VolleyCallback() {
            @Override
            public void onSuccess(Object data) {
                List<SampleType> sampleTypes = (List<SampleType>) data;
                RecyclerView listSampleType = dialogSampleType.findViewById(R.id.listSampleTypes);
                SampleTypeRecycleAdapter sysRecycleAdapter = new SampleTypeRecycleAdapter(getActivity(),sampleTypes);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                listSampleType.setLayoutManager(layoutManager);
                listSampleType.setAdapter(sysRecycleAdapter);
            }

            @Override
            public void onFail(Object data) {
                Toast.makeText(getActivity(), "Lỗi mạng load loại mẫu!!!", Toast.LENGTH_SHORT).show();
            }
        });

        getCase();
    }

    private void getCase() {
        CaseService.getAllCase(getActivity(), new VolleyCallback() {
            @Override
            public void onSuccess(Object data) {
                linearLayoutProcess.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                listManagement.setVisibility(View.VISIBLE);
                listCase = (List<Case>) data;
                adapter = new ManagementRecycleAdapter(getActivity(), listCase);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                listManagement.setLayoutManager(layoutManager);
                listManagement.setAdapter(adapter);
            }

            @Override
            public void onFail(Object data) {
                Toast.makeText(getActivity(), "Lỗi mạng load case!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void innitView() {
        swipeRefreshLayout = getActivity().findViewById(R.id.swipeRefresh);
        listManagement = getActivity().findViewById(R.id.list_management);
        drawer = getActivity().findViewById(R.id.drawer_layout);
        toolbar = getActivity().findViewById(R.id.toolbar);
        navigationView = getActivity().findViewById(R.id.nav_view);
        progressBar = getActivity().findViewById(R.id.progress_bar);
        linearLayoutProcess = getActivity().findViewById(R.id.progress_bar_layout);
        navigationView.setNavigationItemSelectedListener(this);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle("Quản Lý CA");
        toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
////        toggle.
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.viewLitigants) {
            dialogLitigant.show();
        }
        if (id == R.id.viewKits) {
            dialogKit.show();
        }
        if (id == R.id.viewOrganizations) {
            dialogOrganition.show();
        }
        if (id == R.id.viewSystems) {
            dialogSystem.show();
        }
        if (id == R.id.viewSampleTypes) {
            dialogSampleType.show();
        }
        DrawerLayout drawer = getActivity().findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    private void notification() {
        dialogNotify = new Dialog(getActivity(), R.style.AnimationNotify);
        dialogNotify.setContentView(R.layout.view_notify_layout);
        listNotify = dialogNotify.findViewById(R.id.listNotify);
        dialogNotify.findViewById(R.id.allout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogNotify.dismiss();
            }
        });
        dialogNotify.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


        SharedPreferences sharedPreferences = getActivity().getApplicationContext().getSharedPreferences(ConstantManager.USER_MANAGER, Context.MODE_PRIVATE);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
                .build();
        db.setFirestoreSettings(settings);
//        Toast.makeText(getActivity(),sharedPreferences.getString(ConstantManager.USER_ID, "")+"", Toast.LENGTH_SHORT).show();
        db.collection("notifications")
                .whereEqualTo("ReceiverId", Integer.parseInt(sharedPreferences.getString(ConstantManager.USER_ID, "")))
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
//                        if(value.getDocumentChanges().size()>0){

//                        }

                        notifys.clear();

                        for (QueryDocumentSnapshot doc : value) {
//                            menu.findItem(R.id.notification).setIcon(R.drawable.ic_bell_selected);
                            Map<String, Object> data = doc.getData();
                            Notify notify = new Notify();
                            notify.setContent((String) data.get("Content"));
                            notify.setReaded((Boolean) data.get("Readed"));
                            notify.setReceiverId(Integer.parseInt(data.get("ReceiverId").toString()));
                            notify.setSenderId(Integer.parseInt(data.get("SenderId").toString()));
                            notify.setSenderName((String) data.get("SenderName"));
                            Timestamp time = (Timestamp) data.get("createAt");
                            Long timeLong =time.getSeconds();
                            LocalDateTime localDateTime= LocalDateTime.ofInstant(Instant.ofEpochMilli(timeLong), TimeZone
                                    .getDefault().toZoneId());
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a");
                            notify.setCreateAt(localDateTime.format(formatter));
                            notifys.add(notify);
//                            count++;
//                            Toast.makeText(getActivity(), count+"", Toast.LENGTH_SHORT).show();

                        }
                        NotifyRecycleAdapter adapter = new NotifyRecycleAdapter(getActivity(), notifys);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                        listNotify.setLayoutManager(layoutManager);
                        listNotify.setAdapter(adapter);

//                        adapter.notifyDataSetChanged();
                    }
                });


    }


    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        this.menu = menu;
        menu.findItem(R.id.notification).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                menu.findItem(R.id.notification).setIcon(R.drawable.ic_bell);
                dialogNotify.show();
                return false;
            }
        });
        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setIconifiedByDefault(true);
        SearchView.SearchAutoComplete theTextArea = searchView.findViewById(R.id.search_src_text);
       ImageView closeIcon = searchView.findViewById(R.id.search_close_btn);
        closeIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_cancel));
        theTextArea.setTextColor(getResources().getColor(R.color.colorBlack));
        searchView.setQueryHint(Html.fromHtml("<font color = #999999>Tìm mã CA ...</font>"));
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listCase.clear();
                getCase();
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1000);

    }

}
