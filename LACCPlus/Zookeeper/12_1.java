//,temp,FourLetterWordsWhiteListTest.java,246,250,temp,FourLetterWordsWhiteListTest.java,236,240
//,3
public class xxx {
    private void verifyExactMatch(String cmd, String expected) throws IOException, SSLContextException {
        String resp = sendRequest(cmd);
        LOG.info("cmd " + cmd + " expected an exact match of " + expected + "; got " + resp);
        Assert.assertTrue(resp.trim().equals(expected));
    }

};