//,temp,FourLetterWordsWhiteListTest.java,236,240,temp,ZooKeeperServerStartupTest.java,185,190
//,3
public class xxx {
    private void verify(String cmd, String expected)
            throws IOException, SSLContextException {
        String resp = sendRequest(cmd);
        LOG.info("cmd " + cmd + " expected " + expected + " got " + resp);
        Assert.assertTrue("Unexpected response", resp.contains(expected));
    }

};