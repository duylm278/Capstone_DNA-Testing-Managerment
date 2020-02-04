package capstone.summer.project.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDateTime;

import capstone.summer.R;
import capstone.summer.project.Service.UserService;
import capstone.summer.project.Service.VolleyCallback;
import capstone.summer.project.model.User;
import capstone.summer.project.utils.ConstantManager;

public class UserInfoFragment extends Fragment {
    private TextInputLayout username,phone,fullname,dateOfBirth,address;
    private LinearLayout linearLayout;
    private Button update;
    private InputMethodManager imm;
    final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_info, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        username = getActivity().findViewById(R.id.username);
        phone = getActivity().findViewById(R.id.phone);
        fullname = getActivity().findViewById(R.id.fullname);
        dateOfBirth=getActivity().findViewById(R.id.dateOfBirth);
        address = getActivity().findViewById(R.id.address);
        linearLayout=getActivity().findViewById(R.id.out);
        update = getActivity().findViewById(R.id.btbGoToUpdateUser);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imm.hideSoftInputFromWindow(getView().getWindowToken(), flags);
            }
        });
        SharedPreferences sharedPreferences = getActivity().getApplicationContext().getSharedPreferences(ConstantManager.USER_MANAGER, Context.MODE_PRIVATE);
        final User[] user = new User[1];
        UserService.getUserByID(getActivity(), Integer.parseInt(sharedPreferences.getString(ConstantManager.USER_ID, "")), new VolleyCallback() {
            @Override
            public void onSuccess(Object data) {
                user[0] = (User)data;
                username.getEditText().setText(user[0].getUserName());
                phone.getEditText().setText(user[0].getPhone());
                fullname.getEditText().setText(user[0].getName());
                LocalDateTime localDateTime = LocalDateTime.parse(user[0].getDoB());
                dateOfBirth.getEditText().setText(localDateTime.toLocalDate()+"");
                address.getEditText().setText(user[0].getAddress());
            }

            @Override
            public void onFail(Object data) {

            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressdialog = new ProgressDialog(getActivity());
                progressdialog.setMessage("Please Wait....");
                progressdialog.show();
                SharedPreferences sharedPreferences = getActivity().getApplicationContext().getSharedPreferences(ConstantManager.USER_MANAGER, Context.MODE_PRIVATE);
                user[0].setPhone(phone.getEditText().getText()+"");
                user[0].setName(fullname.getEditText().getText()+"");
                user[0].setAddress(address.getEditText().getText()+"");
                user[0].setPassword(sharedPreferences.getString(ConstantManager.USER_PASSWORD,""));
                UserService.Update(getActivity(), user[0], new VolleyCallback() {
                    @Override
                    public void onSuccess(Object data) {
                        progressdialog.dismiss();
                        Toast.makeText(getActivity(), "Cập nhật thành công !!!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFail(Object data) {
                        progressdialog.dismiss();
                        Toast.makeText(getActivity(), "Cập nhật thất bại !!!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imm = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);

    }
}
