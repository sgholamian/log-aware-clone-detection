//,temp,sample_5290.java,2,9,temp,sample_5289.java,2,9
//,2
public class xxx {
public void testQuartzPersistentStoreClusteredApp() throws Exception {
AbstractXmlApplicationContext db = new ClassPathXmlApplicationContext("org/apache/camel/component/quartz2/SpringQuartzConsumerClusteredAppDatabase.xml");
AbstractXmlApplicationContext app = new ClassPathXmlApplicationContext("org/apache/camel/component/quartz2/SpringQuartzConsumerRecoveryClusteredAppOne.xml");
IOHelper.close(app);


log.info("crashed");
}

};