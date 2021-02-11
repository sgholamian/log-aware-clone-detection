//,temp,sample_5027.java,2,9,temp,sample_2798.java,2,10
//,3
public class xxx {
public int run(Path inputDir, int numMappers) throws Exception {
getConf().set(SEARCHER_INPUTDIR_KEY, inputDir.toString());
SortedSet<byte []> keys = readKeysToSearch(getConf());
if (keys.isEmpty()) throw new RuntimeException("No keys to find");


log.info("count of keys to find");
}

};