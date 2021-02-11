//,temp,sample_4768.java,2,12,temp,sample_4769.java,2,12
//,2
public class xxx {
public MyFifoScheduler(RMContext rmContext) {
super();
try {
Configuration conf = new Configuration();
reinitialize(conf, rmContext);
} catch (IOException ie) {


log.info("add application failed with");
}
}

};