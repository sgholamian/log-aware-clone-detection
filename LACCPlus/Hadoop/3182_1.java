//,temp,TimelineReaderWebServices.java,3246,3280,temp,TimelineReaderWebServices.java,1531,1579
//,3
public class xxx {
  @GET
  @Path("/clusters/{clusterid}/apps/{appid}/entity-types")
  @Produces(MediaType.APPLICATION_JSON)
  public Set<String> getEntityTypes(
      @Context HttpServletRequest req,
      @Context HttpServletResponse res,
      @PathParam("clusterid") String clusterId,
      @PathParam("appid") String appId,
      @QueryParam("flowname") String flowName,
      @QueryParam("flowrunid") String flowRunId,
      @QueryParam("userid") String userId) {
    String url = req.getRequestURI() +
        (req.getQueryString() == null ? "" :
            QUERY_STRING_SEP + req.getQueryString());
    UserGroupInformation callerUGI =
        TimelineReaderWebServicesUtils.getUser(req);
    LOG.info("Received URL " + url + " from user " +
        TimelineReaderWebServicesUtils.getUserName(callerUGI));
    long startTime = Time.monotonicNow();
    init(res);
    TimelineReaderManager timelineReaderManager = getTimelineReaderManager();
    Set<String> results = null;
    try {
      results = timelineReaderManager.getEntityTypes(
          TimelineReaderWebServicesUtils.createTimelineReaderContext(
          clusterId, userId, flowName, flowRunId, appId,
              null, null, null));
    } catch (Exception e) {
      handleException(e, url, startTime, "flowrunid");
    }
    long endTime = Time.monotonicNow();
    LOG.info("Processed URL " + url +
        " (Took " + (endTime - startTime) + " ms.)");
    return results;
  }

};