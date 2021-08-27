//,temp,sample_3794.java,2,16,temp,sample_3793.java,2,14
//,3
public class xxx {
private List<HivePrivilegeObject> getFilteredObjects(List<HivePrivilegeObject> listObjs) throws MetaException {
SessionState ss = SessionState.get();
HiveAuthzContext.Builder authzContextBuilder = new HiveAuthzContext.Builder();
authzContextBuilder.setUserIpAddress(ss.getUserIpAddress());
authzContextBuilder.setForwardedAddresses(ss.getForwardedAddresses());
try {
return ss.getAuthorizerV2().filterListCmdObjects(listObjs, authzContextBuilder.build());
} catch (HiveAuthzPluginException e) {
throw new MetaException(e.getMessage());
} catch (HiveAccessControlException e) {


log.info("AccessControlException");
}
}

};