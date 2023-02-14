package com.cycos.learn.quarkus.reactive;

import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.HttpHeaders.ACCEPT_LANGUAGE;
import static org.hamcrest.CoreMatchers.is;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import javax.ws.rs.core.Response;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class GreetingResourceTest {
	@Test
	public void testHelloEndpoint() {
		hello().statusCode(Response.Status.OK.getStatusCode()).body(is("Hello from RESTEasy Reactive"));
		hello("de").statusCode(Response.Status.OK.getStatusCode()).body(is("Hallo von RESTEasy Reactive"));
		hello("fr").statusCode(Response.Status.OK.getStatusCode()).body(is("👋 RESTEasy Reactive"));
		hello("zh").statusCode(Response.Status.OK.getStatusCode()).body(is("来自 RESTEasy Reactive 的问候"));
	}

	@Test
	public void checkRamdomGreeting() {
		long n = ThreadLocalRandom.current().nextLong(4);
		final String name = Stream.of("alice", "bob", "carol", "dave").skip(n).findFirst().orElse(null);
		greeting(name).statusCode(Response.Status.OK.getStatusCode()).body(is("Hello " + name + "!"));
	}

	private static ValidatableResponse hello() {
		return hello(null);
	}

	private static ValidatableResponse hello(String language) {
		RequestSpecification request = given().log().all();
		if (language != null) {
			request = request.header(ACCEPT_LANGUAGE, language);
		}
		return request.when().get("/hello").then().log().all();
	}

	private static ValidatableResponse greeting(String name) {
		RequestSpecification request = given().log().all();
		return request.pathParam("name", name)
				.when().get("hello/greeting/{name}")
				.then().log().all();
	}
}
