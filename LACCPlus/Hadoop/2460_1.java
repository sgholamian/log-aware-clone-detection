//,temp,AHSWebServices.java,381,474,temp,AHSWebServices.java,249,336
//,3
public class xxx {
  @GET
  @Path("/containerlogs/{containerid}/{filename}")
  @Produces({ MediaType.TEXT_PLAIN + "; " + JettyUtils.UTF_8 })
  @Public
  @Unstable
  public Response getLogs(@Context HttpServletRequest req,
      @Context HttpServletResponse res,
      @PathParam(YarnWebServiceParams.CONTAINER_ID) String containerIdStr,
      @PathParam(YarnWebServiceParams.CONTAINER_LOG_FILE_NAME) String filename,
      @QueryParam(YarnWebServiceParams.RESPONSE_CONTENT_FORMAT) String format,
      @QueryParam(YarnWebServiceParams.RESPONSE_CONTENT_SIZE) String size,
      @QueryParam(YarnWebServiceParams.NM_ID) String nmId,
      @QueryParam(YarnWebServiceParams.REDIRECTED_FROM_NODE)
      @DefaultValue("false") boolean redirected_from_node) {
    initForReadableEndpoints(res);
    ContainerId containerId;
    try {
      containerId = ContainerId.fromString(containerIdStr);
    } catch (IllegalArgumentException ex) {
      return createBadResponse(Status.NOT_FOUND,
          "Invalid ContainerId: " + containerIdStr);
    }

    final long length = parseLongParam(size);

    ApplicationId appId = containerId.getApplicationAttemptId()
        .getApplicationId();
    AppInfo appInfo;
    try {
      appInfo = super.getApp(req, res, appId.toString());
    } catch (Exception ex) {
      // directly find logs from HDFS.
      return sendStreamOutputResponse(appId, null, null, containerIdStr,
          filename, format, length, false);
    }
    String appOwner = appInfo.getUser();
    if (isFinishedState(appInfo.getAppState())) {
      // directly find logs from HDFS.
      return sendStreamOutputResponse(appId, appOwner, null, containerIdStr,
          filename, format, length, false);
    }

    if (isRunningState(appInfo.getAppState())) {
      String nodeHttpAddress = null;
      if (nmId != null && !nmId.isEmpty()) {
        try {
          nodeHttpAddress = getNMWebAddressFromRM(conf, nmId);
        } catch (Exception ex) {
          if (LOG.isDebugEnabled()) {
            LOG.debug(ex.getMessage());
          }
        }
      }
      if (nodeHttpAddress == null || nodeHttpAddress.isEmpty()) {
        ContainerInfo containerInfo;
        try {
          containerInfo = super.getContainer(
              req, res, appId.toString(),
              containerId.getApplicationAttemptId().toString(),
              containerId.toString());
        } catch (Exception ex) {
          // output the aggregated logs
          return sendStreamOutputResponse(appId, appOwner, null,
              containerIdStr, filename, format, length, true);
        }
        nodeHttpAddress = containerInfo.getNodeHttpAddress();
        // make sure nodeHttpAddress is not null and not empty. Otherwise,
        // we would only get aggregated logs instead of re-directing the
        // request.
        // If this is the redirect request from NM, we should not re-direct the
        // request back. Simply output the aggregated logs.
        if (nodeHttpAddress == null || nodeHttpAddress.isEmpty()
            || redirected_from_node) {
          // output the aggregated logs
          return sendStreamOutputResponse(appId, appOwner, null,
              containerIdStr, filename, format, length, true);
        }
      }
      String uri = "/" + containerId.toString() + "/logs/" + filename;
      String resURI = JOINER.join(getAbsoluteNMWebAddress(nodeHttpAddress),
          NM_DOWNLOAD_URI_STR, uri);
      String query = req.getQueryString();
      if (query != null && !query.isEmpty()) {
        resURI += "?" + query;
      }
      ResponseBuilder response = Response.status(
          HttpServletResponse.SC_TEMPORARY_REDIRECT);
      response.header("Location", resURI);
      return response.build();
    } else {
      return createBadResponse(Status.NOT_FOUND,
          "The application is not at Running or Finished State.");
    }
  }

};