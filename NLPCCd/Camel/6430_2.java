//,temp,sample_196.java,2,16,temp,sample_195.java,2,16
//,3
public class xxx {
public void dummy_method(){
assertTrue("First two normal exchanges did not complete", notify1.matches(RESPONSE_TIMEOUT, TimeUnit.MILLISECONDS));
mllpClient.setSendEndOfBlock(false);
mllpClient.setSendEndOfData(false);
try {
mllpClient.sendMessageAndWaitForAcknowledgement(Hl7TestMessageGenerator.generateMessage(3));
} catch (MllpJUnitResourceException resourceEx) {
}
mllpClient.disconnect();
Thread.sleep(1000);
mllpClient.connect();


log.info("sending test message");
}

};