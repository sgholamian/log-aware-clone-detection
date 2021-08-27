//,temp,sample_4026.java,2,9,temp,sample_3325.java,2,10
//,3
public class xxx {
public boolean connect(StatsCollectionContext context) {
conf = context.getHiveConf();
List<String> statsDirs = context.getStatsTmpDirs();
assert statsDirs.size() == 1 : "Found multiple stats dirs: " + statsDirs;
Path statsDir = new Path(statsDirs.get(0));


log.info("connecting to");
}

};