//,temp,FileSystemContractBaseTest.java,89,96,temp,LoadGenerator.java,371,381
//,3
public class xxx {
  boolean stopFileCreated() {
    try {
      fc.getFileStatus(flagFile);
    } catch (FileNotFoundException e) {
      return false;
    } catch (IOException e) {
      LOG.error("Got error when checking if file exists:" + flagFile, e);
    }
    LOG.info("Flag file was created. Stopping the test.");
    return true;
  }

};