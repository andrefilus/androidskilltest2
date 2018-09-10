package andre_filus.com.br.cinq.activities;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import andre_filus.com.br.cinq.R;
import andre_filus.com.br.cinq.databinding.ActivityRegisterBinding;
import andre_filus.com.br.cinq.utils.ErrorDialogUtils;

/**
 * Created by André Filus on 08/09/2018.
 */

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding mActivityBinding;
    private String inputName = "", inputEmail = "", inputPassword = "", inputConfirmPassword = "";

    public static void startRegisterActivity(@NonNull Activity activity) {
        Intent menuActivityIntent = new Intent(activity, RegisterActivity.class);
        activity.startActivity(menuActivityIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);
    }

    @Override
    protected void onResume() {
        super.onResume();
        validateFields();
        mActivityBinding.btnSave.setOnClickListener(v ->{
             save();
        });
    }

    private void save(){
        LoginActivity.startLoginActivity(this);
        finish();
    }

    private void validateFields(){
        inputName = mActivityBinding.inputName.getText().toString();
        inputEmail = mActivityBinding.inputEmail.getText().toString();
        inputPassword = mActivityBinding.inputPassword.getText().toString();
        inputConfirmPassword = mActivityBinding.inputConfirmPassword.getText().toString();

        if(inputName.isEmpty()){
            ErrorDialogUtils.showDialogError(this, mActivityBinding.wrapperRegister, getString(R.string.s_ok), "Preencha o campo Nome");
            return;
        }
        if(inputEmail.isEmpty() || inputEmail.contains("@")){
            ErrorDialogUtils.showDialogError(this, mActivityBinding.wrapperRegister, getString(R.string.s_ok), "E-mail inválido");
            return;
        }
        if(inputPassword.isEmpty()){
            ErrorDialogUtils.showDialogError(this, mActivityBinding.wrapperRegister, getString(R.string.s_ok), "Preencha o campo de senha.");
            return;
        }
        if(inputConfirmPassword.isEmpty()){
            ErrorDialogUtils.showDialogError(this, mActivityBinding.wrapperRegister, getString(R.string.s_ok), "Preencha o campo de Confirme sua senha.");
            return;
        }
        if(!inputPassword.equals(inputConfirmPassword)){
            ErrorDialogUtils.showDialogError(this, mActivityBinding.wrapperRegister, getString(R.string.s_ok), "As senhas não coincidem.");
            return;
        }

    }
}
