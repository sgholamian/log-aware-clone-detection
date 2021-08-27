//,temp,sample_166.java,2,9,temp,sample_168.java,2,9
//,2
public class xxx {
public boolean unregister(HealthCheck check) {
boolean result = checks.remove(check);
if (result) {


log.info("healthcheck with id successfully un registered");
}
}

};