package com.cycos.learn.quarkus.reactive;

import javax.inject.Inject;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author todor
 */
@Path("/rooms")
@Produces(MediaType.APPLICATION_JSON)
public class RoomFactoryResource {
  private static final System.Logger LOG = System.getLogger(RoomFactoryResource.class.getName());
  @Inject
  RoomService service;

  @PUT
  @Path("{name}")
  public void create(String name, @QueryParam("location") String location) {
    boolean success = service.createRoom(name, location);
    if (success) {
      LOG.log(System.Logger.Level.DEBUG, () -> "successfully created room " + name);
    } else {
      throw new IllegalStateException("The room " + name + " already exists.");
    }
  }
}
