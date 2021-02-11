//,temp,sample_5414.java,2,14,temp,sample_5612.java,2,11
//,3
public class xxx {
protected String dumpRegistryRobustly(boolean verbose) {
try {
ZKPathDumper pathDumper = dumpPath(verbose);
return pathDumper.toString();
} catch (Exception e) {


log.info("ignoring exception");
}
}

};