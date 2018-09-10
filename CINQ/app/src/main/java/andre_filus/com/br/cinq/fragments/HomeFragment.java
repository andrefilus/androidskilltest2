package andre_filus.com.br.cinq.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import andre_filus.com.br.cinq.R;
import andre_filus.com.br.cinq.adapters.UserAdapter;
import andre_filus.com.br.cinq.data.Business;
import andre_filus.com.br.cinq.databinding.FragmentHomeBinding;
import andre_filus.com.br.cinq.models.User;
import andre_filus.com.br.cinq.utils.ErrorDialogUtils;

/**
 * Created by André Filus on 08/09/2018.
 */

public class HomeFragment extends Fragment implements UserAdapter.UserClickListener {

    private static final String EXTRA_USER = "x_user";
    private static final String EXTRA_USERS_LIST = "x_user_list";
    private OnHomeFragmentActionListener mListener;
    private FragmentHomeBinding mFragmentBinding;
    private UserAdapter adapter;
    private ArrayList<User> mUsers = new ArrayList<>();
    private User mUser;
    private Business mBusiness;

    public HomeFragment() {}

//    public static HomeFragment newInstance(User user) {
    public static HomeFragment newInstance(ArrayList<User> users, User user) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_USER, user);
        args.putParcelableArrayList(EXTRA_USERS_LIST, users);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUser = getArguments().getParcelable(EXTRA_USER);
        mUsers = getArguments().getParcelableArrayList(EXTRA_USERS_LIST);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        return mFragmentBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mBusiness = new Business(getContext());
        mFragmentBinding.txtName.setText(getString(R.string.s_welcome, mUser.name));
        adapter = new UserAdapter(mUsers, HomeFragment.this);
        mFragmentBinding.recyclerUsers.setLayoutManager(new LinearLayoutManager(getContext()));
        mFragmentBinding.recyclerUsers.setAdapter(adapter);
//        mFragmentBinding.recyclerUsers.setAdapter(new UserAdapter(mUsers, HomeFragment.this));
    }

    public void reloadUsers(ArrayList<User> filtered){
        adapter.updateList(filtered);
//        mFragmentBinding.recyclerUsers.getAdapter().notifyDataSetChanged();
//        mFragmentBinding.recyclerUsers.setAdapter(new UserAdapter(filtered, HomeFragment.this));
    }

    public void reloadUsers(){
        mUsers = mBusiness.getUsers();
        adapter.updateList(mUsers);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnHomeFragmentActionListener) {
            mListener = (OnHomeFragmentActionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onUserEditItemClick(User user) {
        mListener.onHomeFragmentActionUpdate(user);
    }

    @Override
    public void onUserDeleteItemClick(User user) {
        if(user.id == mUser.id){
            ErrorDialogUtils.showDialogError(getContext(), mFragmentBinding.recyclerUsers, "Ok", "Não é possível excluir o seu próprio usuário");
            return;
        }
        mListener.onHomeFragmentActionDelete(user);
    }

    public interface OnHomeFragmentActionListener {
        void onHomeFragmentActionUpdate(User user);
        void onHomeFragmentActionDelete(User user);
    }
}
