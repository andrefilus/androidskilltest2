package andre_filus.com.br.cinq.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;

import andre_filus.com.br.cinq.R;

/**
 * Created by André Filus on 08/09/2018.
 */

public class PermissionUtils {

    public static final int STORAGE_PERMISSION = 1;

    public static boolean needToAskPermission() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    public static boolean hasStoragePermission(Context context) {
        if (needToAskPermission()) {
            return (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        }
        return true;
    }

    public static void asksStoragePermission(final Activity activity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(activity)
                    .setMessage(activity.getString(R.string.permission_storage_explanation))
                    .setPositiveButton(R.string.s_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(activity, new String[]{
                                            Manifest.permission.READ_EXTERNAL_STORAGE,
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                                    },
                                    PermissionUtils.STORAGE_PERMISSION);
                        }
                    }).show();
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, PermissionUtils.STORAGE_PERMISSION
            );
        }
    }
}
