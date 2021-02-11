//,temp,RMWebServices.java,2009,2045,temp,RMWebServices.java,1918,1954
//,2
public class xxx {
  @POST
  @Path(RMWSConsts.RESERVATION_UPDATE)
  @Produces({ MediaType.APPLICATION_JSON + "; " + JettyUtils.UTF_8,
      MediaType.APPLICATION_XML + "; " + JettyUtils.UTF_8 })
  @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  @Override
  public Response updateReservation(ReservationUpdateRequestInfo resContext,
      @Context HttpServletRequest hsr)
      throws AuthorizationException, IOException, InterruptedException {

    UserGroupInformation callerUGI = getCallerUserGroupInformation(hsr, true);
    initForWritableEndpoints(callerUGI, false);

    final ReservationUpdateRequest reservation =
        createReservationUpdateRequest(resContext);

    ReservationUpdateResponseInfo resRespInfo;
    try {
      resRespInfo = callerUGI
          .doAs(new PrivilegedExceptionAction<ReservationUpdateResponseInfo>() {
            @Override
            public ReservationUpdateResponseInfo run()
                throws IOException, YarnException {
              rm.getClientRMService().updateReservation(reservation);
              return new ReservationUpdateResponseInfo();
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