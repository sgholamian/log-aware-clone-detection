//,temp,sample_5660.java,2,17,temp,sample_3355.java,2,17
//,3
public class xxx {
public void dummy_method(){
String testName = (String) testMethodName.apply(this);
String name = className + "-" + testName + ".xml";
ManagedCamelContextMBean managedCamelContext = context.getManagedCamelContext();
if (managedCamelContext == null) {
} else {
String xml = managedCamelContext.dumpRoutesCoverageAsXml();
String combined = "<camelRouteCoverage>\n" + gatherTestDetailsAsXml(testName) + xml + "\n</camelRouteCoverage>";
File file = new File(dir);
file.mkdirs();
file = new File(dir, name);


log.info("dumping route coverage to file");
}
}

};