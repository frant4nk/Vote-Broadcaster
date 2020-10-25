package io.github.frant4nk.votebroadcaster;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.service.sql.SqlService;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLUtil
{
    private static Connection connect = null;
    private static Statement statement = null;
    private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;
    private static SqlService sql;
    private static final Votebroadcaster plugin = Votebroadcaster.instance;

    public static DataSource getDataSource(String jdbcUrl) throws SQLException
    {
        if (sql == null) {
            sql = Sponge.getServiceManager().provide(SqlService.class).get();
        }
        return sql.getDataSource(jdbcUrl);
    }

    /*
    public static void saveVote(String username) throws Exception {
        String uri = plugin.getConfig().getNode("database").getString();
        String sql = "INSERT INTO votes (username, dayvotes, totalvotes) VALUES (\"" + username + "\", 1, 1) ON DUPLICATE KEY UPDATE dayvotes = dayvotes + 1, totalvotes = totalvotes + 1;";

        try (Connection conn = getDataSource(uri).getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet results = stmt.executeQuery()) {

            while(results.next())
            {
                System.out.println(results.toString());
            }

        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }
    }
    */
    public static String getVotes(String username) throws Exception
    {
        String uri = plugin.getConfig().getNode("database").getString();
        String sql = "SELECT totalvotes FROM votes WHERE username = \"" + username + "\"";
        String response = "0";

        try (Connection conn = getDataSource(uri).getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet results = stmt.executeQuery()) {

             while(results.next())
             {
                 response = results.getString(1);
             }

        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }
        return response;
    }

    public static List<String> getTop10Names() throws Exception
    {
        String uri = plugin.getConfig().getNode("database").getString();
        String sql = "SELECT username FROM votes ORDER BY totalvotes DESC LIMIT 10";
        List<String> response = new ArrayList<>();

        try (Connection conn = getDataSource(uri).getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet results = stmt.executeQuery()) {

            while(results.next())
            {
                response.add(results.getString(1));
            }

        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }
        return response;
    }

    public static List<String> getTop10Votes() throws Exception
    {
        String uri = plugin.getConfig().getNode("database").getString();
        String sql = "SELECT totalvotes FROM votes ORDER BY totalvotes DESC LIMIT 10";
        List<String> response = new ArrayList<>();

        try (Connection conn = getDataSource(uri).getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet results = stmt.executeQuery()) {

            while(results.next())
            {
                response.add(results.getString(1));
            }

        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }
        return response;
    }

    private static void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {
        }
    }
}
