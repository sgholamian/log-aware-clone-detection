//,temp,SchemaResource.java,216,226,temp,NamespacesInstanceResource.java,134,143
//,2
public class xxx {
  @PUT
  @Consumes({MIMETYPE_XML, MIMETYPE_JSON, MIMETYPE_PROTOBUF,
    MIMETYPE_PROTOBUF_IETF})
  public Response put(final NamespacesInstanceModel model, final @Context UriInfo uriInfo) {
    if (LOG.isTraceEnabled()) {
      LOG.trace("PUT " + uriInfo.getAbsolutePath());
    }
    servlet.getMetrics().incrementRequests(1);
    return processUpdate(model, true, uriInfo);
  }

};