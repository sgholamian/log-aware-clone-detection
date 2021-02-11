//,temp,Xen.java,300,313,temp,Xen.java,286,299
//,2
public class xxx {
        public Integer getVifIdByMac(String mac) {
            Integer c = 0;
            for (final String entry : vmVifs) {
                final String[] parts = entry.split(",");
                final String[] macpart = parts[0].split("=");
                assert macpart.length == 2 : "Invalid entry: " + entry;
                if ("mac".equals(macpart[0]) && macpart[1].equals(mac)) {
                    return c;
                }
                c += 1;
            }
            LOGGER.debug("No vif matched mac: " + mac + " in " + vmVifs);
            return -1;
        }

};