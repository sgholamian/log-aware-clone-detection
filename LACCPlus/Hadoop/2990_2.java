//,temp,HostsFileReader.java,298,305,temp,HostsFileReader.java,281,287
//,3
public class xxx {
  public void setIncludesFile(String includesFile) {
    LOG.info("Setting the includes file to " + includesFile);
    HostDetails oldDetails = current.get();
    HostDetails newDetails = new HostDetails(includesFile, oldDetails.includes,
        oldDetails.excludesFile, oldDetails.excludes);
    current.set(newDetails);
  }

};