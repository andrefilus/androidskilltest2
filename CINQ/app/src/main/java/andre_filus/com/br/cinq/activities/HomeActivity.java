package andre_filus.com.br.cinq.activities;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import andre_filus.com.br.cinq.R;
import andre_filus.com.br.cinq.data.Business;
import andre_filus.com.br.cinq.databinding.ActivityHomeBinding;
import andre_filus.com.br.cinq.databinding.LayoutDialogSuccessBinding;
import andre_filus.com.br.cinq.fragments.AlbumListFragment;
import andre_filus.com.br.cinq.fragments.HomeFragment;
import andre_filus.com.br.cinq.fragments.RegisterFragment;
import andre_filus.com.br.cinq.models.User;
import andre_filus.com.br.cinq.utils.BottomNavigationUtils;
import andre_filus.com.br.cinq.utils.FragmentUtils;
import andre_filus.com.br.cinq.utils.KeyboardUtils;

/**
 * Created by André Filus on 08/09/2018.
 */

public class HomeActivity extends AppCompatActivity implements HomeFragment.OnHomeFragmentActionListener, AlbumListFragment.OnAlbumListFragmentActionListener, RegisterFragment.OnRegisterFragmentActionListener {

    private ActivityHomeBinding mActivityBinding;
    private static final String EXTRA_USER = "x_user";
    private User mUser;
    private ArrayList<User> mUsersList = new ArrayList<>();
    private android.view.Menu mMenu;
    private boolean mRemoveSearchWhenOpen = false;
    private ArrayList<Integer> mMenuStackHistory = new ArrayList<>();
    private Business mBusiness;

