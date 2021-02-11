//,temp,sample_3280.java,2,12,temp,sample_7171.java,2,11
//,3
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