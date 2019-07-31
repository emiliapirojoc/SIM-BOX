package com.unifun.voice.jwt;

import com.unifun.voice.model.ResponseLogout;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Path("/logout")
public class LogoutUser {


    @POST
    public Response logoutUser(String req) throws SQLException {
        Jsonb jsonb = JsonbBuilder.create();
        ResponseLogout responseLogout = jsonb.fromJson(req, ResponseLogout.class);
        insertLogout(responseLogout.getUser());


        String token = "";
        if(LoginUser.getRefreshTokenInstance().containsValue(token)){
            LoginUser.getRefreshTokenInstance().entrySet()
                    .removeIf(
                            entry -> (token
                            .equals(entry.getValue())));
        }
        return Response.status(Response.Status.NO_CONTENT).entity("Logout").build();
    }

    public void insertLogout(String username) throws SQLException {
        Connection myConn = null;
        Statement myStmt = null;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String datat = dtf.format(now);

        try {


            // 1. Get a connection to database
            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb", "root", "1234");

            // 2. Create a statement
            myStmt = myConn.createStatement();

            // 3. Execute SQL query
            String sql = "insert into logs " + " (name,action, datelog)" + " values ('" + username + "','Logout','" + datat + "')";

            myStmt.executeUpdate(sql);

            System.out.println("Insert complete.");

        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            if (myStmt != null) {
                myStmt.close();
            }

            if (myConn != null) {
                myConn.close();
            }
        }

    }
}
