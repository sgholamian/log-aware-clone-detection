//,temp,ScannerResource.java,133,142,temp,SchemaResource.java,216,226
//,3
public class xxx {
  @POST
  @Consumes({MIMETYPE_XML, MIMETYPE_JSON, MIMETYPE_PROTOBUF,
    MIMETYPE_PROTOBUF_IETF})
  public Response post(final TableSchemaModel model,
      final @Context UriInfo uriInfo) {
    if (LOG.isTraceEnabled()) {
      LOG.trace("PUT " + uriInfo.getAbsolutePath());
    }
    servlet.getMetrics().incrementRequests(1);
    return update(model, false, uriInfo);
  }

};