//,temp,sample_3256.java,2,19,temp,sample_3257.java,2,19
//,3
public class xxx {
public void dummy_method(){
Long value = null;
synchronized(metrics) {
if (!metrics.hasKey(name)) {
value = Long.valueOf(increment);
set(name, value);
} else {
try {
value = ((Long)get(name)) + increment;
set(name, value);
} catch (JMException e) {


log.info("could not find counter value for increment operation skipped");
}
}
}
}

};