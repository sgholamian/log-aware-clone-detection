//,temp,sample_3553.java,2,16,temp,sample_6357.java,2,16
//,2
public class xxx {
public void dummy_method(){
ObjectSpec oSpec = new ObjectSpec();
oSpec.setObj(_mor);
oSpec.setSkip(Boolean.TRUE);
oSpec.getSelectSet().add(host2DatastoreTraversal);
PropertyFilterSpec pfSpec = new PropertyFilterSpec();
pfSpec.getPropSet().add(pSpec);
pfSpec.getObjectSet().add(oSpec);
List<PropertyFilterSpec> pfSpecArr = new ArrayList<PropertyFilterSpec>();
pfSpecArr.add(pfSpec);
List<ObjectContent> properties = _context.getService().retrieveProperties(_context.getPropertyCollector(), pfSpecArr);


log.info("vcenter api trace retrieveproperties done");
}

};