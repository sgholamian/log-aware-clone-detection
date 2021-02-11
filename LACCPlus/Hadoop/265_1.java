//,temp,TimelineWebServices.java,306,331,temp,TimelineWebServices.java,217,237
//,3
public class xxx {
  @GET
  @Path("/domain")
  @Produces({ MediaType.APPLICATION_JSON /* , MediaType.APPLICATION_XML */})
  public TimelineDomains getDomains(
      @Context HttpServletRequest req,
      @Context HttpServletResponse res,
      @QueryParam("owner") String owner) {
    init(res);
    owner = parseStr(owner);
    UserGroupInformation callerUGI = getUser(req);
    if (owner == null || owner.length() == 0) {
      if (callerUGI == null) {
        throw new BadRequestException("Domain owner is not specified.");
      } else {
        // By default it's going to list the caller's domains
        owner = callerUGI.getShortUserName();
      }
    }
    try {
      return timelineDataManager.getDomains(owner, callerUGI);
    } catch (Exception e) {
      LOG.error("Error getting domains", e);
      throw new WebApplicationException(e,
          Response.Status.INTERNAL_SERVER_ERROR);
    }
  }

};