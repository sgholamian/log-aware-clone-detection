//,temp,ServerCnxnTest.java,69,73,temp,FourLetterWordsWhiteListTest.java,236,240
//,3
public class xxx {
    private void verify(String cmd, String expected) throws IOException {
        String resp = sendRequest(cmd, 0);
        LOG.info("cmd " + cmd + " expected " + expected + " got " + resp);
        Assert.assertTrue(resp.contains(expected));
    }

};