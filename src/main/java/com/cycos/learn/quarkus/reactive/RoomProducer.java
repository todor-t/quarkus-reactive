package com.cycos.learn.quarkus.reactive;

import java.util.LinkedHashSet;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

/**
 *
 * @author todor
 */
@ApplicationScoped
public class RoomProducer {
  private static final String LOCATION = "Alsdorf";

  @Produces
  public Set<Room> createRooms() {
    Set<Room> rooms = new LinkedHashSet<>();
    rooms.add(new Room("Room 1", LOCATION));
    rooms.add(new Room("Room 1", LOCATION)); // ingored - entry already exists
    rooms.add(new Room("Room 2", LOCATION));
    rooms.add(new Room("Room 3", LOCATION));
    return rooms;
  }
}
