//,temp,sample_5207.java,2,17,temp,sample_1195.java,2,17
//,3
public class xxx {
public void dummy_method(){
UserGroupInformation ugi = UserGroupInformation.createProxyUser(t.getOwner(), UserGroupInformation.getLoginUser());
ugi.doAs(new PrivilegedExceptionAction<Object>() {
public Object run() throws Exception {
mr.run(conf, jobName.toString(), t, sd, txns, ci, su, txnHandler);
return null;
}
});
try {
FileSystem.closeAllForUGI(ugi);
} catch (IOException exception) {


log.info("could not clean up file system handles for ugi for");
}
}

};