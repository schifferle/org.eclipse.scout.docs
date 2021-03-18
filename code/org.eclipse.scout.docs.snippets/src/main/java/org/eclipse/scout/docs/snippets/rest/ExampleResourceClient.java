package org.eclipse.scout.docs.snippets.rest;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.scout.docs.snippets.dataobject.ExampleEntityDo;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.rest.client.IRestResourceClient;

//tag::class[]
public class ExampleResourceClient implements IRestResourceClient {

  protected static final String RESOURCE_PATH = "example";

  protected ExampleRestClientHelper helper() {
    return BEANS.get(ExampleRestClientHelper.class);
  }

  public ExampleEntityDo getExampleEntity(String id) {
    WebTarget target = helper().target(RESOURCE_PATH)
        .path("/{id}")
        .resolveTemplate("id", id);

    return target.request()
        .accept(MediaType.APPLICATION_JSON)
        .get(ExampleEntityDo.class); // <1>
  }

  public ExampleEntityDo updateExampleEntity(String id, ExampleEntityDo entity) {
    WebTarget target = helper().target(RESOURCE_PATH)
        .path("/{id}")
        .resolveTemplate("id", id);

    return target.request()
        .accept(MediaType.APPLICATION_JSON)
        .post(Entity.json(entity), ExampleEntityDo.class); // <2>
  }

  public void deleteExampleEntity(String id) {
    WebTarget target = helper().target(RESOURCE_PATH)
        .path("/{id}")
        .resolveTemplate("id", id);

    Response response = target.request().delete(); // <3>
    response.close();
  }

  public ExampleEntityDo getExampleEntityCustomExceptionHandling(String id) {
    WebTarget target = helper().target(RESOURCE_PATH, this::transformCustomException) // <4>
        .path("/{id}")
        .resolveTemplate("id", id);

    return target.request()
        .accept(MediaType.APPLICATION_JSON)
        .get(ExampleEntityDo.class);
  }

  protected RuntimeException transformCustomException(RuntimeException e, Response r) {
    if (r != null && r.hasEntity() && MediaType.TEXT_PLAIN_TYPE.equals(r.getMediaType())) {
      String message = r.readEntity(String.class);
      throw new VetoException(message);
    }
    return e;
  }
}
//end::class[]
