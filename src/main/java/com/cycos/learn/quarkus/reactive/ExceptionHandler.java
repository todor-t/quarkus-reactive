package com.cycos.learn.quarkus.reactive;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ExceptionHandler implements ExceptionMapper<Throwable> {
  @Override
  public Response toResponse(Throwable exception) {
    return Response.status(Status.INTERNAL_SERVER_ERROR).entity(exception.getMessage() + "\n").build();
  }
}
