//,temp,CitrixResourceBase.java,3078,3089,temp,CitrixResourceBase.java,3061,3076
//,3
public class xxx {
    private long getStaticMax(final String os, final boolean b, final long dynamicMinRam, final long dynamicMaxRam, final long recommendedValue) {
        if (recommendedValue == 0) {
            s_logger.warn("No recommended value found for dynamic max, setting static max and dynamic max equal");
            return dynamicMaxRam;
        }
        final long staticMax = Math.min(recommendedValue, 4l * dynamicMinRam); // XS
        // constraint
        // for
        // stability
        if (dynamicMaxRam > staticMax) { // XS contraint that dynamic max <=
            // static max
            s_logger.warn("dynamixMax " + dynamicMaxRam + " cant be greater than static max " + staticMax + ", can lead to stability issues. Setting static max as much as dynamic max ");
            return dynamicMaxRam;
        }
        return staticMax;
    }

};