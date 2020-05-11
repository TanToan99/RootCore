package rootmc.net.rootcore.provider;

import cn.nukkit.Player;
import cn.nukkit.utils.Config;
import rootmc.net.rootcore.RootCore;

import java.io.File;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.UUID;

import static rootmc.net.rootcore.provider.TableSet.*;

public class MysqlProvider implements Provider {

    private Connection connection;
    private String database;

    @Override
    public void init(File file) {
    }

    @Override
    public void open() {
        TableSet[] tableSets = {
                LANGUAGE_TABLE,
                ACCOUNT_TABLE,
                TRANSACTION_TABLE
        };
        RootCore plugin = RootCore.get();
        Config c = plugin.getConfig();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            database = c.getString("mysql.database");
            String connectionUri = "jdbc:mysql://" + c.getString("mysql.ip") + ":" + c.getString("mysql.port") + "/" + c.getString("mysql.database") + "?autoReconnect=true";
            connection = DriverManager.getConnection(connectionUri, c.getString("mysql.username"), c.getString("mysql.password"));
            connection.setAutoCommit(true);

            Statement statement = connection.createStatement();
            //OPTIMIZE
            statement.executeUpdate(FOR_TABLE_OPTIMIZE_A.getQuery());
            statement.executeUpdate(FOR_TABLE_OPTIMIZE_B.getQuery());

            //ADD TABLE
            for (TableSet set : tableSets) {
                statement.executeUpdate(set.getQuery());
            }
            //todo: create default lang, i think not need now !
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("It was not possible to establish a connection with the database.");
        } catch (ClassNotFoundException ex) {
            System.out.println("MySQL Driver is missing... Are you using the right .jar file?");
        }
    }

    @Override
    public void add_transaction(UUID uuid, String type, String content, int amount, int surplus) {
        try {
            PreparedStatement newUserStatement = connection.prepareStatement(INSERT_TRANSACTION.getQuery());
            newUserStatement.setString(1, uuid.toString());
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
    public void save() {
        //todo: cache player + auto save 1mins
    }

    @Override
    public boolean accountExists(UUID uuid) {
        try {

            PreparedStatement statement = connection.prepareStatement(SELECT_ACCOUNT.getQuery());
            statement.setString(1, uuid.toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean removeAccount(UUID uuid) {
        if (accountExists(uuid)) {
            try {
                PreparedStatement deleteStatement = connection.prepareStatement(UPDATE_REMOVE_ACCOUNT.getQuery());
                deleteStatement.setString(1, uuid.toString());
                deleteStatement.execute();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean createAccount(Player player, String language) {
        if (!this.accountExists(player.getUniqueId())) {
            try {
                PreparedStatement newUserStatement = connection.prepareStatement(INSERT_ACCOUNT.getQuery());
                newUserStatement.setString(1, player.getUniqueId().toString());
                newUserStatement.setString(2, player.getName());
                newUserStatement.setString(3, language);
                newUserStatement.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean setRootPoint(UUID uuid, int amount) {
        try {
            PreparedStatement newUserStatement = connection.prepareStatement(UPDATE_RP_ACCOUNT.getQuery());
            newUserStatement.setInt(1, amount);
            newUserStatement.setString(2, uuid.toString());
            newUserStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return true;
    }


    @Override
    public boolean addRootPoint(UUID uuid, int amount) {
        try {
            PreparedStatement newUserStatement = connection.prepareStatement(UPDATE_ADDRP_ACCOUNT.getQuery());
            newUserStatement.setInt(1, amount);
            newUserStatement.setString(2, uuid.toString());
            newUserStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean reduceRootPoint(UUID uuid, int amount) {
        try {
            PreparedStatement newUserStatement = connection.prepareStatement(UPDATE_REDUCERP_ACCOUNT.getQuery());
            newUserStatement.setInt(1, amount);
            newUserStatement.setString(2, uuid.toString());
            newUserStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return true;
    }

    @Override
    public int getRootPoint(UUID uuid) {
        try {
            PreparedStatement newUserStatement = connection.prepareStatement(SELECT_ACCOUNT.getQuery());
            newUserStatement.setString(1, uuid.toString());
            ResultSet resultSet = newUserStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("rootpoint");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    @Override
    public String getPassLv2(UUID uuid) {
        try {
            PreparedStatement newUserStatement = connection.prepareStatement(SELECT_ACCOUNT.getQuery());
            newUserStatement.setString(1, uuid.toString());
            ResultSet resultSet = newUserStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("password");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean setPassLv2(UUID uuid, String pass) {
        try {
            PreparedStatement newUserStatement = connection.prepareStatement(UPDATE_PASSWD_ACCOUNT.getQuery());
            newUserStatement.setString(1,pass);
            newUserStatement.setString(2, uuid.toString());
            newUserStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return true;
    }

    @Override
    public LinkedHashMap<String, Integer> getAllRP() {
        LinkedHashMap<String, Integer> all = new LinkedHashMap<>();
        try {
            ResultSet resultSet = connection.createStatement().executeQuery(SELECT_ALL_ACCOUNT.getQuery());
            while (resultSet.next()) {
                all.put(resultSet.getString("username"), resultSet.getInt("rootpoint"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return all;
    }
}