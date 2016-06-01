package com.example.xqw.checkinapplication.login.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.xqw.checkinapplication.R;
import com.example.xqw.checkinapplication.base.BaseActivity;
import com.example.xqw.checkinapplication.bean.LoginRequestEntity;
import com.example.xqw.checkinapplication.bean.LoginResponseEntity;
import com.example.xqw.checkinapplication.login.presenter.ILoginPresenter;
import com.example.xqw.checkinapplication.login.presenter.LoginPresenterFactoryIml;
import com.example.xqw.checkinapplication.login.presenter.LoginPresenterLoader;
import com.example.xqw.checkinapplication.main.view.MainActivity;
import com.example.xqw.checkinapplication.utils.SharedPreferencesUtils;
import com.rey.material.widget.Button;
import com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity implements
        LoaderManager.LoaderCallbacks<ILoginPresenter>, ILoginView {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    private static final int LOADER_ID = 101;
    @Bind(R.id.remember_checkbox)
    CheckBox rememberCheckbox;
    @Bind(R.id.base_url)
    EditText baseUrl;
    private ILoginPresenter loginPresenter;
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // Utils references.
    private AutoCompleteTextView mEmailView;
    private ShowHidePasswordEditText mPasswordView;
    MaterialDialog materialDialog;
    private View mLoginFormView;
    private long exitTime = 0;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setStatusBarColor(R.color.colorPrimaryDark);
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        //populateAutoComplete();

        mPasswordView = (ShowHidePasswordEditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        sharedPreferences = SharedPreferencesUtils.getSharedPreferences();
        setAccount();
        rememberCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferencesUtils.writeBoolean("rememberAccount", isChecked);
                if (isChecked) {

                    SharedPreferencesUtils.writeString("userId", mEmailView.getText().toString());
                    SharedPreferencesUtils.writeString("password", mPasswordView.getText().toString());
                }
            }
        });
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        assert mEmailSignInButton != null;
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getSupportLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        //获取读取联系人信息，在android 6.0以下默认开启，6.0以后查询获取
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_password_null));
        }
        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //showProgress(true);
            loginPresenter.saveLoginRequestEntity(getUserInfo());
            loginPresenter.login();
            //mAuthTask = new UserLoginTask(email, password);
            //mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //判断账号
        //TODO: Replace this with your own logic
        return true;
    }

    private boolean isPasswordValid(String password) {
        //判断密码
        //TODO: Replace this with your own logic
        return password.length() > 2;
    }

    /**
     * Shows the progress Utils and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant Utils components.

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loginPresenter.onViewAttached();
    }

    @Override
    protected void onStop() {
        super.onStop();
        loginPresenter.onDestroyed();
    }

    @Override
    public Loader<ILoginPresenter> onCreateLoader(int id, Bundle args) {
        return new LoginPresenterLoader<ILoginPresenter>(this, new LoginPresenterFactoryIml(this));
    }

    @Override
    public void onLoadFinished(Loader<ILoginPresenter> loader, ILoginPresenter data) {
        this.loginPresenter = data;
    }

    @Override
    public void onLoaderReset(Loader<ILoginPresenter> loader) {
        loginPresenter = null;
    }

    @Override
    public void showProgressDialog() {
        materialDialog = new MaterialDialog.Builder(this).title("登录").content("请稍等...")
                .progress(true, 0).build();
        materialDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        materialDialog.dismiss();
    }

    @Override
    public void goToMainActivity() {
        Intent gotoMainActivity = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(gotoMainActivity);
    }

    @Override
    public LoginRequestEntity getUserInfo() {
        LoginRequestEntity loginRequestEntity = new LoginRequestEntity();
        loginRequestEntity.setUserid(mEmailView.getText().toString());
        loginRequestEntity.setPassword(mPasswordView.getText().toString());
        return loginRequestEntity;
    }

    @Override
    public void showUserInfo(LoginResponseEntity loginResponseEntity) {

        if (null == loginResponseEntity) {
            Snackbar.make(mLoginFormView, "用户名或密码错误!", Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void showNetWorkError() {
        Snackbar.make(mLoginFormView, "用户名或密码错误!", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public String getBaseUrl() {
        return baseUrl.getText().toString();
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            //登陆逻辑
            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Snackbar.make(mLoginFormView, "再按一次退出程序", Snackbar.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }

    private void setAccount() {
        rememberCheckbox.setChecked(sharedPreferences.getBoolean("rememberAccount", false));
        if (sharedPreferences.getBoolean("rememberAccount", false)) {
            mEmailView.setText(sharedPreferences.getString("userId", null));
            mPasswordView.setText(sharedPreferences.getString("password", null));
        }

    }
}

