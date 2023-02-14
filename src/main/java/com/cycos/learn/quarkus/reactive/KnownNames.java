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
public class KnownNames {
	@Produces
	public Set<String> createPersonNames() {
		Set<String> names = new LinkedHashSet<>();
		names.add("Alice");
		names.add("Bob");
		names.add("Carol");
		names.add("Dave");
		return names;
	}
}
