//,temp,sample_4027.java,2,14,temp,sample_4022.java,2,11
//,3
public class xxx {
public boolean connect(StatsCollectionContext scc) {
List<String> statsDirs = scc.getStatsTmpDirs();
assert statsDirs.size() == 1 : "Found multiple stats dirs: " + statsDirs;
Path statsDir = new Path(statsDirs.get(0));
if (Utilities.FILE_OP_LOGGER.isTraceEnabled()) {


log.info("about to read stats from");
}
}

};