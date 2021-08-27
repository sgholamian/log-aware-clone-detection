//,temp,sample_1377.java,2,12,temp,sample_1376.java,2,11
//,3
public class xxx {
public void afterPropertiesSet() throws Exception {
StopWatch watch = new StopWatch();
super.afterPropertiesSet();
Boolean shutdownEager = CamelContextHelper.parseBoolean(getContext(), getShutdownEager());
if (shutdownEager != null) {


log.info("using shutdowneager");
}
}

};