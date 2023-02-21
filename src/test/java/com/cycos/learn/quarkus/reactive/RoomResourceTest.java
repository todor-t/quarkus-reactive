package com.cycos.learn.quarkus.reactive;

import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.HttpHeaders.ACCEPT_LANGUAGE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.emptyString;

import java.lang.System.Logger;
import java.lang.System.Logger.Level;

import javax.ws.rs.core.Response;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class RoomResourceTest {
  private static final Logger LOG = System.getLogger(RoomResourceTest.class.getSimpleName());
  private final String location = "Alsdorf";

  @Test
  public void checkRoomCreate() {
    LOG.log(Level.INFO, "\n************ create room ************\n");
    createRoom("Vilcabamba", location).statusCode(Response.Status.NO_CONTENT.getStatusCode()).body(is(emptyString()));

    LOG.log(Level.INFO, "\n************ create room again ************\n");
    createRoom("Vilcabamba", location).statusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
  }

  @Test
  public void checkRoomGet() {
    LOG.log(Level.INFO, "\n************ create room ************\n");
    final String name = "Azubi";
    createRoom(name, location).statusCode(Response.Status.NO_CONTENT.getStatusCode());

    LOG.log(Level.INFO, "\n************ get room ************\n");
    getRoom(name).statusCode(Response.Status.OK.getStatusCode()).contentType(ContentType.JSON)
        .body("name", equalTo(name)).and().body("location", equalTo(location));
  }

  @Test
  public void checkRoomEnter() {
    LOG.log(Level.INFO, "\n************ enter room ************\n");
    enterRoom("Room 1", "alice").statusCode(Response.Status.NO_CONTENT.getStatusCode()).body(is(emptyString()));

    LOG.log(Level.INFO, "\n************ enter unknown room ************\n");
    enterRoom("Room X", "alice").statusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
  }

  private static ValidatableResponse createRoom(String name, String location) {
    return given().log().all().pathParam("name", name).queryParams("location", location).accept(ContentType.JSON)
        .when().put("/rooms/{name}")
        .then().log().all();
  }

  private static ValidatableResponse getRoom(final String name) {
    return given().log().all().pathParam("name", name).accept(ContentType.JSON)
        .when().get("room/{name}")
        .then().log().all();
  }

  private static ValidatableResponse enterRoom(String room, String person) {
    return given().log().all().pathParam("room", room).queryParam("person", person).accept(ContentType.JSON).header(ACCEPT_LANGUAGE, "de")
        .when().post("room/{room}/enter")
        .then().log().all();
  }
}
