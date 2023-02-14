package com.cycos.learn.quarkus.reactive;

import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author todor
 */
@ApplicationScoped
public class GreetingService {
	public String greeting(String name) {
		return "Hello " + name + "!";
	}
}
