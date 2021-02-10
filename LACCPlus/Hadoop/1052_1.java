//,temp,TimelineWebServices.java,181,211,temp,TimelineWebServices.java,102,140
//,3
public class xxx {
  @GET
  @Path("/{entityType}/events")
  @Produces({ MediaType.APPLICATION_JSON /* , MediaType.APPLICATION_XML */})
  public TimelineEvents getEvents(
      @Context HttpServletRequest req,
      @Context HttpServletResponse res,
      @PathParam("entityType") String entityType,
      @QueryParam("entityId") String entityId,
      @QueryParam("eventType") String eventType,
      @QueryParam("windowStart") String windowStart,
      @QueryParam("windowEnd") String windowEnd,
      @QueryParam("limit") String limit) {
    init(res);
    try {
      return timelineDataManager.getEvents(
          parseStr(entityType),
          parseArrayStr(entityId, ","),
          parseArrayStr(eventType, ","),
          parseLongStr(windowStart),
          parseLongStr(windowEnd),
          parseLongStr(limit),
          getUser(req));
    } catch (NumberFormatException e) {
      throw new BadRequestException(
          "windowStart, windowEnd or limit is not a numeric value.");
    } catch (Exception e) {
      LOG.error("Error getting entity timelines", e);
      throw new WebApplicationException(e,
          Response.Status.INTERNAL_SERVER_ERROR);
    }
  }

};