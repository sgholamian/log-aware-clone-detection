//,temp,sample_6717.java,2,17,temp,sample_6716.java,2,17
//,3
public class xxx {
public void dummy_method(){
if (configuredDeadline != null && Integer.class.isInstance(configuredDeadline)) {
deadline = (Integer) configuredDeadline;
}
if (configuredDeadline != null && String.class.isInstance(configuredDeadline)) {
try {
deadline = Integer.valueOf((String) configuredDeadline);
} catch (Exception e) {
}
}
if (deadline != 0) {


log.info("exchange ack deadline");
}
}

};