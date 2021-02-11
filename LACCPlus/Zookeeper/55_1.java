//,temp,OSMXBeanTest.java,62,69,temp,OSMXBeanTest.java,53,60
//,2
public class xxx {
    @Test
    public final void testGetMaxFileDescriptorCount() {
        if (osMbean != null && osMbean.getUnix() == true) {
            mfdc = osMbean.getMaxFileDescriptorCount();
            LOG.info("max fdcount is: " + mfdc);
        }
        Assert.assertFalse("The max file descriptor number is negative",(mfdc < 0));
    }

};