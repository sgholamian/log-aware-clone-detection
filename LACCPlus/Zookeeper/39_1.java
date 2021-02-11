//,temp,OSMXBean.java,150,181,temp,OSMXBean.java,105,142
//,3
public class xxx {
    public long getMaxFileDescriptorCount()
    {
        Long mfdc;

        if (!ibmvendor) {
            mfdc = getOSUnixMXBeanMethod("getMaxFileDescriptorCount");
            return (mfdc != null ? mfdc.longValue () : -1);
        }
        
        try {
            //using linux bash commands to retrieve info
            Process p = Runtime.getRuntime().exec(
                    new String[] { "bash", "-c", "ulimit -n" });
            InputStream in = p.getInputStream();
            BufferedReader output = new BufferedReader(
                    new InputStreamReader(in));

            try {
                String maxFileDesCount;
                if ((maxFileDesCount = output.readLine()) != null) {
                    return Long.parseLong(maxFileDesCount);
                }
            } finally {
                if (output != null) {
                    output.close();
                }
            }
        } catch (IOException ie) {
            LOG.warn("Not able to get the max number of file descriptors", ie);
        }
        return -1;
    }  

};