//,temp,HostsFileReader.java,298,305,temp,HostsFileReader.java,281,287
//,3
public class xxx {
  public void updateFileNames(String includesFile, String excludesFile) {
    LOG.info("Setting the includes file to " + includesFile);
    LOG.info("Setting the excludes file to " + excludesFile);
    HostDetails oldDetails = current.get();
    HostDetails newDetails = new HostDetails(includesFile, oldDetails.includes,
        excludesFile, oldDetails.excludes);
    current.set(newDetails);
  }

};