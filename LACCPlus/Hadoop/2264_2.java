//,temp,CleanupTestContainers.java,67,84,temp,CleanupTestContainers.java,49,65
//,3
public class xxx {
  @Test
  public void testEnumContainers() throws Throwable {
    describe("Enumerating all the WASB test containers");

    int count = 0;
    CloudStorageAccount storageAccount = getTestAccount().getRealAccount();
    CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
    Iterable<CloudBlobContainer> containers
        = blobClient.listContainers(CONTAINER_PREFIX);
    for (CloudBlobContainer container : containers) {
      count++;
      LOG.info("Container {} URI {}",
          container.getName(),
          container.getUri());
    }
    LOG.info("Found {} test containers", count);
  }

};