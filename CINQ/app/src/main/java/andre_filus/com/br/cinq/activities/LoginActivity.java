package andre_filus.com.br.cinq.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import andre_filus.com.br.cinq.R;
import andre_filus.com.br.cinq.data.Business;
import andre_filus.com.br.cinq.databinding.ActivityLoginBinding;
import andre_filus.com.br.cinq.fragments.RegisterFragment;
import andre_filus.com.br.cinq.models.User;
import andre_filus.com.br.cinq.utils.AlertDialogUtils;
import andre_filus.com.br.cinq.utils.ErrorDialogUtils;
import andre_filus.com.br.cinq.utils.FragmentUtils;
import andre_filus.com.br.cinq.utils.PermissionUtils;

/**
 * Created by André Filus on 08/09/2018.
 */

public class LoginActivity extends AppCompatActivity implements RegisterFragment.OnRegisterFragmentActionListener{

    private ActivityLoginBinding mActivityBinding;
    private String email, password;
    private boolean mPermissionStorage = true;
    private Business mBusiness;

    public static void startLoginActivity(@NonNull Context context) {
        Intent loginIntent = new Intent(context, LoginActivity.class);
        context.startActivity(loginIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        mBusiness = new Business(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mActivityBinding.btnLogin.setOnClickListener(v -> {
            email = mActivityBinding.inputUser.getText().toString();
            password = mActivityBinding.inputPassword.getText().toString();
            if (mPermissionStorage) {
                if (validateFields())
                    login();
            } else verifyPermissions();
        });

        mActivityBinding.wrapperSignup.setOnClickListener(v -> {
            FragmentUtils.controlFragment(this, getString(R.string.s_user_registration), R.id.fragmentContainer, RegisterFragment.newInstance(new User()),false);
        });
    }

    public void login() {
        User user = mBusiness.getUserByLogin(email, password);
        if(user.id != 0) {
            HomeActivity.startHomeActivity(this, user);
            finish();
        }
        else
            ErrorDialogUtils.showDialogError(this, (ViewGroup) mActivityBinding.getRoot(), getString(R.string.s_close), getString(R.string.s_invalid_user));
    }


    //validate form fields
    private boolean validateFields() {
        if(email.isEmpty()) {
            mActivityBinding.inputUser.setError("E-mail não pode ser vazio");
            return false;
        }
        if(!email.contains("@") || !email.contains(".com")){
            mActivityBinding.inputUser.setError("Insira um e-mail válido. Exemplo: andre@gmail.com");
            return false;
        }
        if (password.isEmpty()) {
            mActivityBinding.inputPassword.setError("Senha não pode ser vazia");
            return false;
        }
        return true;
    }

        //request user permissions
    private void verifyPermissions() {
        if (!PermissionUtils.hasStoragePermission(this))
            PermissionUtils.asksStoragePermission(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PermissionUtils.STORAGE_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mPermissionStorage = true;
                    verifyPermissions();
                    return;
                }
                mPermissionStorage = false;
                AlertDialogUtils.buildAlertDialog(this, this.getString(R.string.s_atention), this.getString(R.string.without_permission_storage_explanation),
                        true, this.getString(R.string.s_ok), null, null, null).show();
                break;
            default:
                mPermissionStorage = false;
                break;
        }
    }

    @Override
    public void onRegisterSaveActionListener(User user) {
        Fragment mostTopFragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.detach(mostTopFragment);
        mActivityBinding.fragmentContainer.setVisibility(View.GONE);
//        FragmentUtils.clearFragmentManagerStack(getSupportFragmentManager());
    }
}
