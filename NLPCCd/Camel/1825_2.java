//,temp,sample_426.java,2,12,temp,sample_425.java,2,12
//,2
public class xxx {
public void afterPropertiesSet() throws Exception {
if (Strings.isNullOrEmpty(loggerId)) {
log = LoggerFactory.getLogger(this.getClass().getName());
} else {
log = LoggerFactory.getLogger(loggerId);
}
pubsub = getConnectionFactory().getDefaultClient();


log.info("credential file location");
}

};