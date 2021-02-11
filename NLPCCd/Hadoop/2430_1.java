//,temp,sample_7793.java,2,17,temp,sample_1502.java,2,19
//,3
public class xxx {
public synchronized void evictOldDBs() {
Iterator<Entry<Long, DB>> iterator = rollingdbsToEvict.entrySet() .iterator();
while (iterator.hasNext()) {
Entry<Long, DB> entry = iterator.next();
IOUtils.cleanupWithLogger(LOG, entry.getValue());
String dbName = fdf.format(entry.getKey());
Path path = new Path(rollingDBPath, getName() + "." + dbName);
try {
lfs.delete(path, true);
} catch (IOException ioe) {


log.info("failed to evict old db");
}
}
}

};