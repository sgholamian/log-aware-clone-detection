//,temp,Xen.java,300,313,temp,Xen.java,286,299
//,2
public class xxx {
        public Integer getVifIdByIp(String ip) {
            Integer c = 0;
            for (final String entry : vmVifs) {
                final String[] parts = entry.split(",");
                final String[] ippart = parts[1].split("=");
                assert ippart.length == 2 : "Invalid entry: " + entry;
                if ("mac".equals(ippart[0]) && ippart[1].equals(ip)) {
                    return c;
                }
                c += 1;
            }
            LOGGER.debug("No vif matched ip: " + ip + " in " + vmVifs);
            return -1;
        }

};