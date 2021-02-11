//,temp,HostsFileReader.java,298,305,temp,HostsFileReader.java,289,296
//,3
public class xxx {
  public void setExcludesFile(String excludesFile) {
    LOG.info("Setting the excludes file to " + excludesFile);
    HostDetails oldDetails = current.get();
    HostDetails newDetails = new HostDetails(
        oldDetails.includesFile, oldDetails.includes,
        excludesFile, oldDetails.excludes);
    current.set(newDetails);
  }

};