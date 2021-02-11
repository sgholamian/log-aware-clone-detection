//,temp,ClusterMO.java,283,313,temp,ClusterMO.java,245,281
//,3
public class xxx {
    @Override
    public ObjectContent[] getVmPropertiesOnHyperHost(String[] propertyPaths) throws Exception {
        if (s_logger.isTraceEnabled())
            s_logger.trace("vCenter API trace - retrieveProperties() for VM properties. target MOR: " + _mor.getValue() + ", properties: " + new Gson().toJson(propertyPaths));

        PropertySpec pSpec = new PropertySpec();
        pSpec.setType("VirtualMachine");
        pSpec.getPathSet().addAll(Arrays.asList(propertyPaths));

        TraversalSpec host2VmFolderTraversal = new TraversalSpec();
        host2VmFolderTraversal.setType("HostSystem");
        host2VmFolderTraversal.setPath("vm");
        host2VmFolderTraversal.setName("host2VmFolderTraversal");

        TraversalSpec cluster2HostFolderTraversal = new TraversalSpec();
        cluster2HostFolderTraversal.setType("ClusterComputeResource");
        cluster2HostFolderTraversal.setPath("host");
        cluster2HostFolderTraversal.setName("cluster2HostFolderTraversal");
        cluster2HostFolderTraversal.getSelectSet().add(host2VmFolderTraversal);

        ObjectSpec oSpec = new ObjectSpec();
        oSpec.setObj(getMor());
        oSpec.setSkip(Boolean.TRUE);
        oSpec.getSelectSet().add(cluster2HostFolderTraversal);

        PropertyFilterSpec pfSpec = new PropertyFilterSpec();
        pfSpec.getPropSet().add(pSpec);
        pfSpec.getObjectSet().add(oSpec);
        List<PropertyFilterSpec> pfSpecArr = new ArrayList<PropertyFilterSpec>();
        pfSpecArr.add(pfSpec);

        List<ObjectContent> properties = _context.getService().retrieveProperties(_context.getPropertyCollector(), pfSpecArr);

        if (s_logger.isTraceEnabled())
            s_logger.trace("vCenter API trace - retrieveProperties() done");
        return properties.toArray(new ObjectContent[properties.size()]);
    }

};