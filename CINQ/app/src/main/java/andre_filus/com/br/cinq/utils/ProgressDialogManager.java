package andre_filus.com.br.cinq.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import andre_filus.com.br.cinq.R;
import andre_filus.com.br.cinq.databinding.LoadingViewBinding;

/**
 * Created by André Filus on 08/09/2018.
 */

public abstract class ProgressDialogManager {

    private static LoadingViewBinding mLoadingBinding;
    private static AlertDialog mDialog = null;
    private static int mDialogCreatorId;

    public static void showLoading(int creatorId, Context context, int message, boolean isCancelable, DialogInterface.OnDismissListener dismissListener) {
        createAndShowLoading(creatorId, context, context.getString(message), isCancelable, dismissListener);
    }

    public static void hideLoading(int creatorId) {
        if(mDialog != null && mDialogCreatorId == creatorId) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    private static void createAndShowLoading(int creatorId, Context context, String message, boolean isCancelable, ProgressDialog.OnDismissListener onDismissListener) {
        if (mDialog == null) {
            mDialogCreatorId = creatorId;
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setView(R.layout.loading_view);
            builder.setTitle("");
            builder.setMessage(message);
            builder.setCancelable(isCancelable);
            builder.setOnDismissListener(onDismissListener);
            mDialog = builder.create();
            mDialog.show();
        }
    }

}
