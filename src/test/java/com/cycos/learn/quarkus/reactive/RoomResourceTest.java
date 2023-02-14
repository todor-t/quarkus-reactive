package com.cycos.learn.quarkus.reactive;

import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.HttpHeaders.ACCEPT_LANGUAGE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.emptyString;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import javax.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class RoomResourceTest {
	private final String location = "Alsdorf";

	@Test
	public void checkRoomCreate() {
		createRoom("Vilcabamba", location).statusCode(Response.Status.NO_CONTENT.getStatusCode()).body(is(emptyString()));
		createRoom("Vilcabamba", location).statusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
	}

	@Test
	public void checkRoomGet() {
		final String name = "Azubi";
		createRoom(name, location).statusCode(Response.Status.NO_CONTENT.getStatusCode());
		getRoom(name).statusCode(Response.Status.OK.getStatusCode()).contentType(ContentType.JSON)
			.body("name", equalTo(name)).and().body("location", equalTo(location));
	}

	@Test
	public void checkRoomEnter() {
		enterRoom("Room 1", "alice").statusCode(Response.Status.NO_CONTENT.getStatusCode()).body(is(emptyString()));
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
