//,temp,sample_2414.java,2,15,temp,sample_2411.java,2,13
//,3
public class xxx {
protected boolean accept(Path p, @CheckForNull Boolean isDir) {
try {
HColumnDescriptor.isLegalFamilyName(Bytes.toBytes(p.getName()));
} catch (IllegalArgumentException iae) {
return false;
}
try {
return isDirectory(fs, isDir, p);
} catch (IOException ioe) {


log.info("skipping file due to ioexception");
}
}

};