//,temp,AHSWebServices.java,381,474,temp,AHSWebServices.java,249,336
//,3
public class xxx {
  @GET
  @Path("/containers/{containerid}/logs")
  @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  public Response getContainerLogsInfo(
      @Context HttpServletRequest req,
      @Context HttpServletResponse res,
      @PathParam(YarnWebServiceParams.CONTAINER_ID) String containerIdStr,
      @QueryParam(YarnWebServiceParams.NM_ID) String nmId,
      @QueryParam(YarnWebServiceParams.REDIRECTED_FROM_NODE)
      @DefaultValue("false") boolean redirected_from_node) {
    ContainerId containerId = null;
    initForReadableEndpoints(res);
    try {
      containerId = ContainerId.fromString(containerIdStr);
    } catch (IllegalArgumentException e) {
      throw new BadRequestException("invalid container id, " + containerIdStr);
    }

    ApplicationId appId = containerId.getApplicationAttemptId()
        .getApplicationId();
    AppInfo appInfo;
    try {
      appInfo = super.getApp(req, res, appId.toString());
    } catch (Exception ex) {
      // directly find logs from HDFS.
      return getContainerLogMeta(appId, null, null, containerIdStr, false);
    }
    // if the application finishes, directly find logs
    // from HDFS.
    if (isFinishedState(appInfo.getAppState())) {
      return getContainerLogMeta(appId, null, null,
          containerIdStr, false);
    }
    if (isRunningState(appInfo.getAppState())) {
      String appOwner = appInfo.getUser();
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
          // return log meta for the aggregated logs if exists.
          // It will also return empty log meta for the local logs.
          return getContainerLogMeta(appId, appOwner, null,
              containerIdStr, true);
        }
        nodeHttpAddress = containerInfo.getNodeHttpAddress();
        // make sure nodeHttpAddress is not null and not empty. Otherwise,
        // we would only get log meta for aggregated logs instead of
        // re-directing the request
        if (nodeHttpAddress == null || nodeHttpAddress.isEmpty()
            || redirected_from_node) {
          // return log meta for the aggregated logs if exists.
          // It will also return empty log meta for the local logs.
          // If this is the redirect request from NM, we should not
          // re-direct the request back. Simply output the aggregated log meta.
          return getContainerLogMeta(appId, appOwner, null,
              containerIdStr, true);
        }
      }
      String uri = "/" + containerId.toString() + "/logs";
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
      throw new NotFoundException(
          "The application is not at Running or Finished State.");
    }
  }

};