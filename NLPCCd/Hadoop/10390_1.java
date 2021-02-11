//,temp,sample_2963.java,2,8,temp,sample_2935.java,2,8
//,3
public class xxx {
public Set<TimelineEntity> getFlowRunApps( @Context HttpServletRequest req, @Context HttpServletRequest req, @Context HttpServletResponse res, @Context HttpServletResponse res, @PathParam("uid") String uId, @PathParam("uid") String uId, @QueryParam("limit") String limit, @QueryParam("limit") String limit, @QueryParam("createdtimestart") String createdTimeStart, @QueryParam("createdtimestart") String createdTimeStart, @QueryParam("createdtimeend") String createdTimeEnd, @QueryParam("createdtimeend") String createdTimeEnd, @QueryParam("relatesto") String relatesTo, @QueryParam("relatesto") String relatesTo, @QueryParam("isrelatedto") String isRelatedTo, @QueryParam("isrelatedto") String isRelatedTo, @QueryParam("infofilters") String infofilters, @QueryParam("infofilters") String infofilters, @QueryParam("conffilters") String conffilters, @QueryParam("conffilters") String conffilters, @QueryParam("metricfilters") String metricfilters, @QueryParam("metricfilters") String metricfilters, @QueryParam("eventfilters") String eventfilters, @QueryParam("eventfilters") String eventfilters, @QueryParam("confstoretrieve") String confsToRetrieve, @QueryParam("confstoretrieve") String confsToRetrieve, @QueryParam("metricstoretrieve") String metricsToRetrieve, @QueryParam("metricstoretrieve") String metricsToRetrieve, @QueryParam("fields") String fields, @QueryParam("fields") String fields, @QueryParam("metricslimit") String metricsLimit, @QueryParam("metricslimit") String metricsLimit, @QueryParam("metricstimestart") String metricsTimeStart, @QueryParam("metricstimestart") String metricsTimeStart, @QueryParam("metricstimeend") String metricsTimeEnd, @QueryParam("metricstimeend") String metricsTimeEnd, @QueryParam("fromid") String fromId) {
String url = req.getRequestURI() + (req.getQueryString() == null ? "" : QUERY_STRING_SEP + req.getQueryString());
UserGroupInformation callerUGI = TimelineReaderWebServicesUtils.getUser(req);


log.info("received url from user");
}

};