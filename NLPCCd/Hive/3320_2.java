//,temp,sample_5552.java,2,10,temp,sample_2441.java,2,10
//,2
public class xxx {
public void startCleanup(Configuration config) {
try {
ZooKeeperCleanup.startInstance(config);
} catch (Exception e) {


log.info("cleanup instance didn t start");
}
}

};