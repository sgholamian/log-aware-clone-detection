//,temp,sample_7793.java,2,17,temp,sample_3993.java,2,16
//,3
public class xxx {
public void dummy_method(){
Map<String, String> initialCachedResources = getInitialCachedResources(FileSystem.get(conf), conf);
Iterator<Map.Entry<String, String>> it = initialCachedResources.entrySet().iterator();
while (it.hasNext()) {
Map.Entry<String, String> e = it.next();
String key = intern(e.getKey());
String fileName = e.getValue();
SharedCacheResource resource = new SharedCacheResource(fileName);
cachedResources.put(key, resource);
it.remove();
}


log.info("bootstrapping complete");
}

};