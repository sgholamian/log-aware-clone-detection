//,temp,NetUtils.java,1214,1223,temp,NetUtils.java,1205,1212
//,2
public class xxx {
    public static boolean validateIcmpType(final long icmpType) {
        //Source - http://www.erg.abdn.ac.uk/~gorry/course/inet-pages/icmp-code.html
        if (!(icmpType >= 0 && icmpType <= 255)) {
            s_logger.warn("impcType is not within 0-255 range");
            return false;
        }
        return true;
    }

};