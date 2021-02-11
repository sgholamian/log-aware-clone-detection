//,temp,sample_269.java,2,8,temp,sample_5612.java,2,11
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