package capstone.summer.project.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import capstone.summer.R;
import capstone.summer.project.Service.SampleService;
import capstone.summer.project.Service.VolleyCallback;
import capstone.summer.project.adapter.SampleRecycleAdapter;
import capstone.summer.project.model.Sample;

public class SampleActivity extends AppCompatActivity {
    private RecyclerView listSample;
    private List<Sample> samples = new ArrayList<>();
    private SampleRecycleAdapter adapter;
    private TextView title;
    private TextView codeCA;
    private TextView qdtcNumber;
    private TextView receiveDate;
    private TextView signatureDate;
    private LinearLayout processBar;
    private LinearLayout notData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        innitView();
        getSample();

    }

    private void getSample(){
        Intent intent = this.getIntent();
        String id = intent.getStringExtra("CaseID");
        String codeCAS = intent.getStringExtra("CodeCA");
        String qdtcNumberS= intent.getStringExtra("QDTCNumber");
        String receiveDateS = intent.getStringExtra("QDTCReceiveDate");
        String signatureDateS = intent.getStringExtra("QDTCSignDate");

        LocalDateTime receive = LocalDateTime.parse(receiveDateS);
        final LocalDateTime signature = LocalDateTime.parse(signatureDateS);
        codeCA.setText(codeCAS);
        qdtcNumber.setText(qdtcNumberS);
        receiveDate.setText(receive.toLocalDate()+"");
        signatureDate.setText(signature.toLocalDate()+"");
        SampleService.getSampleByCaseID(this, id, new VolleyCallback() {
            @Override
            public void onSuccess(Object data) {
                processBar.setVisibility(View.GONE);
                samples = (List<Sample>) data;
                if (samples.size()==0){
                    notData.setVisibility(View.VISIBLE);
                }
                adapter = new SampleRecycleAdapter(SampleActivity.this,samples);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getParent(), RecyclerView.VERTICAL, false);
                listSample.setLayoutManager(layoutManager);
                listSample.setAdapter(adapter);
            }

            @Override
            public void onFail(Object data) {

            }
        });
    }

    private void innitView(){
        listSample=findViewById(R.id.list_sample);
        codeCA = findViewById(R.id.codeCA);
        qdtcNumber=findViewById(R.id.qdtcNumber);
        receiveDate=findViewById(R.id.receiveDate);
        signatureDate=findViewById(R.id.signatureDate);
        processBar=findViewById(R.id.progress_bar_layout);
        notData=findViewById(R.id.not_data);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.right_to_left,R.anim.right_out);
    }
}
