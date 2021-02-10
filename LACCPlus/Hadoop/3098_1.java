//,temp,RMWebServices.java,2009,2045,temp,RMWebServices.java,1485,1524
//,3
public class xxx {
  @POST
  @Path(RMWSConsts.RESERVATION_DELETE)
  @Produces({ MediaType.APPLICATION_JSON + "; " + JettyUtils.UTF_8,
      MediaType.APPLICATION_XML + "; " + JettyUtils.UTF_8 })
  @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  @Override
  public Response deleteReservation(ReservationDeleteRequestInfo resContext,
      @Context HttpServletRequest hsr)
      throws AuthorizationException, IOException, InterruptedException {

    UserGroupInformation callerUGI = getCallerUserGroupInformation(hsr, true);
    initForWritableEndpoints(callerUGI, false);

    final ReservationDeleteRequest reservation =
        createReservationDeleteRequest(resContext);

    ReservationDeleteResponseInfo resRespInfo;
    try {
      resRespInfo = callerUGI
          .doAs(new PrivilegedExceptionAction<ReservationDeleteResponseInfo>() {
            @Override
            public ReservationDeleteResponseInfo run()
                throws IOException, YarnException {
              rm.getClientRMService().deleteReservation(reservation);
              return new ReservationDeleteResponseInfo();
            }
          });
    } catch (UndeclaredThrowableException ue) {
      if (ue.getCause() instanceof YarnException) {
        throw new BadRequestException(ue.getCause().getMessage());
      }
      LOG.info("Update reservation request failed", ue);
      throw ue;
    }

    return Response.status(Status.OK).entity(resRespInfo).build();
  }

};