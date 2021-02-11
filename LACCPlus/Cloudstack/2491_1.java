//,temp,CitrixResourceBase.java,3078,3089,temp,CitrixResourceBase.java,3061,3076
//,3
public class xxx {
    private long getStaticMin(final String os, final boolean b, final long dynamicMinRam, final long dynamicMaxRam, final long recommendedValue) {
        if (recommendedValue == 0) {
            s_logger.warn("No recommended value found for dynamic min");
            return dynamicMinRam;
        }

        if (dynamicMinRam < recommendedValue) { // XS contraint that dynamic min
            // > static min
            s_logger.warn("Vm is set to dynamixMin " + dynamicMinRam + " less than the recommended static min " + recommendedValue + ", could lead to stability issues");
        }
        return dynamicMinRam;
    }

};