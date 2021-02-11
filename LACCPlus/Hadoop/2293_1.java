//,temp,RouterAdminServer.java,362,380,temp,RouterAdminServer.java,330,347
//,3
public class xxx {
  @Override
  public EnableNameserviceResponse enableNameservice(
      EnableNameserviceRequest request) throws IOException {
    RouterPermissionChecker pc = getPermissionChecker();
    if (pc != null) {
      pc.checkSuperuserPrivilege();
    }

    String nsId = request.getNameServiceId();
    DisabledNameserviceStore store = getDisabledNameserviceStore();
    Set<String> disabled = store.getDisabledNameservices();
    boolean success = false;
    if (disabled.contains(nsId)) {
      success = store.enableNameservice(nsId);
    } else {
      LOG.error("Cannot enable {}, it was not disabled", nsId);
    }
    return EnableNameserviceResponse.newInstance(success);
  }

};