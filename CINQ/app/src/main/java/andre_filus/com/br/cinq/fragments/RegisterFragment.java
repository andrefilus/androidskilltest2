package andre_filus.com.br.cinq.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import andre_filus.com.br.cinq.R;
import andre_filus.com.br.cinq.data.Business;
import andre_filus.com.br.cinq.databinding.FragmentRegisterBinding;
import andre_filus.com.br.cinq.models.User;
import andre_filus.com.br.cinq.utils.ErrorDialogUtils;

/**
 * Created by André Filus on 08/09/2018.
 */

public class RegisterFragment extends Fragment {

    private static final String EXTRA_USER = "x_user";
    private OnRegisterFragmentActionListener mListener;
    private FragmentRegisterBinding mFragmentBinding;
    private User mUser;
    private String inputName = "", inputEmail = "", inputPassword = "", inputConfirmPassword = "";
    private Business mBusiness;

    public RegisterFragment() {}

    public static RegisterFragment newInstance(User user) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUser = getArguments().getParcelable(EXTRA_USER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false);
        return mFragmentBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(mUser.id != 0){
            mFragmentBinding.inputName.setText(mUser.name);
            mFragmentBinding.inputEmail.setText(mUser.email);
            mFragmentBinding.inputEmail.setEnabled(false);
            mFragmentBinding.inputEmail.setBackgroundResource(R.color.disabled);
            mFragmentBinding.inputPassword.setText(mUser.password);
            mFragmentBinding.inputConfirmPassword.setText(mUser.password);
        }
        mFragmentBinding.btnSave.setOnClickListener(v -> validateFields());
        mBusiness = new Business(getContext());
    }

    private void validateFields(){
        inputName = mFragmentBinding.inputName.getText().toString();
        inputEmail = mFragmentBinding.inputEmail.getText().toString();
        inputPassword = mFragmentBinding.inputPassword.getText().toString();
        inputConfirmPassword = mFragmentBinding.inputConfirmPassword.getText().toString();

        if(inputName.isEmpty()){
            mFragmentBinding.inputName.setError("Campo nome não pode ser vazio");
//            ErrorDialogUtils.showDialogError(getContext(), mFragmentBinding.wrapperRegister, getString(R.string.s_ok), "Campo nome não pode ser vazio");
            return;
        }
        if(inputEmail.isEmpty() || !inputEmail.contains("@") || !inputEmail.contains(".com")){
            mFragmentBinding.inputEmail.setError("E-mail inválido");
//            ErrorDialogUtils.showDialogError(getContext(), mFragmentBinding.wrapperRegister, getString(R.string.s_ok), "E-mail inválido");
            return;
        }
        if(inputPassword.isEmpty()){
            mFragmentBinding.inputPassword.setError("Preencha o campo de senha");
//            ErrorDialogUtils.showDialogError(getContext(), mFragmentBinding.wrapperRegister, getString(R.string.s_ok), "Preencha o campo de senha.");
            return;
        }
        if(inputConfirmPassword.isEmpty()){
            mFragmentBinding.inputConfirmPassword.setError("Preencha o campo de \"Confirme sua senha\"");
//            ErrorDialogUtils.showDialogError(getContext(), mFragmentBinding.wrapperRegister, getString(R.string.s_ok), "Preencha o campo de \"Confirme sua senha\".");
            return;
        }
        if(!inputPassword.equals(inputConfirmPassword)){
            mFragmentBinding.inputConfirmPassword.setError("As senhas não coincidem.");
//            ErrorDialogUtils.showDialogError(getContext(), mFragmentBinding.wrapperRegister, getString(R.string.s_ok), "As senhas não coincidem.");
            return;
        }
        mUser.name = inputName;
        mUser.email = inputEmail;
        mUser.password = inputPassword;
        if(mUser.id == 0) {
            if(!mBusiness.verifyIfUserExists(inputEmail))
                mBusiness.insert(mUser);
        }
        else
            mBusiness.update(mUser);
        mListener.onRegisterSaveActionListener(mUser);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRegisterFragmentActionListener) {
            mListener = (OnRegisterFragmentActionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public interface OnRegisterFragmentActionListener {
        void onRegisterSaveActionListener(User user);
    }
}
