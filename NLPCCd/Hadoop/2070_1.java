//,temp,sample_7092.java,2,16,temp,sample_7069.java,2,16
//,3
public class xxx {
public void dummy_method(){
KMSWebApp.getKeyCallsMeter().mark();
assertAccess(KMSACLs.Type.GET, user, KMSOp.GET_KEY_VERSIONS, name);
List<KeyVersion> ret = user.doAs( new PrivilegedExceptionAction<List<KeyVersion>>() {
public List<KeyVersion> run() throws Exception {
return provider.getKeyVersions(name);
}
}
);
Object json = KMSServerJSONUtils.toJSON(ret);
kmsAudit.ok(user, KMSOp.GET_KEY_VERSIONS, name, "");


log.info("exiting getkeyversions method");
}

};