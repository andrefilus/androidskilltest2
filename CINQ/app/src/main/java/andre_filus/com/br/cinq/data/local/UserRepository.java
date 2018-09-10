package andre_filus.com.br.cinq.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import andre_filus.com.br.cinq.models.User;

/**
 * Created by André Filus on 09/09/2018.
 */

public class UserRepository {

    private static UserRepository userRepository;
    private DataBaseHelper mDataBaseHelper;

    public UserRepository(Context context) {
        this.mDataBaseHelper = new DataBaseHelper(context);
    }

    public static synchronized UserRepository getInstance(Context context) {
        if (userRepository == null) {
            userRepository = new UserRepository(context);
        }
        return userRepository;
    }

    public Boolean insert(User user) {
        try {
            SQLiteDatabase sqLiteDatabase = this.mDataBaseHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DataBaseConstants.USER.COLUMNS.NAME, user.name);
            contentValues.put(DataBaseConstants.USER.COLUMNS.EMAIL, user.email);
            contentValues.put(DataBaseConstants.USER.COLUMNS.PASSWORD, user.password);

            sqLiteDatabase.insert(DataBaseConstants.USER.TABLE_NAME, null, contentValues);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean update(User user) {
        try {
            SQLiteDatabase sqLiteDatabase = this.mDataBaseHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DataBaseConstants.USER.COLUMNS.NAME, user.name);
            contentValues.put(DataBaseConstants.USER.COLUMNS.EMAIL, user.email);
            contentValues.put(DataBaseConstants.USER.COLUMNS.PASSWORD, user.password);

            String selection = DataBaseConstants.USER.COLUMNS.ID + " = ?";
            String[] args = {String.valueOf(user.id)};

            sqLiteDatabase.update(DataBaseConstants.USER.TABLE_NAME, contentValues, selection, args);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean delete(User user) {
        try {
            SQLiteDatabase sqLiteDatabase = this.mDataBaseHelper.getWritableDatabase();

            String where = DataBaseConstants.USER.COLUMNS.ID + " = ?";
            String[] args = {String.valueOf(user.id)};

            sqLiteDatabase.delete(DataBaseConstants.USER.TABLE_NAME, where, args);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> userList = new ArrayList<>();
        try {
            SQLiteDatabase sqLiteDatabase = this.mDataBaseHelper.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery(DataBaseConstants.GET_USERS, null);
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    User user = new User();
                    user.id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.USER.COLUMNS.ID));
                    user.name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.USER.COLUMNS.NAME));
                    user.email = cursor.getString(cursor.getColumnIndex(DataBaseConstants.USER.COLUMNS.EMAIL));
                    user.password = cursor.getString(cursor.getColumnIndex(DataBaseConstants.USER.COLUMNS.PASSWORD));
                    userList.add(user);
                }
            }
            if (cursor != null)
                cursor.close();
        } catch (Exception e) {
            return userList;
        }
        return userList;
    }

    public Boolean verifyIfUserExists(String email) {
        Boolean exists = false;
        try {
            SQLiteDatabase sqLiteDatabase = this.mDataBaseHelper.getReadableDatabase();

            String[] projection = {
                    DataBaseConstants.USER.COLUMNS.ID,
                    DataBaseConstants.USER.COLUMNS.NAME,
                    DataBaseConstants.USER.COLUMNS.EMAIL,
                    DataBaseConstants.USER.COLUMNS.PASSWORD,
            };
            String where = DataBaseConstants.USER.COLUMNS.EMAIL + " = ?";
            String[] args = {email};
            Cursor cursor = sqLiteDatabase.query(DataBaseConstants.USER.TABLE_NAME, projection, where, args, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                exists = true;
            }
            if (cursor != null)
                cursor.close();
        } catch (Exception e) {
            return false;
        }
        return exists;
    }

    public User getUserByQuery(String email, String password) {
        User user = new User();
        try {
            SQLiteDatabase sqLiteDatabase = this.mDataBaseHelper.getReadableDatabase();

            String[] projection = {
                    DataBaseConstants.USER.COLUMNS.ID,
                    DataBaseConstants.USER.COLUMNS.NAME,
                    DataBaseConstants.USER.COLUMNS.EMAIL,
                    DataBaseConstants.USER.COLUMNS.PASSWORD,
            };
            String where = DataBaseConstants.USER.COLUMNS.EMAIL + " = ? AND " + DataBaseConstants.USER.COLUMNS.PASSWORD + " = ?";
            String[] args = {email, password};
            Cursor cursor = sqLiteDatabase.query(DataBaseConstants.USER.TABLE_NAME, projection, where, args, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                user.id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.USER.COLUMNS.ID));
                user.name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.USER.COLUMNS.NAME));
                user.email = cursor.getString(cursor.getColumnIndex(DataBaseConstants.USER.COLUMNS.EMAIL));
                user.password = cursor.getString(cursor.getColumnIndex(DataBaseConstants.USER.COLUMNS.PASSWORD));
            }
            if (cursor != null)
                cursor.close();
        } catch (Exception e) {
            return user;
        }
        return user;
    }


}
