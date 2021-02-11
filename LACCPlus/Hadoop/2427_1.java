//,temp,TimelineWebServices.java,147,182,temp,TimelineWebServices.java,103,142
//,3
public class xxx {
  @GET
  @Path("/{entityType}/{entityId}")
  @Produces({ MediaType.APPLICATION_JSON + "; " + JettyUtils.UTF_8
      /* , MediaType.APPLICATION_XML */})
  public TimelineEntity getEntity(
      @Context HttpServletRequest req,
      @Context HttpServletResponse res,
      @PathParam("entityType") String entityType,
      @PathParam("entityId") String entityId,
      @QueryParam("fields") String fields) {
    init(res);
    TimelineEntity entity = null;
    try {
      entity = timelineDataManager.getEntity(
          parseStr(entityType),
          parseStr(entityId),
          parseFieldsStr(fields, ","),
          getUser(req));
    } catch (YarnException e) {
      // The user doesn't have the access to override the existing domain.
      LOG.info(e.getMessage(), e);
      throw new ForbiddenException(e);
    } catch (IllegalArgumentException e) {
      throw new BadRequestException(e);
    } catch (Exception e) {
      LOG.error("Error getting entity", e);
      throw new WebApplicationException(e,
          Response.Status.INTERNAL_SERVER_ERROR);
    }
    if (entity == null) {
      throw new NotFoundException("Timeline entity "
          + new EntityIdentifier(parseStr(entityId), parseStr(entityType))
          + " is not found");
    }
    return entity;
  }

};