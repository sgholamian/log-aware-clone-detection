//,temp,RouterAdminServer.java,362,380,temp,RouterAdminServer.java,330,347
//,3
public class xxx {
  @Override
  public DisableNameserviceResponse disableNameservice(
      DisableNameserviceRequest request) throws IOException {

    RouterPermissionChecker pc = getPermissionChecker();
    if (pc != null) {
      pc.checkSuperuserPrivilege();
    }

    String nsId = request.getNameServiceId();
    boolean success = false;
    if (namespaceExists(nsId)) {
      success = getDisabledNameserviceStore().disableNameservice(nsId);
    } else {
      LOG.error("Cannot disable {}, it does not exists", nsId);
    }
    return DisableNameserviceResponse.newInstance(success);
  }

};