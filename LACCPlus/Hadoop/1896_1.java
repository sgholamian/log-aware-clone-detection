//,temp,TimelineWebServices.java,243,270,temp,TimelineWebServices.java,217,237
//,3
public class xxx {
  @PUT
  @Path("/domain")
  @Consumes({ MediaType.APPLICATION_JSON /* , MediaType.APPLICATION_XML */})
  public TimelinePutResponse putDomain(
      @Context HttpServletRequest req,
      @Context HttpServletResponse res,
      TimelineDomain domain) {
    init(res);
    UserGroupInformation callerUGI = getUser(req);
    if (callerUGI == null) {
      String msg = "The owner of the posted timeline domain is not set";
      LOG.error(msg);
      throw new ForbiddenException(msg);
    }
    domain.setOwner(callerUGI.getShortUserName());
    try {
      timelineDataManager.putDomain(domain, callerUGI);
    } catch (YarnException e) {
      // The user doesn't have the access to override the existing domain.
      LOG.error(e.getMessage(), e);
      throw new ForbiddenException(e);
    } catch (IOException e) {
      LOG.error("Error putting domain", e);
      throw new WebApplicationException(e,
          Response.Status.INTERNAL_SERVER_ERROR);
    }
    return new TimelinePutResponse();
  }

};