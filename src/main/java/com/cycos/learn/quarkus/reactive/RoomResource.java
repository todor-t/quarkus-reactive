package com.cycos.learn.quarkus.reactive;

import java.lang.System.Logger;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author todor
 */
@Path("/room/{room}")
@Produces(MediaType.APPLICATION_JSON)
public class RoomResource {
	private static final Logger LOG = System.getLogger(RoomResource.class.getName());
	@Inject
	RoomService service;

	@GET
	public Room getRoom(@PathParam("room") String name) {
		Room room = service.getRoom(name);
		if (room != null) {
			LOG.log(System.Logger.Level.INFO, () -> "got room by name: " + room);
		}
		return room;
	}

	@POST
	@Path("/enter")
	public void enterRoom(@PathParam("room") String roomName, @QueryParam("person") String personName) {
		boolean success = service.enterRoom(roomName, personName);
		if (!success) {
			throw new IllegalStateException(personName + " failed to enter room " + roomName);
		}
	}
}
