//,temp,sample_1748.java,2,10,temp,sample_1746.java,2,10
//,2
public class xxx {
public final void removeFromHintMap(final EncodeHintType hintType) {
if (this.writerHintMap.containsKey(hintType)) {
this.writerHintMap.remove(hintType);
} else {


log.info("could not find encode hint type s in writer hint map");
}
}

};