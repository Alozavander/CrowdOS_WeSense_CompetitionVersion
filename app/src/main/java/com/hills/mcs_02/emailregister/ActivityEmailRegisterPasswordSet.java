package com.hills.mcs_02.emailregister;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.gson.Gson;
import com.hills.mcs_02.BaseActivity;
import com.hills.mcs_02.R;
import com.hills.mcs_02.account.RegexVerify;
import com.hills.mcs_02.dataBeans.BeanUserAccount;
import com.hills.mcs_02.dataBeans.User;
import com.hills.mcs_02.networkclasses.interfacesPack.PostRequestUserRegister;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActivityEmailRegisterPasswordSet extends BaseActivity implements View.OnClickListener {
    private String TAG = "Activity_EmailRegister_PasswordSet";
    private EditText mPwdSetEt;
    private EditText mPwdNicknameEt;
    private EditText mPwdSetConfirmEt;
    private ImageView mBackIv;
    private Button mConfirmBtn;
    private BeanUserAccount userAccount;
    private RegexVerify regexVerify;
    private TextView mPwdInputErrorTv;
    private TextView mPwInputErrorConfirmTv;
    private String mEmailAddress;
    private boolean[] mFinishTag;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_reigister_password_set);

        mFinishTag = new boolean[]{false, false, false};
        regexVerify = new RegexVerify();

        /** Get the registered email address */
        Intent lIntent = getIntent();
        mEmailAddress = lIntent.getStringExtra("email_address");

        initView();
    }

    private void initView() {
        mPwdSetEt = findViewById(R.id.activity_er_passwordset_pwinput_et);
        mPwdNicknameEt = findViewById(R.id.activity_er_passwordset_nickname_et);
        mPwdSetConfirmEt = findViewById(R.id.activity_er_passwordset_pwconfirm_et);
        mPwdInputErrorTv = findViewById(R.id.activity_er_passwordset_pwinput_remind_tv);
        mPwInputErrorConfirmTv = findViewById(R.id.activity_er_passwordset_pwconfirm_remind_tv);
        mBackIv = findViewById(R.id.activity_er_passwordset_back);
        mConfirmBtn = findViewById(R.id.activity_er_passwordset_complete_bt);
        mBackIv.setOnClickListener(this);
        mConfirmBtn.setOnClickListener(this);

        bindTextWatcher();
        /** cancel the nice name views */
        mPwdNicknameEt.setVisibility(View.GONE);
    }

    private void bindTextWatcher() {
       /** Password input textbox listener */
        mPwdSetEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence seq, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence seq, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable edit) {
                Log.i("Register tag",edit.toString());
                if (!regexVerify.registerUsernameVerfy(edit.toString())) {
                    mPwdInputErrorTv.setVisibility(View.VISIBLE);
                    mFinishTag[0] = false;
                } else {
                    mPwdInputErrorTv.setVisibility(View.INVISIBLE);
                    mFinishTag[0] = true;
                }
                checkEnableRegister();
            }
        });

       /**  Password confirmation input textbox listener */
        mPwdSetConfirmEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence seq, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence seq, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable edit) {
                if (!edit.toString().equals(mPwdSetEt.getText().toString())) {
                    mPwInputErrorConfirmTv.setVisibility(View.VISIBLE);
                    mFinishTag[1] = false;
                } else {
                    mPwInputErrorConfirmTv.setVisibility(View.INVISIBLE);
                    mFinishTag[1] = true;
                }
                checkEnableRegister();
            }
        });

        /** Text box listener for nickname input */
        /** Cancel the view listener
        mPwdNicknameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence seq, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence seq, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable edit) {
                if (edit.toString() != null) mFinishTag[2] = true;
                else mFinishTag[2] = false;
                checkEnableRegister();
            }
        });*/
        mFinishTag[2] = true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_er_passwordset_back:
                finish();
                break;
            case R.id.activity_er_passwordset_complete_bt:
                retrofitUserCreate();
                break;
        }
    }

    private void retrofitUserCreate() {
        mUser = new User(null, mEmailAddress,
            mPwdSetEt.getText().toString(), null,1000,"null");
        Retrofit lRetrofit = new Retrofit.Builder().baseUrl(getString(R.string.base_url)).addConverterFactory(GsonConverterFactory.create()).build();
        Gson lGson = new Gson();
        String requestContent = lGson.toJson(mUser);
        RequestBody lRequestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), requestContent);

        PostRequestUserRegister lPostRequestUserRegister = lRetrofit.create(
            PostRequestUserRegister.class);
        Call<ResponseBody> call = lPostRequestUserRegister.userRegister(lRequestBody);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                switch (response.code()) {
                    case 200:
                        registerSuccess();
                        break;
                    case 400:
                        registerErrorAlert("?????????????????????");
                        break;
                    case 502:
                        registerErrorAlert("??????????????????");
                        break;
                    default:
                        registerErrorAlert("????????????");
                        break;
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

            }
        });
    }

    private void registerErrorAlert(String str) {
        new AlertDialog.Builder(ActivityEmailRegisterPasswordSet.this)
                .setTitle("????????????")
                .setMessage(str)
                .setNegativeButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
    }

    private void registerSuccess() {
        Toast.makeText(ActivityEmailRegisterPasswordSet.this,"????????????????????????",Toast.LENGTH_LONG).show();
        finish();
    }

  /** Check if you can register */
    public void checkEnableRegister() {
        if (mFinishTag[0] && mFinishTag[1] && mFinishTag[2]) {
            mConfirmBtn.setEnabled(true);
        } else mConfirmBtn.setEnabled(false);
    }
}
