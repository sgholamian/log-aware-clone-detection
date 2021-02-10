//,temp,ApiServer.java,528,570,temp,ApiServer.java,301,335
//,3
public class xxx {
  @PUT
  @Path(COMP_INSTANCES_PATH)
  @Consumes({MediaType.APPLICATION_JSON})
  @Produces({RestApiConstants.MEDIA_TYPE_JSON_UTF8, MediaType.TEXT_PLAIN})
  public Response updateComponentInstances(@Context HttpServletRequest request,
      @PathParam(SERVICE_NAME) String serviceName,
      List<Container> requestContainers) {

    try {
      if (requestContainers == null || requestContainers.isEmpty()) {
        throw new YarnException("No containers provided.");
      }
      UserGroupInformation ugi = getProxyUser(request);
      List<String> toUpgrade = new ArrayList<>();
      for (Container reqContainer : requestContainers) {
        if (reqContainer.getState() != null &&
            reqContainer.getState().equals(ContainerState.UPGRADING)) {
          toUpgrade.add(reqContainer.getComponentInstanceName());
        }
      }

      if (!toUpgrade.isEmpty()) {
        Service service = getServiceFromClient(ugi, serviceName);
        LOG.info("PUT: upgrade component instances {} for service = {} " +
            "user = {}", toUpgrade, serviceName, ugi);
        List<Container> liveContainers = ServiceApiUtil
            .getLiveContainers(service, toUpgrade);

        return processContainersUpgrade(ugi, service, liveContainers);
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