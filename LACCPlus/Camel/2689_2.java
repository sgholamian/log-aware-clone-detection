//,temp,BarcodeDataFormat.java,268,275,temp,BarcodeDataFormat.java,256,263
//,2
public class xxx {
    public final void removeFromHintMap(final EncodeHintType hintType) {
        if (this.writerHintMap.containsKey(hintType)) {
            this.writerHintMap.remove(hintType);
            LOG.info(String.format("Removed '%s' from writer hint map.", hintType.toString()));
        } else {
            LOG.warn(String.format("Could not find encode hint type '%s' in writer hint map.", hintType.toString()));
        }
    }

};