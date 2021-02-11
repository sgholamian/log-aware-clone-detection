//,temp,sample_9467.java,2,14,temp,sample_9469.java,2,13
//,3
public class xxx {
public void testDeleteContainers() throws Throwable {
describe("Delete all the WASB test containers");
int count = 0;
CloudStorageAccount storageAccount = getTestAccount().getRealAccount();
CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
Iterable<CloudBlobContainer> containers = blobClient.listContainers(CONTAINER_PREFIX);
for (CloudBlobContainer container : containers) {


log.info("container uri");
}
}

};