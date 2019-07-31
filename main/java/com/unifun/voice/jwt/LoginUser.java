package com.unifun.voice.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.unifun.voice.model.TokenResponse;
import com.unifun.voice.model.User;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Path("/login")
public class LoginUser {
    private static Map<String,String> refreshTokens = new HashMap<>();

    private String SecretKey = "veryverysecretkey123";

    public static Map<String,String> getRefreshTokenInstance() {
        if(refreshTokens == null) {
            refreshTokens =  new HashMap<String, String>();
        }
        return refreshTokens;
    }

    @POST
    public String getUserData(String req) throws SQLException{
        User user = validateUserLdap(req);
        if (user != null) {
            LoginSession(user.getUsername());
            String token = genToken(user.getUsername());
            String refreshToken = genRefreshToken();
            addRefreshTokenToList(user, refreshToken);
            return tokenResponse(token, refreshToken);
        }
        return "notok";
    }



    public User validateUserLdap (String reqBody) {
        Jsonb jsonb = JsonbBuilder.create();
        User user = jsonb.fromJson(reqBody, User.class);

        try {
            Hashtable<String, String> env = new Hashtable<String, String>();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.PROVIDER_URL, "ldap://localhost:389");
            env.put(Context.SECURITY_AUTHENTICATION, "simple");
            env.put(Context.SECURITY_PRINCIPAL, "cn=" + user.getUsername() + ",ou=users,dc=unifun,dc=in");
            env.put(Context.SECURITY_CREDENTIALS, user.getPassword());
            DirContext ctx = new InitialDirContext(env);
            ctx.close();

            return user;
        } catch (Exception e) {
            return null;
        }
    }

    //    LOGIN SEESION ---------->

    public  void  LoginSession(String username)  throws SQLException {



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
                String sql = "insert into logs " + " (name,action, datelog)" + " values ('" + username + "','Login','" + datat + "')";

                myStmt.executeUpdate(sql);

                System.out.println("Insert complete.");

        }
        catch (Exception exc) {
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


    private String genToken(String username){

        try {
            Algorithm algorithmHS = Algorithm.HMAC256(SecretKey);
            String token = JWT.create()
                    .withIssuer("auth0")
                    .withClaim("userID", username )
                    .withExpiresAt(new Date(System.currentTimeMillis()+(10*1*1000)))
                    .sign(algorithmHS);
            return token;
        } catch (JWTCreationException exception){
            return "invalid token";
        }
    }

    private String tokenResponse(String token, String refreshToken){
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setJwt(token);
        tokenResponse.setRefreshToken(refreshToken);
        Jsonb jsonb1 = JsonbBuilder.create();
        return jsonb1.toJson(tokenResponse);
    }

    private void addRefreshTokenToList(User user, String refreshToken){
        LoginUser.getRefreshTokenInstance().put(user.getUsername(),refreshToken);
    }

    private String genRefreshToken(){
        try {
            Algorithm algorithmHS = Algorithm.HMAC256(SecretKey);
            String refreshToken = JWT.create()
                    .withIssuer("auth0")
                    .withClaim("userID", "text" )
                    .withExpiresAt(new Date(System.currentTimeMillis()+(100*60*1000)))
                    .sign(algorithmHS);
            return refreshToken;
        } catch (JWTCreationException exception){
            return "invalid refresh token";
        }
    }



}
