//,temp,sample_6525.java,2,9,temp,sample_7386.java,2,14
//,3
public class xxx {
private FileContext(final AbstractFileSystem defFs, final FsPermission theUmask, final Configuration aConf) {
defaultFS = defFs;
umask = theUmask;
conf = aConf;
tracer = FsTracer.get(aConf);
try {
ugi = UserGroupInformation.getCurrentUser();
} catch (IOException e) {


log.info("exception in getcurrentuser");
}
}

};