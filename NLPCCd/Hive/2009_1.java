//,temp,sample_3252.java,2,10,temp,sample_3253.java,2,10
//,2
public class xxx {
public Long getNumCounter() {
try {
return (Long) metrics.get(numCounter);
} catch (JMException e) {


log.info("could not find counter value for returning null instead");
}
}

};