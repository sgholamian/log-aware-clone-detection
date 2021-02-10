//,temp,RMWebServices.java,2009,2045,temp,RMWebServices.java,1485,1524
//,3
public class xxx {
  @POST
  @Path(RMWSConsts.APPS)
  @Produces({ MediaType.APPLICATION_JSON + "; " + JettyUtils.UTF_8,
      MediaType.APPLICATION_XML + "; " + JettyUtils.UTF_8 })
  @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  @Override
  public Response submitApplication(ApplicationSubmissionContextInfo newApp,
      @Context HttpServletRequest hsr)
      throws AuthorizationException, IOException, InterruptedException {

    UserGroupInformation callerUGI = getCallerUserGroupInformation(hsr, true);
    initForWritableEndpoints(callerUGI, false);

    ApplicationSubmissionContext appContext =
        RMWebAppUtil.createAppSubmissionContext(newApp, conf);

    final SubmitApplicationRequest req =
        SubmitApplicationRequest.newInstance(appContext);

    try {
      callerUGI
          .doAs(new PrivilegedExceptionAction<SubmitApplicationResponse>() {
            @Override
            public SubmitApplicationResponse run()
                throws IOException, YarnException {
              return rm.getClientRMService().submitApplication(req);
            }
          });
    } catch (UndeclaredThrowableException ue) {
      if (ue.getCause() instanceof YarnException) {
        throw new BadRequestException(ue.getCause().getMessage());
      }
      LOG.info("Submit app request failed", ue);
      throw ue;
    }

    String url = hsr.getRequestURL() + "/" + newApp.getApplicationId();
    return Response.status(Status.ACCEPTED).header(HttpHeaders.LOCATION, url)
        .build();
  }

};