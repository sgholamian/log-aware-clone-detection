//,temp,sample_4027.java,2,14,temp,sample_4022.java,2,11
//,3
public class xxx {
public boolean closeConnection(StatsCollectionContext scc) {
List<String> statsDirs = scc.getStatsTmpDirs();
assert statsDirs.size() == 1 : "Found multiple stats dirs: " + statsDirs;
Path statsDir = new Path(statsDirs.get(0));
try {
fs.delete(statsDir,true);
return true;
} catch (IOException e) {


log.info("failed to delete stats dir");
}
}

};