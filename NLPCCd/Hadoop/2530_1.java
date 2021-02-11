//,temp,sample_1741.java,2,11,temp,sample_7171.java,2,11
//,2
public class xxx {
public void run() {
long start = Time.monotonicNow();
try {
db.compactRange(null, null);
} catch (DBException e) {


log.info("error compacting database");
}
}

};