//,temp,NfsExports.java,257,273,temp,RegistrySecurity.java,320,334
//,3
public class xxx {
    @Override
    public boolean isIncluded(String address, String hostname) {
      if(subnetInfo.isInRange(address)) {
        if(LOG.isDebugEnabled()) {
          LOG.debug("CIDRNMatcher low = " + subnetInfo.getLowAddress() +
              ", high = " + subnetInfo.getHighAddress() +
              ", allowing client '" + address + "', '" + hostname + "'");
        }
        return true;
      }
      if(LOG.isDebugEnabled()) {
        LOG.debug("CIDRNMatcher low = " + subnetInfo.getLowAddress() +
            ", high = " + subnetInfo.getHighAddress() +
            ", denying client '" + address + "', '" + hostname + "'");
      }
      return false;
    }

};