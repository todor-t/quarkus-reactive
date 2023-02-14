package com.cycos.learn.quarkus.reactive;

import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingResource {
	private static final Logger LOG = System.getLogger(GreetingResource.class.getName());
	@Inject
	GreetingService service;
	@Inject
	RequestLocale requestLocale;

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String hello() {
		logRequestLocale();
		final Locale locale = requestLocale.getLocale();
		String reply;
		if (Objects.equals(locale, Locale.ENGLISH)) {
			reply = "Hello from RESTEasy Reactive";
		} else if (Objects.equals(locale, Locale.GERMAN)) {
			reply = "Hallo von RESTEasy Reactive";
		} else if (Objects.equals(locale, Locale.CHINESE)) {
			reply = "来自 RESTEasy Reactive 的问候";
		} else {
			reply = "👋 RESTEasy Reactive";
		}
		return reply;
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/greeting/{name}")
	public String greeting(String name) {
		logRequestLocale();
		return service.greeting(name);
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/greeting")
	public String greeting2(@QueryParam("name") String name) {
		logRequestLocale();
		return service.greeting(name);
	}

	private void logRequestLocale() {
		LOG.log(Level.INFO, () -> "requst locale: " + requestLocale.getLocale());
	}
}
