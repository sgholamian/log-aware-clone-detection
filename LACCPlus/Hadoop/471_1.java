//,temp,RMWebServices.java,1644,1695,temp,RMWebServices.java,1588,1635
//,3
public class xxx {
  @DELETE
  @Path("/delegation-token")
  @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
  public Response cancelDelegationToken(@Context HttpServletRequest hsr)
      throws AuthorizationException, IOException, InterruptedException,
      Exception {

    init();
    UserGroupInformation callerUGI;
    try {
      callerUGI = createKerberosUserGroupInformation(hsr);
    } catch (YarnException ye) {
      return Response.status(Status.FORBIDDEN).entity(ye.getMessage()).build();
    }

    Token<RMDelegationTokenIdentifier> token = extractToken(hsr);

    org.apache.hadoop.yarn.api.records.Token dToken =
        BuilderUtils.newDelegationToken(token.getIdentifier(), token.getKind()
          .toString(), token.getPassword(), token.getService().toString());
    final CancelDelegationTokenRequest req =
        CancelDelegationTokenRequest.newInstance(dToken);

    try {
      callerUGI
        .doAs(new PrivilegedExceptionAction<CancelDelegationTokenResponse>() {
          @Override
          public CancelDelegationTokenResponse run() throws IOException,
              YarnException {
            return rm.getClientRMService().cancelDelegationToken(req);
          }
        });
    } catch (UndeclaredThrowableException ue) {
      if (ue.getCause() instanceof YarnException) {
        if (ue.getCause().getCause() instanceof InvalidToken) {
          throw new BadRequestException(ue.getCause().getCause().getMessage());
        } else if (ue.getCause().getCause() instanceof org.apache.hadoop.security.AccessControlException) {
          return Response.status(Status.FORBIDDEN)
            .entity(ue.getCause().getCause().getMessage()).build();
        }
        LOG.info("Renew delegation token request failed", ue);
        throw ue;
      }
      LOG.info("Renew delegation token request failed", ue);
      throw ue;
    } catch (Exception e) {
      LOG.info("Renew delegation token request failed", e);
      throw e;
    }

    return Response.status(Status.OK).build();
  }

};