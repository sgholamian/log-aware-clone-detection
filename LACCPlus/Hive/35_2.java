//,temp,CLIService.java,373,380,temp,CLIService.java,251,258
//,3
public class xxx {
  @Override
  public GetInfoValue getInfo(SessionHandle sessionHandle, GetInfoType getInfoType)
      throws HiveSQLException {
    GetInfoValue infoValue = sessionManager.getSession(sessionHandle)
        .getInfo(getInfoType);
    LOG.debug(sessionHandle + ": getInfo()");
    return infoValue;
  }

};