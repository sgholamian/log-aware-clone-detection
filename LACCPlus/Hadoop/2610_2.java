//,temp,FileSystemContractBaseTest.java,89,96,temp,YarnRegistryViewForProviders.java,220,227
//,3
public class xxx {
  public void deleteComponent(ComponentInstanceId instanceId,
      String containerId) throws IOException {
    String path = RegistryUtils.componentPath(
        user, serviceClass, instanceName,
        containerId);
    LOG.info(instanceId + ": Deleting registry path " + path);
    registryOperations.delete(path, false);
  }

};