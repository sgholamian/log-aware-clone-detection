//,temp,TimelineWebServices.java,306,331,temp,TimelineWebServices.java,275,301
//,3
public class xxx {
  @GET
  @Path("/domain/{domainId}")
  @Produces({ MediaType.APPLICATION_JSON /* , MediaType.APPLICATION_XML */})
  public TimelineDomain getDomain(
      @Context HttpServletRequest req,
      @Context HttpServletResponse res,
      @PathParam("domainId") String domainId) {
    init(res);
    domainId = parseStr(domainId);
    if (domainId == null || domainId.length() == 0) {
      throw new BadRequestException("Domain ID is not specified.");
    }
    TimelineDomain domain = null;
    try {
      domain = timelineDataManager.getDomain(
          parseStr(domainId), getUser(req));
    } catch (Exception e) {
      LOG.error("Error getting domain", e);
      throw new WebApplicationException(e,
          Response.Status.INTERNAL_SERVER_ERROR);
    }
    if (domain == null) {
      throw new NotFoundException("Timeline domain ["
          + domainId + "] is not found");
    }
    return domain;
  }

};