package capstone.summer.project.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.auth0.android.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.textfield.TextInputLayout;

import capstone.summer.R;
import capstone.summer.project.Service.UserService;
import capstone.summer.project.Service.VolleyCallback;
import capstone.summer.project.model.Login;
import capstone.summer.project.utils.ConstantManager;
import cn.pedant.SweetAlert.SweetAlertDialog;

@SuppressWarnings("deprecation")
public class LoginActivity extends AppCompatActivity {

    private Button mBtnLogin;

    private TextInputLayout username;
    private TextInputLayout password;
    private ObjectMapper om = new ObjectMapper();
    private LinearLayout linearLayout;
    private InputMethodManager imm;
    final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        linearLayout = findViewById(R.id.out);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), flags);
            }
        });
        imm = (InputMethodManager) getSystemService(LoginActivity.INPUT_METHOD_SERVICE);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
    }

    public void toLogin(View view) {

        final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#56baed"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(true);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();

        String inputUsername = username.getEditText().getText().toString();
        final String inputPassword = password.getEditText().getText().toString();

        if (inputUsername.trim().isEmpty()) {
            username.setError("Nhập sai");
            pDialog.dismiss();
            return;
        }
        username.setError("");

        if (inputPassword.trim().isEmpty()) {
            password.setError("Nhập sai");
            pDialog.dismiss();
            return;
        }
        password.setError("");

        UserService.Login(getApplicationContext(), inputUsername, inputPassword, new VolleyCallback() {
            @Override
            public void onSuccess(Object data) {
                Login token = (Login) data;
                JWT jwt = new JWT(token.getJwtString());
                SharedPreferences sharedPreferences = getApplication().getApplicationContext().getSharedPreferences(ConstantManager.USER_MANAGER, Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putString(ConstantManager.USER_ID, jwt.getClaim("userId").asString());
                edit.putString(ConstantManager.USER_PASSWORD, inputPassword);
                edit.apply();
                if (jwt.getClaim("role").asString().equalsIgnoreCase("Supervisor")) {
                    pDialog.setTitleText("Đăng nhập!")
                            .setContentText("Thành công!")
                            .setConfirmText("Tiếp...")
                            .showCancelButton(false)

                            .setCancelClickListener(null)
                            .setConfirmClickListener(null)
                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                    pDialog.getButton(SweetAlertDialog.BUTTON_CONFIRM).setVisibility(View.GONE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pDialog.dismissWithAnimation();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
                        }
                    }, 1000);
                }else{
                    pDialog.setTitleText("Đăng nhập!")
                            .setContentText("Sai quyền truy cập!")
                            .setConfirmText("Tiếp...")
                            .showCancelButton(false)

                            .setCancelClickListener(null)
                            .setConfirmClickListener(null)
                            .changeAlertType(SweetAlertDialog.WARNING_TYPE);
                    pDialog.getButton(SweetAlertDialog.BUTTON_CONFIRM).setBackgroundResource(R.drawable.button_login);

                }


            }

            @Override
            public void onFail(Object data) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pDialog.setTitleText("Lỗi !")
                                .setContentText("Đăng nhập lỗi !!!")
                                .setConfirmText("Thử lại")
                                .showCancelButton(false)
                                .setCancelClickListener(null)
                                .setConfirmClickListener(null)
                                .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                        pDialog.getButton(SweetAlertDialog.BUTTON_CONFIRM).setBackground(getResources().getDrawable(R.drawable.button_login));

                    }
                }, 1000);

            }
        });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
//    }

}
