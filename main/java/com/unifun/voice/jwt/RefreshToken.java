package com.unifun.voice.jwt;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("refresh")
public class RefreshToken {
    @POST
    public Response refreshToken(String req) {
        System.out.println(req.toString());
        String token = "";
        if(LoginUser.getRefreshTokenInstance().containsValue(token)){
            return null;

        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Logout").build();
        }
    }
}
