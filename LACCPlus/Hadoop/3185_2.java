//,temp,TimelineReaderWebServices.java,3246,3280,temp,TimelineReaderWebServices.java,1052,1101
//,3
public class xxx {
  @GET
  @Path("/clusters/{clusterid}/users/{userid}/flows/{flowname}/"
      + "runs/{flowrunid}/")
  @Produces(MediaType.APPLICATION_JSON + "; " + JettyUtils.UTF_8)
  public TimelineEntity getFlowRun(
      @Context HttpServletRequest req,
      @Context HttpServletResponse res,
      @PathParam("clusterid") String clusterId,
      @PathParam("userid") String userId,
      @PathParam("flowname") String flowName,
      @PathParam("flowrunid") String flowRunId,
      @QueryParam("metricstoretrieve") String metricsToRetrieve) {
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
      TimelineReaderContext context = TimelineReaderWebServicesUtils
          .createTimelineReaderContext(clusterId, userId, flowName, flowRunId,
              null, TimelineEntityType.YARN_FLOW_RUN.toString(), null, null);
      // TODO to be removed or modified once ACL story is played
      checkAccess(timelineReaderManager, callerUGI, context.getUserId());

      entity = timelineReaderManager.getEntity(context,
          TimelineReaderWebServicesUtils
              .createTimelineDataToRetrieve(null, metricsToRetrieve, null, null,
                  null, null));
    } catch (Exception e) {
      handleException(e, url, startTime, "flowrunid");
    }
    long endTime = Time.monotonicNow();
    if (entity == null) {
      LOG.info("Processed URL " + url + " but flowrun not found (Took " +
          (endTime - startTime) + " ms.)");
      throw new NotFoundException("Flow run {flow name: " +
          TimelineReaderWebServicesUtils.parseStr(flowName) + ", run id: " +
          TimelineReaderWebServicesUtils.parseLongStr(flowRunId) +
          " } is not found");
    }
    LOG.info("Processed URL " + url +
        " (Took " + (endTime - startTime) + " ms.)");
    return entity;
  }

};