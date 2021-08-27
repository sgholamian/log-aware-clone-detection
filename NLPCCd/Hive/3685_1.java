//,temp,sample_3763.java,2,10,temp,sample_72.java,2,11
//,3
public class xxx {
public static Exception stopQuietly(Service service) {
try {
stop(service);
} catch (Exception e) {


log.info("when stopping the service");
}
}

};