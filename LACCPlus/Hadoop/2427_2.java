//,temp,TimelineWebServices.java,147,182,temp,TimelineWebServices.java,103,142
//,3
public class xxx {
  @GET
  @Path("/{entityType}")
  @Produces({ MediaType.APPLICATION_JSON + "; " + JettyUtils.UTF_8
      /* , MediaType.APPLICATION_XML */})
  public TimelineEntities getEntities(
      @Context HttpServletRequest req,
      @Context HttpServletResponse res,
      @PathParam("entityType") String entityType,
      @QueryParam("primaryFilter") String primaryFilter,
      @QueryParam("secondaryFilter") String secondaryFilter,
      @QueryParam("windowStart") String windowStart,
      @QueryParam("windowEnd") String windowEnd,
      @QueryParam("fromId") String fromId,
      @QueryParam("fromTs") String fromTs,
      @QueryParam("limit") String limit,
      @QueryParam("fields") String fields) {
    init(res);
    try {
      return timelineDataManager.getEntities(
          parseStr(entityType),
          parsePairStr(primaryFilter, ":"),
          parsePairsStr(secondaryFilter, ",", ":"),
          parseLongStr(windowStart),
          parseLongStr(windowEnd),
          parseStr(fromId),
          parseLongStr(fromTs),
          parseLongStr(limit),
          parseFieldsStr(fields, ","),
          getUser(req));
    } catch (NumberFormatException e) {
      throw new BadRequestException(
        "windowStart, windowEnd, fromTs or limit is not a numeric value: " + e);
    } catch (IllegalArgumentException e) {
      throw new BadRequestException("requested invalid field: " + e);
    } catch (Exception e) {
      LOG.error("Error getting entities", e);
      throw new WebApplicationException(e,
          Response.Status.INTERNAL_SERVER_ERROR);
    }
  }

};