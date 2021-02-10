//,temp,TimelineReaderWebServices.java,864,917,temp,TimelineReaderWebServices.java,679,727
//,3
public class xxx {
  @GET
  @Path("/entity-uid/{uid}/")
  @Produces(MediaType.APPLICATION_JSON + "; " + JettyUtils.UTF_8)
  public TimelineEntity getEntity(
      @Context HttpServletRequest req,
      @Context HttpServletResponse res,
      @PathParam("uid") String uId,
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
      TimelineReaderContext context =
          TimelineUIDConverter.GENERIC_ENTITY_UID.decodeUID(uId);
      if (context == null) {
        throw new BadRequestException("Incorrect UID " +  uId);
      }
      entity = timelineReaderManager.getEntity(context,
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
      throw new NotFoundException("Timeline entity with uid: " + uId +
          "is not found");
    }
    LOG.info("Processed URL " + url +
        " (Took " + (endTime - startTime) + " ms.)");
    return entity;
  }

};