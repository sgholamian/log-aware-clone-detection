//,temp,TimelineReaderWebServices.java,3246,3280,temp,TimelineReaderWebServices.java,1296,1349
//,3
public class xxx {
  @GET
  @Path("/clusters/{clusterid}/users/{userid}/flows/{flowname}/runs/")
  @Produces(MediaType.APPLICATION_JSON + "; " + JettyUtils.UTF_8)
  public Set<TimelineEntity> getFlowRuns(
      @Context HttpServletRequest req,
      @Context HttpServletResponse res,
      @PathParam("clusterid") String clusterId,
      @PathParam("userid") String userId,
      @PathParam("flowname") String flowName,
      @QueryParam("limit") String limit,
      @QueryParam("createdtimestart") String createdTimeStart,
      @QueryParam("createdtimeend") String createdTimeEnd,
      @QueryParam("metricstoretrieve") String metricsToRetrieve,
      @QueryParam("fields") String fields,
      @QueryParam("fromid") String fromId) {
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
    Set<TimelineEntity> entities = null;
    try {
      TimelineReaderContext timelineReaderContext = TimelineReaderWebServicesUtils
          .createTimelineReaderContext(clusterId, userId, flowName, null,
              null, TimelineEntityType.YARN_FLOW_RUN.toString(), null,
              null);
      // TODO to be removed or modified once ACL story is played
      checkAccess(timelineReaderManager, callerUGI,
          timelineReaderContext.getUserId());

      entities = timelineReaderManager.getEntities(timelineReaderContext,
          TimelineReaderWebServicesUtils
              .createTimelineEntityFilters(limit, createdTimeStart,
                  createdTimeEnd, null, null, null, null, null, null, fromId),
          TimelineReaderWebServicesUtils
              .createTimelineDataToRetrieve(null, metricsToRetrieve, fields,
                  null, null, null));
    } catch (Exception e) {
      handleException(e, url, startTime,
          "createdTime start/end or limit or fromId");
    }
    long endTime = Time.monotonicNow();
    if (entities == null) {
      entities = Collections.emptySet();
    }
    LOG.info("Processed URL " + url +
        " (Took " + (endTime - startTime) + " ms.)");
    return entities;
  }

};