//,temp,sample_2552.java,2,16,temp,sample_2548.java,2,16
//,3
public class xxx {
public void dummy_method(){
if (packageNames == null) {
return Collections.emptySet();
}
if (log.isDebugEnabled()) {
}
PackageScanFilter test = getCompositeFilter(new AssignableToPackageScanFilter(parent));
Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
for (String pkg : packageNames) {
find(test, pkg, classes);
}


log.info("found");
}

};