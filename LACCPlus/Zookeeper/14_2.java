//,temp,ServerCnxnTest.java,69,73,temp,FourLetterWordsWhiteListTest.java,236,240
//,3
public class xxx {
    private void verifyFuzzyMatch(String cmd, String expected) throws IOException, SSLContextException {
        String resp = sendRequest(cmd);
        LOG.info("cmd " + cmd + " expected " + expected + " got " + resp);
        Assert.assertTrue(resp.contains(expected));
    }

};