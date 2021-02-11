//,temp,ClusterMO.java,315,345,temp,ClusterMO.java,283,313
//,3
public class xxx {
    public ObjectContent[] getHostPropertiesOnCluster(String[] propertyPaths) throws Exception {
        if (s_logger.isTraceEnabled())
            s_logger.trace("vCenter API trace - retrieveProperties() on Host properties. target MOR: " + _mor.getValue() + ", properties: " + new Gson().toJson(propertyPaths));

        PropertySpec pSpec = new PropertySpec();
        pSpec.setType("HostSystem");
        pSpec.getPathSet().addAll(Arrays.asList(propertyPaths));

        TraversalSpec cluster2HostTraversal = new TraversalSpec();
        cluster2HostTraversal.setType("ClusterComputeResource");
        cluster2HostTraversal.setPath("host");
        cluster2HostTraversal.setName("cluster2HostTraversal");

        ObjectSpec oSpec = new ObjectSpec();
        oSpec.setObj(_mor);
        oSpec.setSkip(Boolean.TRUE);
        oSpec.getSelectSet().add(cluster2HostTraversal);

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