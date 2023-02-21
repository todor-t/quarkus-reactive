package com.cycos.learn.quarkus.reactive;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * @author todor
 */
public class Room implements Comparable<Room> {
  private final String name;
  private final String location;
  private final AtomicBoolean open = new AtomicBoolean();

  public Room(String name, String location) {
    this.name = name;
    this.location = location;
  }

  public boolean open() {
    return open.compareAndSet(false, true);
  }

  public String getName() {
    return name;
  }

  public String getLocation() {
    return location;
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 37 * hash + Objects.hashCode(this.name);
    hash = 37 * hash + Objects.hashCode(this.location);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Room other = (Room) obj;
    if (!Objects.equals(this.name, other.name)) {
      return false;
    }
    return Objects.equals(this.location, other.location);
  }

  @Override
  public int compareTo(Room o) {
    return name.compareTo(o.name);
  }

  @Override
  public String toString() {
    return "Room{" + "name=" + name + ", location=" + location + '}';
  }
}
