//,temp,TimelineReaderWebServices.java,1440,1486,temp,TimelineReaderWebServices.java,679,727
//,3
public class xxx {
  @GET
  @Path("/clusters/{clusterid}/flows/")
  @Produces(MediaType.APPLICATION_JSON + "; " + JettyUtils.UTF_8)
  public Set<TimelineEntity> getFlows(
      @Context HttpServletRequest req,
      @Context HttpServletResponse res,
      @PathParam("clusterid") String clusterId,
      @QueryParam("limit") String limit,
      @QueryParam("daterange") String dateRange,
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
      DateRange range = parseDateRange(dateRange);
      TimelineEntityFilters entityFilters =
          TimelineReaderWebServicesUtils.createTimelineEntityFilters(
              limit, range.dateStart, range.dateEnd,
              null, null, null, null, null, null, fromId);
      entities = timelineReaderManager.getEntities(
          TimelineReaderWebServicesUtils.createTimelineReaderContext(
          clusterId, null, null, null, null,
              TimelineEntityType.YARN_FLOW_ACTIVITY.toString(), null, null),
          entityFilters, TimelineReaderWebServicesUtils.
              createTimelineDataToRetrieve(null, null, null, null, null, null));
    } catch (Exception e) {
      handleException(e, url, startTime, "limit");
    }
    long endTime = Time.monotonicNow();
    if (entities == null) {
      entities = Collections.emptySet();
    } else {
      checkAccess(timelineReaderManager, callerUGI, entities,
          FlowActivityEntity.USER_INFO_KEY, true);
    }
    LOG.info("Processed URL " + url +
        " (Took " + (endTime - startTime) + " ms.)");
    return entities;
  }

};