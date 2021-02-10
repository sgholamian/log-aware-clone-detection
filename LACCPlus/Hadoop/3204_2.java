//,temp,ApiServer.java,528,570,temp,ApiServer.java,301,335
//,3
public class xxx {
  @PUT
  @Path(COMPONENTS_PATH)
  @Consumes({MediaType.APPLICATION_JSON})
  @Produces({RestApiConstants.MEDIA_TYPE_JSON_UTF8, MediaType.TEXT_PLAIN})
  public Response updateComponents(@Context HttpServletRequest request,
      @PathParam(SERVICE_NAME) String serviceName,
      List<Component> requestComponents) {

    try {
      if (requestComponents == null || requestComponents.isEmpty()) {
        throw new YarnException("No components provided.");
      }
      UserGroupInformation ugi = getProxyUser(request);
      Set<String> compNamesToUpgrade = new HashSet<>();
      requestComponents.forEach(reqComp -> {
        if (reqComp.getState() != null &&
            reqComp.getState().equals(ComponentState.UPGRADING)) {
          compNamesToUpgrade.add(reqComp.getName());
        }
      });
      LOG.info("PUT: upgrade components {} for service {} " +
          "user = {}", compNamesToUpgrade, serviceName, ugi);
      return processComponentsUpgrade(ugi, serviceName, compNamesToUpgrade);
    } catch (AccessControlException e) {
      return formatResponse(Response.Status.FORBIDDEN, e.getMessage());
    } catch (YarnException e) {
      return formatResponse(Response.Status.BAD_REQUEST, e.getMessage());
    } catch (IOException | InterruptedException e) {
      return formatResponse(Response.Status.INTERNAL_SERVER_ERROR,
          e.getMessage());
    } catch (UndeclaredThrowableException e) {
      return formatResponse(Response.Status.INTERNAL_SERVER_ERROR,
          e.getCause().getMessage());
    }
  }

};