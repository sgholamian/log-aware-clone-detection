//,temp,TimelineReaderWebServices.java,3394,3440,temp,TimelineReaderWebServices.java,1440,1486
//,3
public class xxx {
  @GET
  @Path("/clusters/{clusterid}/users/{userid}/entities/{entitytype}/{entityid}")
  @Produces(MediaType.APPLICATION_JSON + "; " + JettyUtils.UTF_8)
  public Set<TimelineEntity> getSubAppEntities(@Context HttpServletRequest req,
      @Context HttpServletResponse res,
      @PathParam("clusterid") String clusterId,
      @PathParam("userid") String userId,
      @PathParam("entitytype") String entityType,
      @PathParam("entityid") String entityId,
      @QueryParam("confstoretrieve") String confsToRetrieve,
      @QueryParam("metricstoretrieve") String metricsToRetrieve,
      @QueryParam("fields") String fields,
      @QueryParam("metricslimit") String metricsLimit,
      @QueryParam("metricstimestart") String metricsTimeStart,
      @QueryParam("metricstimeend") String metricsTimeEnd,
      @QueryParam("entityidprefix") String entityIdPrefix) {
    String url = req.getRequestURI() + (req.getQueryString() == null ? ""
        : QUERY_STRING_SEP + req.getQueryString());
    UserGroupInformation callerUGI =
        TimelineReaderWebServicesUtils.getUser(req);
    LOG.info("Received URL " + url + " from user "
        + TimelineReaderWebServicesUtils.getUserName(callerUGI));
    long startTime = Time.monotonicNow();
    init(res);
    TimelineReaderManager timelineReaderManager = getTimelineReaderManager();
    Set<TimelineEntity> entities = null;
    try {
      TimelineReaderContext context = TimelineReaderWebServicesUtils
          .createTimelineReaderContext(clusterId, null, null, null, null,
              entityType, entityIdPrefix, entityId, userId);
      entities = timelineReaderManager.getEntities(context,
          new TimelineEntityFilters.Builder().build(),
          TimelineReaderWebServicesUtils.createTimelineDataToRetrieve(
              confsToRetrieve, metricsToRetrieve, fields, metricsLimit,
              metricsTimeStart, metricsTimeEnd));
      checkAccessForSubAppEntities(entities,callerUGI);
    } catch (Exception e) {
      handleException(e, url, startTime, "");
    }
    long endTime = Time.monotonicNow();
    if (entities == null) {
      entities = Collections.emptySet();
    }
    LOG.info(
        "Processed URL " + url + " (Took " + (endTime - startTime) + " ms.)");
    return entities;
  }

};