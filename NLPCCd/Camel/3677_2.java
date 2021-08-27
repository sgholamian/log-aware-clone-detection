//,temp,sample_5660.java,2,17,temp,sample_3355.java,2,17
//,3
public class xxx {
public void dummy_method(){
String name = className + "-" + getTestMethodName() + ".xml";
ManagedCamelContextMBean managedCamelContext = context.getManagedCamelContext();
if (managedCamelContext == null) {
} else {
logCoverageSummary(managedCamelContext);
String xml = managedCamelContext.dumpRoutesCoverageAsXml();
String combined = "<camelRouteCoverage>\n" + gatherTestDetailsAsXml() + xml + "\n</camelRouteCoverage>";
File file = new File(dir);
file.mkdirs();
file = new File(dir, name);


log.info("dumping route coverage to file");
}
}

};