    public static void startHomeActivity(@NonNull Context context, User user) {
        Intent homeIntent = new Intent(context, HomeActivity.class);
        homeIntent.putExtra(EXTRA_USER, user);
        context.startActivity(homeIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        mUser = getIntent().getParcelableExtra(EXTRA_USER);
        setSupportActionBar(mActivityBinding.toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        setTitle(getString(R.string.app_name));
        mBusiness = new Business(this);
        BottomNavigationUtils.setUpBottomNavigation((BottomNavigationMenuView) mActivityBinding.bottomNavigationView.getChildAt(0));

        mActivityBinding.bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.actionHome:
                        if (mActivityBinding.bottomNavigationView.getSelectedItemId() != R.id.actionHome) {
                            FragmentUtils.controlFragment(HomeActivity.this, null, R.id.fragmentContainer, HomeFragment.newInstance(mUsersList, mUser), true);
                            if (mMenuStackHistory.contains(item.getItemId())) mMenuStackHistory.remove((Integer) item.getItemId());
                            mMenuStackHistory.add(item.getItemId());
                        } else {
                            FragmentUtils.clearFragmentManagerStack(getSupportFragmentManager(), HomeFragment.class.getName());
                        }
                        break;
                    case R.id.actionListAlbums:
                        if (mActivityBinding.bottomNavigationView.getSelectedItemId() != R.id.actionListAlbums) {
                            FragmentUtils.controlFragment(HomeActivity.this, getString(R.string.s_albums) ,R.id.fragmentContainer, AlbumListFragment.newInstance(), true);
                            if (mMenuStackHistory.contains(item.getItemId())) mMenuStackHistory.remove((Integer) item.getItemId());
                            mMenuStackHistory.add(item.getItemId());
                        } else {
                            FragmentUtils.clearFragmentManagerStack(getSupportFragmentManager(), AlbumListFragment.class.getName());
                        }
                        break;
                    case R.id.actionAddUser:
                        if (mActivityBinding.bottomNavigationView.getSelectedItemId() != R.id.actionAddUser) {
                            FragmentUtils.controlFragment(HomeActivity.this, getString(R.string.s_user_registration), R.id.fragmentContainer, RegisterFragment.newInstance(new User()), true);
                            if (mMenuStackHistory.contains(item.getItemId())) mMenuStackHistory.remove((Integer) item.getItemId());
                            mMenuStackHistory.add(item.getItemId());
                        } else {
                            FragmentUtils.clearFragmentManagerStack(getSupportFragmentManager(), RegisterFragment.class.getName());
                        }
                        break;
                }
                return true;
            }
        });

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment topFragment = fragmentManager.findFragmentById(R.id.fragmentContainer);
                if (fragmentManager.getBackStackEntryCount() == 0 || topFragment instanceof HomeFragment) {
                    mMenu.getItem(0).setVisible(true);
                    getSupportActionBar().setDisplayShowHomeEnabled(false);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    setTitle(getString(R.string.app_name));
                    ((HomeFragment) topFragment).reloadUsers();
                    return;
                }
                FragmentManager.BackStackEntry backStackEntry = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1);
                mMenu.getItem(0).setVisible(false);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                if (TextUtils.isEmpty(backStackEntry.getBreadCrumbTitle())) {
                    setTitle("");
                } else setTitle(backStackEntry.getBreadCrumbTitle().toString());
            }
        });
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mActivityBinding.bottomNavigationView.setSelectedItemId(R.id.actionHome);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mUsersList = mBusiness.getUsers();
//        mUsersList = getUsers();
        FragmentUtils.controlFragment(this, null, R.id.fragmentContainer, HomeFragment.newInstance(mUsersList, mUser),false);
    }

    @Override
    public void onBackPressed() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                Fragment mostTopFragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
                if(mActivityBinding.searchBar.getVisibility() == View.VISIBLE){
                    hideSearchBar();
                    return;
                }
                if (mostTopFragment.isAdded()) {
                    if (mostTopFragment.getChildFragmentManager().getBackStackEntryCount() > 0) {
                        mostTopFragment.getChildFragmentManager().popBackStackImmediate();
                        return;
                    }
                    if (mostTopFragment instanceof HomeFragment && getSupportFragmentManager().getBackStackEntryCount() == 0) {
                        LoginActivity.startLoginActivity(HomeActivity.this);
                        finish();
                        return;
                    }
                    else if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                        FragmentUtils.controlFragment(HomeActivity.this, null, R.id.fragmentContainer, HomeFragment.newInstance(mUsersList, mUser), false);
                        return;
                    }
                }
                if (mMenuStackHistory.size() > 0) {
                    if (mostTopFragment instanceof HomeFragment)
                        mMenuStackHistory.remove(mMenuStackHistory.size() - 1);
                    else if (mostTopFragment instanceof RegisterFragment)
                        mMenuStackHistory.remove(mMenuStackHistory.size() - 1);
                    else if (mostTopFragment instanceof AlbumListFragment)
                        mMenuStackHistory.remove(mMenuStackHistory.size() - 1);
                }

                HomeActivity.super.onBackPressed();
                if (mMenuStackHistory.size() > 0) {
                    switch (mMenuStackHistory.get(mMenuStackHistory.size() - 1)) {
                        case R.id.actionHome:
                            mActivityBinding.bottomNavigationView.getMenu().getItem(0).setChecked(true);
                            break;
                        case R.id.actionListAlbums:
                            mActivityBinding.bottomNavigationView.getMenu().getItem(1).setChecked(true);
                            break;
                        case R.id.actionAddUser:
                            mActivityBinding.bottomNavigationView.getMenu().getItem(2).setChecked(true);
                            break;
                    }
                } else {
                    mActivityBinding.bottomNavigationView.getMenu().getItem(0).setChecked(true);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        mMenu = menu;
        if (mRemoveSearchWhenOpen) {
            mRemoveSearchWhenOpen = false;
            removeSearchIconFromMenu();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionSearch:
                showSearchBar();
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showSearchBar() {
        mActivityBinding.buttonClose.setOnClickListener(v -> {
            hideSearchBar();
            Fragment topFragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
            if (topFragment instanceof HomeFragment)
                ((HomeFragment) topFragment).reloadUsers(mUsersList);
        });
        mActivityBinding.searchBar.animate().alpha(1.0f).setDuration(300).withStartAction(new Runnable() {
            @Override
            public void run() {
                mActivityBinding.searchBar.setVisibility(View.VISIBLE);
            }
        }).withEndAction(new Runnable() {
            @Override
            public void run() {
                mActivityBinding.completeEditText.requestFocus();
                KeyboardUtils.showKeyboard(HomeActivity.this, mActivityBinding.completeEditText);
            }
        }).start();

        mActivityBinding.completeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable search) {
                ArrayList<User> teste = new ArrayList<>();
                for(User user: mUsersList){
                    if(user.name.toLowerCase().contains(search.toString().toLowerCase()) || user.email.contains(search.toString().toLowerCase()))
                        teste.add(user);
                }

                Fragment topFragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
                if (topFragment instanceof HomeFragment) {
                    ((HomeFragment) topFragment).reloadUsers(teste);
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            FragmentUtils.controlFragment(HomeActivity.this, null, R.id.fragmentContainer,
                                    HomeFragment.newInstance(teste, mUser), true);
                        }
                    }, 100);
                }
            }
        });

    }

    private void hideSearchBar() {
        KeyboardUtils.hideKeyboard(this);
        mActivityBinding.completeEditText.setText("");
        mActivityBinding.searchBar.animate().alpha(0.0f).setDuration(300).withEndAction(new Runnable() {
            @Override
            public void run() {
                mActivityBinding.searchBar.setVisibility(View.GONE);
            }
        }).start();
    }

    public void removeSearchIconFromMenu() {
        if (mMenu == null) {
            mRemoveSearchWhenOpen = true;
            return;
        }
        mMenu.getItem(0).setVisible(false);
    }

    public void showSearchIconFromMenu() {
        mMenu.getItem(0).setVisible(true);
    }

    public void showDialogSuccess(@NonNull Context context, User removeUser, @NonNull ViewGroup father, String stringMessage) {
        View inflatedView = LayoutInflater.from(context).inflate(R.layout.layout_dialog_success, father, false);
        LayoutDialogSuccessBinding dialogFeedBinding = DataBindingUtil.bind(inflatedView);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogFeedBinding.getRoot());
        builder.setCancelable(true);
        dialogFeedBinding.txtTitleMessage.setText(stringMessage);
//        dialogFeedBinding.txtMessage.setText(stringMessage);
        final AlertDialog dialog = builder.create();
        dialogFeedBinding.imgSuccess.setOnClickListener(v -> {
            this.mBusiness.delete(removeUser);
            Fragment topFragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
            if (topFragment instanceof HomeFragment)
                ((HomeFragment) topFragment).reloadUsers();
            dialog.dismiss();
            Snackbar.make(mActivityBinding.getRoot(), R.string.s_updates_success, Snackbar.LENGTH_INDEFINITE).setDuration(2000).show();
        });
        dialogFeedBinding.imgClose.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    @Override
    public void onHomeFragmentActionUpdate(User user) {
        FragmentUtils.controlFragment(this, getString(R.string.s_user_registration), R.id.fragmentContainer, RegisterFragment.newInstance(user), true);
    }

    @Override
    public void onRegisterSaveActionListener(User user) {
        onBackPressed();
        Snackbar.make(mActivityBinding.getRoot(), R.string.s_updates_success, Snackbar.LENGTH_INDEFINITE).setDuration(2000).show();
    }

    @Override
    public void onHomeFragmentActionDelete(User user) {
        showDialogSuccess(this, user, (ViewGroup) mActivityBinding.getRoot(),"Deseja excluir usuário ?");
    }
}
