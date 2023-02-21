package com.cycos.learn.quarkus.reactive;

import java.lang.System.Logger.Level;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 *
 * @author todor
 */
@ApplicationScoped
public class RoomService {
  private static final System.Logger LOG = System.getLogger(RoomService.class.getName());

  @Inject
  Set<String> knownNames;

  private final ConcurrentMap<String, Room> rooms = new ConcurrentHashMap<>();

  @Inject
  void initRooms(Set<Room> existingRooms) {
    existingRooms.forEach(r -> rooms.putIfAbsent(r.getName(), r));
  }

  @PostConstruct
  public void init() {
    LOG.log(Level.INFO, () -> "room-service initialized, rooms: " + rooms.values().stream().sorted().toList());
    LOG.log(Level.INFO, () -> "known persons: " + knownNames);
  }

  @PreDestroy
  public void close() {
    rooms.clear();
    LOG.log(Level.INFO, () -> "room-service closed");
  }

  /**
   * Creates a new room and starts it. If the room cannot be or has already been created, this method returns
   * {@code false}.
   *
   * @param name the name of the room
   * @param location the location of the room
   * @return {@code true} if created and started, otherwise {@code false}
   */
  public boolean createRoom(String name, String location) {
    Objects.requireNonNull(name, "name must not be null");
    return rooms.computeIfAbsent(name, k -> new Room(k, location)).open();
  }

  /**
   * Gets the room with the specified {@code name} if exists.
   *
   * @param name the name of the room to get
   * @return the Room instance if found, otherwise {@code null}
   */
  public Room getRoom(String name) {
    return name == null ? null : rooms.get(name);
  }

  public boolean enterRoom(String roomName, String personName) {
    Objects.requireNonNull(roomName, "roomName must not be null");
    Objects.requireNonNull(personName, "roomName must not be null");
    boolean success = rooms.containsKey(roomName); // room exists
    success = success && knownNames.parallelStream().anyMatch(personName::equalsIgnoreCase); // known person
    if (success) {
      LOG.log(Level.INFO, () -> personName + " entered room " + roomName);
    }
    return success;
  }
}
