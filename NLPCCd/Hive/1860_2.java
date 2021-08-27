//,temp,sample_3184.java,2,17,temp,sample_3183.java,2,14
//,3
public class xxx {
public int startVectorizedBatch(int size) throws IOException, HiveException {
if (!isEnabled) {
return FORWARD;
} else if (topN == 0) {
return EXCLUDE;
}
if (usage > threshold) {
int excluded = this.excluded;


log.info("top n hash is flushing rows");
}
}

};