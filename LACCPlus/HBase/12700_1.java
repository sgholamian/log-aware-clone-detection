//,temp,ScannerResource.java,144,153,temp,ScannerResource.java,133,142
//,2
public class xxx {
  @POST
  @Consumes({MIMETYPE_XML, MIMETYPE_JSON, MIMETYPE_PROTOBUF,
    MIMETYPE_PROTOBUF_IETF})
  public Response post(final ScannerModel model,
      final @Context UriInfo uriInfo) {
    if (LOG.isTraceEnabled()) {
      LOG.trace("POST " + uriInfo.getAbsolutePath());
    }
    return update(model, false, uriInfo);
  }

};