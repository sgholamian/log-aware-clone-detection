//,temp,OSMXBean.java,150,181,temp,OSMXBean.java,105,142
//,3
public class xxx {
    public long getOpenFileDescriptorCount() 
    {
        Long ofdc;
    
        if (!ibmvendor) {
            ofdc = getOSUnixMXBeanMethod("getOpenFileDescriptorCount");
            return (ofdc != null ? ofdc.longValue () : -1);
        }
        
        try {
            //need to get the PID number of the process first
            RuntimeMXBean rtmbean = ManagementFactory.getRuntimeMXBean();
            String rtname = rtmbean.getName();
            String[] pidhost = rtname.split("@");

            //using linux bash commands to retrieve info
            Process p = Runtime.getRuntime().exec(
                    new String[] { "bash", "-c",
                    "ls /proc/" + pidhost[0] + "/fdinfo | wc -l" });
            InputStream in = p.getInputStream();
            BufferedReader output = new BufferedReader(
                    new InputStreamReader(in));

            try {
                String openFileDesCount;
                if ((openFileDesCount = output.readLine()) != null) {
                    return Long.parseLong(openFileDesCount);
                }
            } finally {
                if (output != null) {
                    output.close();
                }
            }
        } catch (IOException ie) {
            LOG.warn("Not able to get the number of open file descriptors", ie);
        }
        return -1;
    }

};