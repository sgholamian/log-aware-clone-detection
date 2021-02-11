//,temp,NetUtils.java,1214,1223,temp,NetUtils.java,1205,1212
//,2
public class xxx {
    public static boolean validateIcmpCode(final long icmpCode) {

        //Source - http://www.erg.abdn.ac.uk/~gorry/course/inet-pages/icmp-code.html
        if (!(icmpCode >= 0 && icmpCode <= 15)) {
            s_logger.warn("Icmp code should be within 0-15 range");
            return false;
        }

        return true;
    }

};