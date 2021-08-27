//,temp,sample_4026.java,2,9,temp,sample_3325.java,2,10
//,3
public class xxx {
public boolean closeConnection(StatsCollectionContext scc) {
List<String> statsDirs = scc.getStatsTmpDirs();
assert statsDirs.size() == 1 : "Found multiple stats dirs: " + statsDirs;
Path statsDir = new Path(statsDirs.get(0));


log.info("about to delete stats tmp dir");
}

};