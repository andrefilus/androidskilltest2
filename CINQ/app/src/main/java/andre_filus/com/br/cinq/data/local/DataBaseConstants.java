package andre_filus.com.br.cinq.data.local;

/**
 * Created by André Filus on 09/09/2018.
 */

public class DataBaseConstants {

    private DataBaseConstants(){

    }

    public static class USER {
        public static final String TABLE_NAME = "User";

        public static class COLUMNS{
            public static final String ID = "id";
            public static final String NAME = "name";
            public static final String EMAIL = "email";
            public static final String PASSWORD = "password";
        }
    }

    public static final String GET_USERS = "SELECT * FROM " + USER.TABLE_NAME;

}
