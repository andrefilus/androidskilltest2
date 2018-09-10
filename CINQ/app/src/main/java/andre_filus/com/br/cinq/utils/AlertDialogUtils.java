package andre_filus.com.br.cinq.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

/**
 * Created by André Filus on 08/09/2018.
 */

public class AlertDialogUtils {

    public static @NonNull
    AlertDialog.Builder buildAlertDialog(@NonNull Context context, @NonNull String dialogTitle, @NonNull String dialogMessage, boolean isDialogDismissible,
                                         @Nullable String buttonYes, @Nullable DialogInterface.OnClickListener buttonYesClick,
                                         @Nullable String buttonNo, @Nullable DialogInterface.OnClickListener buttonNoClick) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setTitle(dialogTitle)
                .setMessage(dialogMessage).setCancelable(isDialogDismissible);

        if (!TextUtils.isEmpty(buttonYes)) {
            dialogBuilder.setPositiveButton(buttonYes, buttonYesClick);
        }
        if (!TextUtils.isEmpty(buttonNo)) {
            dialogBuilder.setNegativeButton(buttonNo, buttonNoClick);
        }

        return dialogBuilder;
    }

}