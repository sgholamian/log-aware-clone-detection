//,temp,TimelineReaderWebServices.java,3394,3440,temp,TimelineReaderWebServices.java,3313,3373
//,3
public class xxx {
  @GET
  @Path("/clusters/{clusterid}/users/{userid}/entities/{entitytype}")
  @Produces(MediaType.APPLICATION_JSON + "; " + JettyUtils.UTF_8)
  public Set<TimelineEntity> getSubAppEntities(
      @Context HttpServletRequest req,
      @Context HttpServletResponse res,
      @PathParam("clusterid") String clusterId,
      @PathParam("userid") String userId,
      @PathParam("entitytype") String entityType,
      @QueryParam("limit") String limit,
      @QueryParam("createdtimestart") String createdTimeStart,
      @QueryParam("createdtimeend") String createdTimeEnd,
      @QueryParam("relatesto") String relatesTo,
      @QueryParam("isrelatedto") String isRelatedTo,
      @QueryParam("infofilters") String infofilters,
      @QueryParam("conffilters") String conffilters,
      @QueryParam("metricfilters") String metricfilters,
      @QueryParam("eventfilters") String eventfilters,
      @QueryParam("confstoretrieve") String confsToRetrieve,
      @QueryParam("metricstoretrieve") String metricsToRetrieve,
      @QueryParam("fields") String fields,
      @QueryParam("metricslimit") String metricsLimit,
      @QueryParam("metricstimestart") String metricsTimeStart,
      @QueryParam("metricstimeend") String metricsTimeEnd,
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
      TimelineReaderContext context =
          TimelineReaderWebServicesUtils.createTimelineReaderContext(clusterId,
              null, null, null, null, entityType, null, null, userId);
      entities = timelineReaderManager.getEntities(context,
          TimelineReaderWebServicesUtils.createTimelineEntityFilters(
          limit, createdTimeStart, createdTimeEnd, relatesTo, isRelatedTo,
              infofilters, conffilters, metricfilters, eventfilters,
              fromId),
          TimelineReaderWebServicesUtils.createTimelineDataToRetrieve(
          confsToRetrieve, metricsToRetrieve, fields, metricsLimit,
          metricsTimeStart, metricsTimeEnd));
      checkAccessForSubAppEntities(entities,callerUGI);
    } catch (Exception e) {
      handleException(e, url, startTime,
          "createdTime start/end or limit");
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