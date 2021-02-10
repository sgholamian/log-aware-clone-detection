//,temp,TimelineWebServices.java,243,270,temp,TimelineWebServices.java,217,237
//,3
public class xxx {
  @POST
  @Consumes({ MediaType.APPLICATION_JSON /* , MediaType.APPLICATION_XML */})
  public TimelinePutResponse postEntities(
      @Context HttpServletRequest req,
      @Context HttpServletResponse res,
      TimelineEntities entities) {
    init(res);
    UserGroupInformation callerUGI = getUser(req);
    if (callerUGI == null) {
      String msg = "The owner of the posted timeline entities is not set";
      LOG.error(msg);
      throw new ForbiddenException(msg);
    }
    try {
      return timelineDataManager.postEntities(entities, callerUGI);
    } catch (Exception e) {
      LOG.error("Error putting entities", e);
      throw new WebApplicationException(e,
          Response.Status.INTERNAL_SERVER_ERROR);
    }
  }

};