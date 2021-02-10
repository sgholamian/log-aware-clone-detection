//,temp,TestSecureRMRegistryOperations.java,89,111,temp,TestCopyMapper.java,81,89
//,3
public class xxx {
  public RMRegistryOperationsService startRMRegistryOperations() throws
      LoginException, IOException, InterruptedException {
    // kerberos
    secureConf.set(KEY_REGISTRY_CLIENT_AUTH,
        REGISTRY_CLIENT_AUTH_KERBEROS);
    secureConf.set(KEY_REGISTRY_CLIENT_JAAS_CONTEXT, ZOOKEEPER_CLIENT_CONTEXT);

    RMRegistryOperationsService registryOperations = zookeeperUGI.doAs(
        new PrivilegedExceptionAction<RMRegistryOperationsService>() {
          @Override
          public RMRegistryOperationsService run() throws Exception {
            RMRegistryOperationsService operations
                = new RMRegistryOperationsService("rmregistry", secureZK);
            addToTeardown(operations);
            operations.init(secureConf);
            LOG.info(operations.bindingDiagnosticDetails());
            operations.start();
            return operations;
          }
        });

    return registryOperations;
  }

};