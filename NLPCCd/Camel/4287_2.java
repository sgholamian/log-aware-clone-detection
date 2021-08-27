//,temp,sample_192.java,2,14,temp,sample_191.java,2,13
//,3
public class xxx {
public void testOpenMllpEnvelopeWithReset() throws Exception {
expectedCompleteCount = 4;
expectedInvalidCount = 1;
setExpectedCounts();
NotifyBuilder notify1 = new NotifyBuilder(context).whenDone(2).create();
NotifyBuilder notify2 = new NotifyBuilder(context).whenDone(5).create();
mllpClient.connect();
mllpClient.setSoTimeout(10000);


log.info("sending test message");
}

};