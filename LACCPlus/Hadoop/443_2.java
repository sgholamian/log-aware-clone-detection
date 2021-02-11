//,temp,MRAMSimulator.java,157,185,temp,AMSimulator.java,288,314
//,3
public class xxx {
  private void registerAM()
          throws YarnException, IOException, InterruptedException {
    // register application master
    final RegisterApplicationMasterRequest amRegisterRequest =
            Records.newRecord(RegisterApplicationMasterRequest.class);
    amRegisterRequest.setHost("localhost");
    amRegisterRequest.setRpcPort(1000);
    amRegisterRequest.setTrackingUrl("localhost:1000");

    UserGroupInformation ugi =
        UserGroupInformation.createRemoteUser(appAttemptId.toString());
    Token<AMRMTokenIdentifier> token = rm.getRMContext().getRMApps().get(appId)
        .getRMAppAttempt(appAttemptId).getAMRMToken();
    ugi.addTokenIdentifier(token.decodeIdentifier());

    ugi.doAs(
            new PrivilegedExceptionAction<RegisterApplicationMasterResponse>() {
      @Override
      public RegisterApplicationMasterResponse run() throws Exception {
        return rm.getApplicationMasterService()
                .registerApplicationMaster(amRegisterRequest);
      }
    });

    LOG.info(MessageFormat.format(
            "Register the application master for application {0}", appId));
  }

};