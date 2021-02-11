//,temp,sample_2551.java,2,14,temp,sample_2552.java,2,14
//,2
public class xxx {
public RMNMInfo(RMContext rmc, ResourceScheduler sched) {
this.rmContext = rmc;
this.scheduler = sched;
StandardMBean bean;
try {
bean = new StandardMBean(this,RMNMInfoBeans.class);
MBeans.register("ResourceManager", "RMNMInfo", bean);
} catch (NotCompliantMBeanException e) {
}


log.info("registered rmnminfo mbean");
}

};