//,temp,TimelineReaderWebServices.java,1143,1193,temp,TimelineReaderWebServices.java,942,985
//,3
public class xxx {
  @GET
  @Path("/run-uid/{uid}/")
  @Produces(MediaType.APPLICATION_JSON + "; " + JettyUtils.UTF_8)
  public TimelineEntity getFlowRun(
      @Context HttpServletRequest req,
      @Context HttpServletResponse res,
      @PathParam("uid") String uId,
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
      TimelineReaderContext context =
          TimelineUIDConverter.FLOWRUN_UID.decodeUID(uId);
      if (context == null) {
        throw new BadRequestException("Incorrect UID " +  uId);
      }
      // TODO to be removed or modified once ACL story is played
      checkAccess(timelineReaderManager, callerUGI, context.getUserId());
      context.setEntityType(TimelineEntityType.YARN_FLOW_RUN.toString());
      entity = timelineReaderManager.getEntity(context,
          TimelineReaderWebServicesUtils.createTimelineDataToRetrieve(
          null, metricsToRetrieve, null, null, null, null));
    } catch (Exception e) {
      handleException(e, url, startTime, "flowrunid");
    }
    long endTime = Time.monotonicNow();
    if (entity == null) {
      LOG.info("Processed URL " + url + " but flowrun not found (Took " +
          (endTime - startTime) + " ms.)");
      throw new NotFoundException("Flowrun with uid: " + uId + "is not found");
    }
    LOG.info("Processed URL " + url +
        " (Took " + (endTime - startTime) + " ms.)");
    return entity;
  }

};