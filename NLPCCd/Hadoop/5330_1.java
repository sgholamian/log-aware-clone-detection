//,temp,sample_2969.java,2,8,temp,sample_2951.java,2,8
//,3
public class xxx {
public Set<TimelineEntity> getSubAppEntities(@Context HttpServletRequest req, @Context HttpServletResponse res, @Context HttpServletResponse res, @PathParam("clusterid") String clusterId, @PathParam("clusterid") String clusterId, @PathParam("userid") String userId, @PathParam("userid") String userId, @PathParam("entitytype") String entityType, @PathParam("entitytype") String entityType, @PathParam("entityid") String entityId, @PathParam("entityid") String entityId, @QueryParam("confstoretrieve") String confsToRetrieve, @QueryParam("confstoretrieve") String confsToRetrieve, @QueryParam("metricstoretrieve") String metricsToRetrieve, @QueryParam("metricstoretrieve") String metricsToRetrieve, @QueryParam("fields") String fields, @QueryParam("fields") String fields, @QueryParam("metricslimit") String metricsLimit, @QueryParam("metricslimit") String metricsLimit, @QueryParam("metricstimestart") String metricsTimeStart, @QueryParam("metricstimestart") String metricsTimeStart, @QueryParam("metricstimeend") String metricsTimeEnd, @QueryParam("metricstimeend") String metricsTimeEnd, @QueryParam("entityidprefix") String entityIdPrefix) {
String url = req.getRequestURI() + (req.getQueryString() == null ? "" : QUERY_STRING_SEP + req.getQueryString());
UserGroupInformation callerUGI = TimelineReaderWebServicesUtils.getUser(req);


log.info("received url from user");
}

};