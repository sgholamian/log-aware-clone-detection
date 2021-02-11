//,temp,sample_2953.java,2,8,temp,sample_2951.java,2,8
//,3
public class xxx {
public Set<TimelineEntity> getFlowRuns( @Context HttpServletRequest req, @Context HttpServletRequest req, @Context HttpServletResponse res, @Context HttpServletResponse res, @PathParam("uid") String uId, @PathParam("uid") String uId, @QueryParam("limit") String limit, @QueryParam("limit") String limit, @QueryParam("createdtimestart") String createdTimeStart, @QueryParam("createdtimestart") String createdTimeStart, @QueryParam("createdtimeend") String createdTimeEnd, @QueryParam("createdtimeend") String createdTimeEnd, @QueryParam("metricstoretrieve") String metricsToRetrieve, @QueryParam("metricstoretrieve") String metricsToRetrieve, @QueryParam("fields") String fields, @QueryParam("fields") String fields, @QueryParam("fromid") String fromId) {
String url = req.getRequestURI() + (req.getQueryString() == null ? "" : QUERY_STRING_SEP + req.getQueryString());
UserGroupInformation callerUGI = TimelineReaderWebServicesUtils.getUser(req);


log.info("received url from user");
}

};