//,temp,RMWebServices.java,1644,1695,temp,RMWebServices.java,1588,1635
//,3
public class xxx {
  private Response renewDelegationToken(DelegationToken tokenData,
      HttpServletRequest hsr, UserGroupInformation callerUGI)
      throws AuthorizationException, IOException, InterruptedException,
      Exception {

    Token<RMDelegationTokenIdentifier> token =
        extractToken(tokenData.getToken());

    org.apache.hadoop.yarn.api.records.Token dToken =
        BuilderUtils.newDelegationToken(token.getIdentifier(), token.getKind()
          .toString(), token.getPassword(), token.getService().toString());
    final RenewDelegationTokenRequest req =
        RenewDelegationTokenRequest.newInstance(dToken);

    RenewDelegationTokenResponse resp;
    try {
      resp =
          callerUGI
            .doAs(new PrivilegedExceptionAction<RenewDelegationTokenResponse>() {
              @Override
              public RenewDelegationTokenResponse run() throws IOException,
                  YarnException {
                return rm.getClientRMService().renewDelegationToken(req);
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
    long renewTime = resp.getNextExpirationTime();

    DelegationToken respToken = new DelegationToken();
    respToken.setNextExpirationTime(renewTime);
    return Response.status(Status.OK).entity(respToken).build();
  }

};