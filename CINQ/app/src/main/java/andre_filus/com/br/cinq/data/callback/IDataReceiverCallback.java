package andre_filus.com.br.cinq.data.callback;

import android.support.annotation.NonNull;

/**
 * Created by André Filus on 08/09/2018.
 */

public interface IDataReceiverCallback<T> {
    void onDataReceived(T data);
    void onDataNotReceived(@NonNull Throwable response);
}
