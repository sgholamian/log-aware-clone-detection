//,temp,sample_1646.java,2,14,temp,sample_1648.java,2,14
//,2
public class xxx {
public void testMqttDuplicatesAfterBrokerRestartWithoutClientID() throws Exception {
brokerService.stop();
brokerService.waitUntilStopped();
brokerService = new BrokerService();
brokerService.setPersistent(false);
brokerService.setAdvisorySupport(false);
brokerService.addConnector(MQTTTestSupport.getConnection() + "?trace=true");
brokerService.start();
brokerService.waitUntilStarted();


log.info("broker restarted");
}

};