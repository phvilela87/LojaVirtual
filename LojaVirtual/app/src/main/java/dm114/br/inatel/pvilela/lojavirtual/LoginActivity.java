package dm114.br.inatel.pvilela.lojavirtual;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import dm114.br.inatel.pvilela.lojavirtual.tasks.UserEvents;
import dm114.br.inatel.pvilela.lojavirtual.tasks.UserTasks;
import dm114.br.inatel.pvilela.lojavirtual.webservice.WebServiceAdminMessageProviderClient;
import dm114.br.inatel.pvilela.lojavirtual.webservice.WebServiceSalesProviderClient;
import dm114.br.inatel.pvilela.lojavirtual.webservice.WebServiceUserMessageProviderClient;

public class LoginActivity extends AppCompatActivity implements UserEvents {

    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.setTitle("Login");

        context = getApplicationContext();

        if(WebServiceSalesProviderClient.isTokenValid(context)
                && WebServiceUserMessageProviderClient.isTokenValid(context)
                && WebServiceAdminMessageProviderClient.isTokenValid(context)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            mEmailView = (EditText) findViewById(R.id.email);
            mPasswordView = (EditText) findViewById(R.id.password);
            mLoginFormView = findViewById(R.id.login_form);
            mProgressView = findViewById(R.id.login_progress);

            Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
            mEmailSignInButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    attemptLogin();
                }
            });
        }

    }

    private void attemptLogin() {

        mEmailView.setError(null);
        mPasswordView.setError(null);

        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

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
            focusView.requestFocus();
        } else {
            showProgress(true);
            UserTasks userTasks = new UserTasks(this, context, mEmailView.getText().toString(), mPasswordView.getText().toString());
            userTasks.loginValidationOnSalesProvider();
        }

    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
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

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void loginSuccessOnSalesProvider() {
        UserTasks userTasks = new UserTasks(this, context, mEmailView.getText().toString(), mPasswordView.getText().toString());
        userTasks.loginValidationOnUserMessageProvider();
    }

    @Override
    public void loginFailOnSalesProvider() {
        showProgress(false);
        Toast.makeText(context, R.string.login_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginSuccessOnUserMessageProvider() {
        UserTasks userTasks = new UserTasks(this, context);
        userTasks.loginValidationOnAdminMessageProvider();
    }

    @Override
    public void loginFailOnUserMessageProvider() {
        showProgress(false);
        Toast.makeText(context, R.string.login_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginSuccessOnAdminMessageProvider() {
        showProgress(false);

        String email = mEmailView.getText().toString();

        SharedPreferences sharedSettings =
               PreferenceManager.getDefaultSharedPreferences(context);
       SharedPreferences.Editor editor = sharedSettings.edit();

        editor.putString(context.getString(R.string.pref_user_email), email);

        editor.commit();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void loginFailOnAdminMessageProvider() {
        showProgress(false);
        Toast.makeText(context, R.string.login_error, Toast.LENGTH_SHORT).show();
    }

}
