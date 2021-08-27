//,temp,BarcodeDataFormat.java,268,275,temp,BarcodeDataFormat.java,256,263
//,2
public class xxx {
    public final void removeFromHintMap(final DecodeHintType hintType) {
        if (this.readerHintMap.containsKey(hintType)) {
            this.readerHintMap.remove(hintType);
            LOG.info(String.format("Removed '%s' from reader hint map.", hintType.toString()));
        } else {
            LOG.warn(String.format("Could not find decode hint type '%s' in reader hint map.", hintType.toString()));
        }
    }

};