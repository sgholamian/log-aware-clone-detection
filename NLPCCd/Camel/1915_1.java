//,temp,sample_1748.java,2,10,temp,sample_1746.java,2,10
//,2
public class xxx {
public final void removeFromHintMap(final DecodeHintType hintType) {
if (this.readerHintMap.containsKey(hintType)) {
this.readerHintMap.remove(hintType);
} else {


log.info("could not find decode hint type s in reader hint map");
}
}

};