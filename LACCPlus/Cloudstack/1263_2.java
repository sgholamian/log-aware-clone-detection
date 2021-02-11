//,temp,ClusterMO.java,245,281,temp,HostMO.java,649,680
//,3
public class xxx {
    @Override
    public ObjectContent[] getVmPropertiesOnHyperHost(String[] propertyPaths) throws Exception {
        if (s_logger.isTraceEnabled())
            s_logger.trace("vCenter API trace - retrieveProperties() for VM properties. target MOR: " + _mor.getValue() + ", properties: " +
                    new Gson().toJson(propertyPaths));

        PropertySpec pSpec = new PropertySpec();
        pSpec.setType("VirtualMachine");
        pSpec.getPathSet().addAll(Arrays.asList(propertyPaths));

        TraversalSpec host2VmTraversal = new TraversalSpec();
        host2VmTraversal.setType("HostSystem");
        host2VmTraversal.setPath("vm");
        host2VmTraversal.setName("host2VmTraversal");

        ObjectSpec oSpec = new ObjectSpec();
        oSpec.setObj(_mor);
        oSpec.setSkip(Boolean.TRUE);
        oSpec.getSelectSet().add(host2VmTraversal);

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