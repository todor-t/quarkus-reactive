package com.cycos.learn.quarkus.reactive;

import static io.vertx.core.http.HttpHeaders.ACCEPT_LANGUAGE;

import java.util.Locale;
import javax.enterprise.context.RequestScoped;

import io.vertx.core.http.HttpServerRequest;

/**
 *
 * @author todor
 */
@RequestScoped
public class RequestLocale {
  private final Locale locale;

  public RequestLocale(HttpServerRequest request) {
    String acceptLanguage = request.getHeader(ACCEPT_LANGUAGE);
    this.locale = acceptLanguage == null ? Locale.ENGLISH : new Locale(acceptLanguage);
  }

  public Locale getLocale() {
    return locale;
  }
}
