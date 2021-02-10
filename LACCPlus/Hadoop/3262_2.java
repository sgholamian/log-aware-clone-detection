//,temp,TimelineReaderWebServices.java,3394,3440,temp,TimelineReaderWebServices.java,1701,1750
//,3
public class xxx {
  @GET
  @Path("/clusters/{clusterid}/apps/{appid}/")
  @Produces(MediaType.APPLICATION_JSON + "; " + JettyUtils.UTF_8)
  public TimelineEntity getApp(
      @Context HttpServletRequest req,
      @Context HttpServletResponse res,
      @PathParam("clusterid") String clusterId,
      @PathParam("appid") String appId,
      @QueryParam("flowname") String flowName,
      @QueryParam("flowrunid") String flowRunId,
      @QueryParam("userid") String userId,
      @QueryParam("confstoretrieve") String confsToRetrieve,
      @QueryParam("metricstoretrieve") String metricsToRetrieve,
      @QueryParam("fields") String fields,
      @QueryParam("metricslimit") String metricsLimit,
      @QueryParam("metricstimestart") String metricsTimeStart,
      @QueryParam("metricstimeend") String metricsTimeEnd) {
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
    TimelineEntity entity = null;
    try {
      entity = timelineReaderManager.getEntity(
          TimelineReaderWebServicesUtils.createTimelineReaderContext(
          clusterId, userId, flowName, flowRunId, appId,
              TimelineEntityType.YARN_APPLICATION.toString(), null, null),
          TimelineReaderWebServicesUtils.createTimelineDataToRetrieve(
          confsToRetrieve, metricsToRetrieve, fields, metricsLimit,
          metricsTimeStart, metricsTimeEnd));
      checkAccessForAppEntity(entity, callerUGI);
    } catch (Exception e) {
      handleException(e, url, startTime, "flowrunid");
    }
    long endTime = Time.monotonicNow();
    if (entity == null) {
      LOG.info("Processed URL " + url + " but app not found" + " (Took " +
          (endTime - startTime) + " ms.)");
      throw new NotFoundException("App " + appId + " not found");
    }
    LOG.info("Processed URL " + url +
        " (Took " + (endTime - startTime) + " ms.)");
    return entity;
  }

};