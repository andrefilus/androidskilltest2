package andre_filus.com.br.cinq.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by André Filus on 09/09/2018.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "CinqTeste.db";

    private static final String SQL_CREATE_TABLE_USER =
            "CREATE TABLE " + DataBaseConstants.USER.TABLE_NAME
            + " (" + DataBaseConstants.USER.COLUMNS.ID  +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DataBaseConstants.USER.COLUMNS.NAME + " VARCHAR(50), "
            + DataBaseConstants.USER.COLUMNS.EMAIL + " VARCHAR(50), "
            + DataBaseConstants.USER.COLUMNS.PASSWORD + " VARCHAR(50) );";

    private static final String DROP_TABLE_USER = "DROP TABLE IF EXISTS " + DataBaseConstants.USER.TABLE_NAME;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_USER);
        db.execSQL(SQL_CREATE_TABLE_USER);
    }
}
