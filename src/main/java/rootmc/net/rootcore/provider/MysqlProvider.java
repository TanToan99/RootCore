package rootmc.net.rootcore.provider;

import cn.nukkit.Server;
import cn.nukkit.utils.Config;
import rootmc.net.rootcore.RootCore;

import java.io.File;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.UUID;

public class MysqlProvider implements Provider {

    private Connection connection;
    private String database;

    @Override
    public void init(File file) { }

    @Override
    public void open() {
        RootCore plugin = RootCore.get();
        Config c = plugin.getConfig();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            database = c.getString("mysql.database");
            String connectionUri = "jdbc:mysql://" + c.getString("mysql.ip") + ":" + c.getString("mysql.port") + "/" + c.getString("mysql.database")+"?autoReconnect=true";
            connection = DriverManager.getConnection(connectionUri, c.getString("mysql.username"), c.getString("mysql.password"));
            connection.setAutoCommit(true);
            DatabaseMetaData dbm = null;
            dbm = connection.getMetaData();
            ResultSet tables = dbm.getTables(null, null, "rootaccount", null);
            if (!tables.next()) {
                String tableCreate = "CREATE TABLE rootaccount (" +
                        "uuid VARCHAR(64) NOT NULL UNIQUE, " +
                        "username VARCHAR(64) NOT NULL, " +
                        "password VARCHAR(64) NULL, " +
                        "rootpoint int default 0, " +
                        "constraint rootpoint_pk primary key(uuid))";
                Statement createTable = connection.createStatement();
                createTable.executeUpdate(tableCreate);
            }
            tables = dbm.getTables(null, null, "account_transaction_history", null);
            if (!tables.next()) {
                String tableCreate = "CREATE TABLE account_transaction_history(" +
                        "id int(11) AUTO_INCREMENT, " +
                        "username VARCHAR(64) NOT NULL, " +
                        "transactiontype VARCHAR(225) NOT NULL," +
                        "content text NOT NULL," +
                        "amount int(11) NOT NULL," +
                        "surplus int(11) NOT NULL," +
                        "datetime date NOT NULL," +
                        "CONSTRAINT `id_pk` primary key(id))";
                Statement createTable = connection.createStatement();
                createTable.executeUpdate(tableCreate);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("It was not possible to establish a connection with the database.");
        } catch (ClassNotFoundException ex) {
            System.out.println("MySQL Driver is missing... Are you using the right .jar file?");
        }
    }

    @Override
    public void add_transaction(String player, String type, String content, int amount, int surplus) {
        try {
            PreparedStatement newUserStatement = connection.prepareStatement("INSERT INTO " + database + ".account_transaction_history (username, transactiontype, content, amount, surplus, datetime) VALUES (?,?,?,?,?,?)");
            newUserStatement.setString(1, player);
            newUserStatement.setString(2, type);
            newUserStatement.setString(3, content);
            newUserStatement.setInt(4, amount);
            newUserStatement.setInt(5, surplus);
            newUserStatement.setDate(6, new Date(System.currentTimeMillis()));
            newUserStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void save() { }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean accountExists(String uuid) {
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM " + database + ".rootaccount WHERE uuid='" + uuid + "'");
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean removeAccount(String uuid) {
        if (accountExists(uuid)) {
            try {
                PreparedStatement deleteStatement = connection.prepareStatement("UPDATE " + database + ".rootaccount SET rootpoint = 0 WHERE uuid=?");
                deleteStatement.setString(1, uuid);
                deleteStatement.execute();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean createAccount(String uuid, int defaultrootpoint) {
        if (!this.accountExists(uuid)) {
            try {
                PreparedStatement newUserStatement = connection.prepareStatement("INSERT INTO " + database + ".rootaccount (uuid, username, rootpoint) VALUES (?,?,?)");
                newUserStatement.setString(1, uuid);
                newUserStatement.setString(2, Server.getInstance().getPlayer(UUID.fromString(uuid)).get().getName().toLowerCase());
                newUserStatement.setInt(3, defaultrootpoint);
                newUserStatement.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean setRootPoint(String uuid, int amount) {
        try {
            connection.createStatement().executeUpdate("UPDATE " + database + ".rootaccount SET rootpoint = " + amount +" WHERE uuid='" + uuid + "'");
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean addRootPoint(String uuid, int amount) {
        try {
            connection.createStatement().executeUpdate("UPDATE " + database + ".rootaccount SET rootpoint = rootpoint +" + amount +" WHERE uuid='" + uuid + "'");
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean reduceRootPoint(String uuid, int amount) {
        try {
            connection.createStatement().executeUpdate("UPDATE " + database + ".rootaccount SET rootpoint = rootpoint -" + amount +" WHERE uuid='" + uuid + "'");
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public int getRootPoint(String uuid) {
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM " + database + ".rootaccount WHERE uuid='" + uuid + "'");
            if (resultSet.next()) {
                return resultSet.getInt("rootpoint");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    @Override
    public String getPassLv2(String uuid) {
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM " + database + ".rootaccount WHERE uuid='" + uuid + "'");
            if (resultSet.next()) {
                return resultSet.getString("password");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean setPassLv2(String uuid, String pass) {
        try {
            connection.createStatement().executeUpdate("UPDATE " + database + ".rootaccount SET password = " + pass +" WHERE uuid='" + uuid + "'");
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public LinkedHashMap<String, Integer> getAll() {
        LinkedHashMap<String, Integer> all = new LinkedHashMap<String, Integer>();
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM " + database + ".rootaccount");
            while (resultSet.next()) {
                all.put(resultSet.getString("username"), resultSet.getInt("rootpoint"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return all;
    }

    @Override
    public String getName() {
        return "MySQL";
    }

}