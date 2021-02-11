//,temp,sample_7013.java,2,11,temp,sample_7014.java,2,12
//,3
public class xxx {
public synchronized byte[] getImage(int key) {
if (key == 0) {
key = nextKey;
}
if (cache.containsKey(key)) {
return cache.get(key);
}


log.info("image is no long in cache key");
}

};