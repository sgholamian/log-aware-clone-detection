//,temp,ClusterMO.java,283,313,temp,ClusterMO.java,245,281
//,3
public class xxx {
    @Override
    public ObjectContent[] getDatastorePropertiesOnHyperHost(String[] propertyPaths) throws Exception {
        if (s_logger.isTraceEnabled())
            s_logger.trace("vCenter API trace - retrieveProperties() on Datastore properties. target MOR: " + _mor.getValue() + ", properties: " + new Gson().toJson(propertyPaths));

        PropertySpec pSpec = new PropertySpec();
        pSpec.setType("Datastore");
        pSpec.getPathSet().addAll(Arrays.asList(propertyPaths));

        TraversalSpec cluster2DatastoreTraversal = new TraversalSpec();
        cluster2DatastoreTraversal.setType("ClusterComputeResource");
        cluster2DatastoreTraversal.setPath("datastore");
        cluster2DatastoreTraversal.setName("cluster2DatastoreTraversal");

        ObjectSpec oSpec = new ObjectSpec();
        oSpec.setObj(_mor);
        oSpec.setSkip(Boolean.TRUE);
        oSpec.getSelectSet().add(cluster2DatastoreTraversal);

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