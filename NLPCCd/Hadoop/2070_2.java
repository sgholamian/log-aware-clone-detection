//,temp,sample_7092.java,2,16,temp,sample_7069.java,2,16
//,3
public class xxx {
public void dummy_method(){
KMSWebApp.getAdminCallsMeter().mark();
assertAccess(KMSACLs.Type.GET_METADATA, user, KMSOp.GET_METADATA, name);
KeyProvider.Metadata metadata = user.doAs( new PrivilegedExceptionAction<KeyProvider.Metadata>() {
public KeyProvider.Metadata run() throws Exception {
return provider.getMetadata(name);
}
}
);
Object json = KMSServerJSONUtils.toJSON(name, metadata);
kmsAudit.ok(user, KMSOp.GET_METADATA, name, "");


log.info("exiting getmetadata method");
}

};