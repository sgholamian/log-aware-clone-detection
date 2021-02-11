//,temp,RMWebServices.java,1709,1765,temp,RMWebServices.java,1654,1700
//,3
public class xxx {
  @DELETE
  @Path(RMWSConsts.DELEGATION_TOKEN)
  @Produces({ MediaType.APPLICATION_JSON + "; " + JettyUtils.UTF_8,
      MediaType.APPLICATION_XML + "; " + JettyUtils.UTF_8 })
  @Override
  public Response cancelDelegationToken(@Context HttpServletRequest hsr)
      throws AuthorizationException, IOException, InterruptedException,
      Exception {

    UserGroupInformation callerUGI = getCallerUserGroupInformation(hsr, true);
    initForWritableEndpoints(callerUGI, false);

    try {
      createKerberosUserGroupInformation(hsr, callerUGI);
      callerUGI.setAuthenticationMethod(AuthenticationMethod.KERBEROS);
    } catch (YarnException ye) {
      return Response.status(Status.FORBIDDEN).entity(ye.getMessage()).build();
    }

    Token<RMDelegationTokenIdentifier> token = extractToken(hsr);

    org.apache.hadoop.yarn.api.records.Token dToken = BuilderUtils
        .newDelegationToken(token.getIdentifier(), token.getKind().toString(),
            token.getPassword(), token.getService().toString());
    final CancelDelegationTokenRequest req =
        CancelDelegationTokenRequest.newInstance(dToken);

    try {
      callerUGI
          .doAs(new PrivilegedExceptionAction<CancelDelegationTokenResponse>() {
            @Override
            public CancelDelegationTokenResponse run()
                throws IOException, YarnException {
              return rm.getClientRMService().cancelDelegationToken(req);
            }
          });
    } catch (UndeclaredThrowableException ue) {
      if (ue.getCause() instanceof YarnException) {
        if (ue.getCause().getCause() instanceof InvalidToken) {
          throw new BadRequestException(ue.getCause().getCause().getMessage());
        } else if (ue.getCause()
            .getCause() instanceof org.apache.hadoop.security.AccessControlException) {
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