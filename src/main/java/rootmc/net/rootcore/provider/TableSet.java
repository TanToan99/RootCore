package rootmc.net.rootcore.provider;


public enum TableSet {

    LANGUAGE_TABLE("CREATE TABLE IF NOT EXISTS language(" +
            "code VARCHAR(64) PRIMARY KEY," +
            "name VARCHAR(64) NOT NULL) %OPTIMIZE"),

    ACCOUNT_TABLE("CREATE TABLE IF NOT EXISTS account(" +
            "uuid VARCHAR(64) NOT NULL UNIQUE PRIMARY KEY, " +
            "username VARCHAR(64) NOT NULL, " +
            "password VARCHAR(64) NULL, " +
            "rootpoint int default 0, " +
            "language VARCHAR(64) NOT NULL," +
            "onlinetime int default 0," +
            "FOREIGN KEY (uuid) REFERENCES language(code) ON UPDATE CASCADE) %OPTIMIZE"),

    TRANSACTION_TABLE("CREATE TABLE IF NOT EXISTS transaction(" +
            "id int(11) AUTO_INCREMENT PRIMARY KEY," +
            "uuid VARCHAR(64) NOT NULL," +
            "type VARCHAR(225) NOT NULL," +
            "content text NOT NULL," +
            "amount int(11) NOT NULL," +
            "surplus int(11) NOT NULL," +
            "datetime date NOT NULL," +
            "FOREIGN KEY (uuid) REFERENCES account(uuid) ON UPDATE CASCADE) %OPTIMIZE"),

    FOR_TABLE_OPTIMIZE_A("SET GLOBAL innodb_file_per_table=1"),
    FOR_TABLE_OPTIMIZE_B("SET GLOBAL innodb_file_format=Barracuda"),

    SELECT_ACCOUNT("SELECT * FROM account WHERE uuid=?"),
    SELECT_ALL_ACCOUNT("SELECT * FROM account"),

    UPDATE_PASSWD_ACCOUNT("UPDATE rootaccount SET password = ? WHERE uuid= ?"),
    UPDATE_REDUCERP_ACCOUNT("UPDATE account SET rootpoint = rootpoint - ? WHERE uuid= ?"),
    UPDATE_ADDRP_ACCOUNT("UPDATE account SET rootpoint = rootpoint + ? WHERE uuid= ?"),
    UPDATE_RP_ACCOUNT("UPDATE account SET rootpoint = ? WHERE uuid= ?"),
    UPDATE_REMOVE_ACCOUNT("UPDATE account SET rootpoint = 0 WHERE uuid=?"),

    INSERT_TRANSACTION("INSERT INTO transaction (uuid, type, content, amount, surplus, datetime) VALUES (?,?,?,?,?,?)"),
    INSERT_ACCOUNT("INSERT INTO account (uuid, username,language) VALUES (?,?,?)");


    private final String query;

    TableSet(String query) {
        this.query = query;
    }

    public String getQuery() {
        String resultPoint = query;
        resultPoint = resultPoint.replace("%IGNORE", "IGNORE");
        resultPoint = resultPoint.replace("%OPTIMIZE", "ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPRESSED");
        return resultPoint;
    }
}