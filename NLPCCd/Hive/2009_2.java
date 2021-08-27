//,temp,sample_3252.java,2,10,temp,sample_3253.java,2,10
//,2
public class xxx {
public Long getTimeCounter() {
try {
return (Long) metrics.get(timeCounter);
} catch (JMException e) {


log.info("could not find timer value for returning null instead");
}
}

};