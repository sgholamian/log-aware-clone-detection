//,temp,ApiServer.java,475,526,temp,ApiServer.java,301,335
//,3
public class xxx {
  @PUT
  @Path(COMP_INSTANCE_LONG_PATH)
  @Consumes({MediaType.APPLICATION_JSON})
  @Produces({RestApiConstants.MEDIA_TYPE_JSON_UTF8, MediaType.TEXT_PLAIN})
  public Response updateComponentInstance(@Context HttpServletRequest request,
      @PathParam(SERVICE_NAME) String serviceName,
      @PathParam(COMPONENT_NAME) String componentName,
      @PathParam(COMP_INSTANCE_NAME) String compInstanceName,
      Container reqContainer) {

    try {
      UserGroupInformation ugi = getProxyUser(request);
      LOG.info("PUT: update component instance {} for component = {}" +
              " service = {} user = {}", compInstanceName, componentName,
          serviceName, ugi);
      if (reqContainer == null) {
        throw new YarnException("No container data provided.");
      }
      Service service = getServiceFromClient(ugi, serviceName);
      Component component = service.getComponent(componentName);
      if (component == null) {
        throw new YarnException(String.format(
            "The component name in the URI path (%s) is invalid.",
            componentName));
      }

      Container liveContainer = component.getComponentInstance(
          compInstanceName);
      if (liveContainer == null) {
        throw new YarnException(String.format(
            "The component (%s) does not have a component instance (%s).",
            componentName, compInstanceName));
      }

      if (reqContainer.getState() != null
          && reqContainer.getState().equals(ContainerState.UPGRADING)) {
        return processContainersUpgrade(ugi, service,
            Lists.newArrayList(liveContainer));
      }
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
    return Response.status(Status.NO_CONTENT).build();
  }

};