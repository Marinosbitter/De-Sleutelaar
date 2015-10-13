package nl.hsleiden.imtpmd.desleutelaar.database;

/**
 * Created by Marijn on 13/10/2015.
 */
public class DatabaseInfo {
    public class LockTables {
        public static final String LOCK = "lock";
    }

    public class LockColumn {
        public static final String INFO_EXCERPT = "info_excerpt";
        public static final String INFO = "info";
    }

    public class CustomerTables {
        public static final String CUSTOMER = "customer";
    }

    public class CustomerColumn {
        public static final String NAME = "name";
        public static final String ADRESS = "adress";
        public static final String PHONE = "phone";
        public static final String MAIL = "mail";
        public static final String TYPE = "type";
    }
}
