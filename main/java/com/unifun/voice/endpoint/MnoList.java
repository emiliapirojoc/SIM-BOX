package com.unifun.voice.endpoint;

import com.unifun.voice.orm.model.MnoListDb;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("/MnoList")
@ApplicationScoped
@Produces("application/json")

public class MnoList {
    @Inject
    EntityManager em;

    @GET
    public String get(@QueryParam("simboxId")int id) {
        return JsonbBuilder.create().toJson(em.createQuery("Select m from MnoListDb m WHERE m.simboxId = " + id ,MnoListDb.class ).getResultList());

    }
}
