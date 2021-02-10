//,temp,TimelineReaderWebServices.java,1828,1890,temp,TimelineReaderWebServices.java,864,917
//,3
public class xxx {
  @GET
  @Path("/clusters/{clusterid}/apps/{appid}/entities/{entitytype}/{entityid}/")
  @Produces(MediaType.APPLICATION_JSON + "; " + JettyUtils.UTF_8)
  public TimelineEntity getEntity(
      @Context HttpServletRequest req,
      @Context HttpServletResponse res,
      @PathParam("clusterid") String clusterId,
      @PathParam("appid") String appId,
      @PathParam("entitytype") String entityType,
      @PathParam("entityid") String entityId,
      @QueryParam("userid") String userId,
      @QueryParam("flowname") String flowName,
      @QueryParam("flowrunid") String flowRunId,
      @QueryParam("confstoretrieve") String confsToRetrieve,
      @QueryParam("metricstoretrieve") String metricsToRetrieve,
      @QueryParam("fields") String fields,
      @QueryParam("metricslimit") String metricsLimit,
      @QueryParam("metricstimestart") String metricsTimeStart,
      @QueryParam("metricstimeend") String metricsTimeEnd,
      @QueryParam("entityidprefix") String entityIdPrefix) {
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
              clusterId, userId, flowName, flowRunId, appId, entityType,
              entityIdPrefix, entityId),
          TimelineReaderWebServicesUtils.createTimelineDataToRetrieve(
          confsToRetrieve, metricsToRetrieve, fields, metricsLimit,
          metricsTimeStart, metricsTimeEnd));
      checkAccessForGenericEntity(entity, callerUGI);
    } catch (Exception e) {
      handleException(e, url, startTime, "flowrunid");
    }
    long endTime = Time.monotonicNow();
    if (entity == null) {
      LOG.info("Processed URL " + url + " but entity not found" + " (Took " +
          (endTime - startTime) + " ms.)");
      throw new NotFoundException("Timeline entity {id: " + entityId +
          ", type: " + entityType + " } is not found");
    }
    LOG.info("Processed URL " + url +
        " (Took " + (endTime - startTime) + " ms.)");
    return entity;
  }

};