//,temp,sample_7231.java,2,9,temp,sample_7996.java,2,13
//,3
public class xxx {
public static void setPersistentFileForConfigAdmin(BundleContext bundleContext, String pid, String fileName, final Dictionary props, String symbolicName, Set<Long> bpEvents, boolean expectReload) throws IOException, InterruptedException {
if (pid != null) {
if (fileName == null) {
throw new IllegalArgumentException("The persistent file should not be null");
} else {
File load = new File(fileName);


log.info("loading properties from osgi config admin file");
}
}
}

};