//,temp,NfsExports.java,293,308,temp,RegistrySecurity.java,339,353
//,3
public class xxx {
    @Override
    public boolean isIncluded(String address, String hostname) {
      if(ipOrHost.equalsIgnoreCase(address) ||
          ipOrHost.equalsIgnoreCase(hostname)) {
        if(LOG.isDebugEnabled()) {
          LOG.debug("ExactMatcher '" + ipOrHost + "', allowing client " +
              "'" + address + "', '" + hostname + "'");
        }
        return true;
      }
      if(LOG.isDebugEnabled()) {
        LOG.debug("ExactMatcher '" + ipOrHost + "', denying client " +
            "'" + address + "', '" + hostname + "'");
      }
      return false;
    }

};