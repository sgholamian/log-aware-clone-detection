//,temp,sample_3551.java,2,17,temp,sample_3553.java,2,17
//,2
public class xxx {
public void dummy_method(){
try {
switch(lMode) {
case EXCLUSIVE: metrics.decrementCounter(MetricsConstant.ZOOKEEPER_HIVE_EXCLUSIVELOCKS);
break;
case SEMI_SHARED: metrics.decrementCounter(MetricsConstant.ZOOKEEPER_HIVE_SEMISHAREDLOCKS);
break;
default: metrics.decrementCounter(MetricsConstant.ZOOKEEPER_HIVE_SHAREDLOCKS);
break;
}
} catch (Exception e) {


log.info("error reporting hive client zookeeper unlock operation to metrics system");
}
}

};