//,temp,sample_4426.java,2,12,temp,sample_4425.java,2,12
//,2
public class xxx {
public byte[] getEndKey() {
if (this.metaEntry != null) {
return this.metaEntry.getEndKey();
} else if (this.hdfsEntry != null) {
return this.hdfsEntry.hri.getEndKey();
} else {


log.info("entry has no meta or hdfs region start key");
}
}

};