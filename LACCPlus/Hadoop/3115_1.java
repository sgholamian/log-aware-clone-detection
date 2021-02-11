//,temp,TimelineCollectorWebService.java,203,245,temp,TimelineCollectorWebService.java,143,193
//,3
public class xxx {
  @PUT
  @Path("/domain")
  @Consumes({ MediaType.APPLICATION_JSON /* , MediaType.APPLICATION_XML */ })
  public Response putDomain(
      @Context HttpServletRequest req,
      @Context HttpServletResponse res,
      @QueryParam("appid") String appId,
      TimelineDomain domain) {
    init(res);
    UserGroupInformation callerUgi = getUser(req);
    if (callerUgi == null) {
      String msg = "The owner of the posted timeline entities is not set";
      LOG.error(msg);
      throw new ForbiddenException(msg);
    }

    try {
      ApplicationId appID = parseApplicationId(appId);
      if (appID == null) {
        return Response.status(Response.Status.BAD_REQUEST).build();
      }
      NodeTimelineCollectorManager collectorManager =
          (NodeTimelineCollectorManager) context.getAttribute(
              NodeTimelineCollectorManager.COLLECTOR_MANAGER_ATTR_KEY);
      TimelineCollector collector = collectorManager.get(appID);
      if (collector == null) {
        LOG.error("Application: " + appId + " is not found");
        throw new NotFoundException("Application: " + appId + " is not found");
      }

      domain.setOwner(callerUgi.getShortUserName());
      collector.putDomain(domain, callerUgi);

      return Response.ok().build();
    } catch (NotFoundException e) {
      throw new WebApplicationException(e,
          Response.Status.INTERNAL_SERVER_ERROR);
    } catch (IOException e) {
      LOG.error("Error putting entities", e);
      throw new WebApplicationException(e,
          Response.Status.INTERNAL_SERVER_ERROR);
    }
  }

};