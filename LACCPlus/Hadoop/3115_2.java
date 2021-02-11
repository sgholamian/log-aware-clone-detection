//,temp,TimelineCollectorWebService.java,203,245,temp,TimelineCollectorWebService.java,143,193
//,3
public class xxx {
  @PUT
  @Path("/entities")
  @Consumes({ MediaType.APPLICATION_JSON /* , MediaType.APPLICATION_XML */})
  public Response putEntities(
      @Context HttpServletRequest req,
      @Context HttpServletResponse res,
      @QueryParam("async") String async,
      @QueryParam("subappwrite") String isSubAppEntities,
      @QueryParam("appid") String appId,
      TimelineEntities entities) {
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
        LOG.error("Application: "+ appId + " is not found");
        throw new NotFoundException("Application: "+ appId + " is not found");
      }

      boolean isAsync = async != null && async.trim().equalsIgnoreCase("true");
      if (isAsync) {
        collector.putEntitiesAsync(processTimelineEntities(entities, appId,
            Boolean.valueOf(isSubAppEntities)), callerUgi);
      } else {
        collector.putEntities(processTimelineEntities(entities, appId,
            Boolean.valueOf(isSubAppEntities)), callerUgi);
      }

      return Response.ok().build();
    } catch (NotFoundException | ForbiddenException e) {
      throw new WebApplicationException(e,
          Response.Status.INTERNAL_SERVER_ERROR);
    } catch (IOException e) {
      LOG.error("Error putting entities", e);
      throw new WebApplicationException(e,
          Response.Status.INTERNAL_SERVER_ERROR);
    }
  }

};