//,temp,sample_2615.java,2,16,temp,sample_2616.java,2,17
//,3
public class xxx {
public void dummy_method(){
IndexInformation info = cache.get(mapId);
if (info == null || ((info != null) && isUnderConstruction(info))) {
return;
}
info = cache.remove(mapId);
if (info != null) {
totalMemoryUsed.addAndGet(-info.getSize());
if (!queue.remove(mapId)) {
}
} else {


log.info("map id not found in cache");
}
}

};