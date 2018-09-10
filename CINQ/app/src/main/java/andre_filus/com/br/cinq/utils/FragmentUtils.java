package andre_filus.com.br.cinq.utils;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import andre_filus.com.br.cinq.R;

/**
 * Created by André Filus on 08/09/2018.
 */

public abstract class FragmentUtils {

    public static void controlFragment(@NonNull AppCompatActivity fragmentsFather, @Nullable String title, @IdRes int viewContainerId, @NonNull Fragment fragmentAdd, boolean isAdd){
        FragmentTransaction fragmentTransaction = fragmentsFather.getSupportFragmentManager().beginTransaction();
        if(isAdd) {
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_right);
            fragmentTransaction.add(viewContainerId, fragmentAdd, fragmentAdd.getClass().getName());
            fragmentTransaction.setBreadCrumbTitle(title);
            fragmentTransaction.addToBackStack(fragmentAdd.getClass().getName());
        } else  fragmentTransaction.replace(viewContainerId, fragmentAdd);
        fragmentTransaction.commit();
    }

    public static void clearFragmentManagerStack(@NonNull FragmentManager fragmentManager, @NonNull String name) {
        fragmentManager.popBackStackImmediate(name, 0);
    }

    public static void clearFragmentManagerStack(@NonNull FragmentManager fragmentManager) {
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }


}
