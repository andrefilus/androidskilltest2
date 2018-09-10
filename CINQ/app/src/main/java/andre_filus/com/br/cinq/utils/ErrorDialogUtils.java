package andre_filus.com.br.cinq.utils;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import andre_filus.com.br.cinq.R;
import andre_filus.com.br.cinq.databinding.LayoutDialogErrorBinding;
import andre_filus.com.br.cinq.databinding.LayoutDialogSuccessBinding;

/**
 * Created by André Filus on 08/09/2018.
 */

public abstract class ErrorDialogUtils {

    public static void showDialogError(@NonNull Context context, @NonNull ViewGroup father, String stringButton, String stringMessage) {
        View inflatedView = LayoutInflater.from(context).inflate(R.layout.layout_dialog_error, father, false);
        LayoutDialogErrorBinding dialogFeedBinding = DataBindingUtil.bind(inflatedView);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogFeedBinding.getRoot());
        builder.setCancelable(true);
        dialogFeedBinding.textErrorMessage.setText(stringMessage);
        dialogFeedBinding.textContinue.setText(stringButton);
        final AlertDialog dialog = builder.create();
        dialogFeedBinding.textContinue.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    public static void showDialogSuccess(@NonNull Context context, @NonNull ViewGroup father, String stringButton, String stringMessage) {
        View inflatedView = LayoutInflater.from(context).inflate(R.layout.layout_dialog_success, father, false);
        LayoutDialogSuccessBinding dialogFeedBinding = DataBindingUtil.bind(inflatedView);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogFeedBinding.getRoot());
        builder.setCancelable(true);
        dialogFeedBinding.txtMessage.setText(stringMessage);
        final AlertDialog dialog = builder.create();
        dialogFeedBinding.imgSuccess.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialogFeedBinding.imgClose.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }

}
