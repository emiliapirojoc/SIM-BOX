package com.unifun.voice.endpoint;



import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import com.unifun.voice.orm.model.SimboxListDb;
import com.unifun.voice.orm.repository.SimboxListRepository;


@Path("/SimboxList")
@RequestScoped
public class SimBoxList {
	@Inject
	SimboxListRepository simboxListRepository;
	@GET
	public String get() {
		try {
			return JsonbBuilder.create().toJson(simboxListRepository.getSimboxList());

		} catch (Exception e) {
			return e.toString();
		}
	}

}