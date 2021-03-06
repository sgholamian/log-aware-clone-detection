//,temp,SimulatedFSDataset.java,1146,1161,temp,FsDatasetImpl.java,2075,2086
//,3
public class xxx {
  void registerMBean(final String storageId) {
    // We wrap to bypass standard mbean naming convetion.
    // This wraping can be removed in java 6 as it is more flexible in 
    // package naming for mbeans and their impl.
    StandardMBean bean;

    try {
      bean = new StandardMBean(this,FSDatasetMBean.class);
      mbeanName = MBeans.register("DataNode", "FSDatasetState-"+
                                  storageId, bean);
    } catch (NotCompliantMBeanException e) {
      DataNode.LOG.warn("Error registering FSDatasetState MBean", e);
    }
 
    DataNode.LOG.info("Registered FSDatasetState MBean");
  }

};