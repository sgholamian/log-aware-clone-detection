//,temp,OSMXBeanTest.java,62,69,temp,OSMXBeanTest.java,53,60
//,2
public class xxx {
    @Test
    public final void testGetOpenFileDescriptorCount() {
        if (osMbean != null && osMbean.getUnix() == true) {
            ofdc = osMbean.getOpenFileDescriptorCount();
            LOG.info("open fdcount is: " + ofdc);
        }   
        Assert.assertFalse("The number of open file descriptor is negative",(ofdc < 0));
    }

};