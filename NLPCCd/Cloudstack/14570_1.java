//,temp,sample_1666.java,2,12,temp,sample_1665.java,2,11
//,2
public class xxx {
private String prepareAjaxImage(List<TileInfo> tiles, boolean init) {
byte[] imgBits;
if (init) imgBits = getFrameBufferJpeg();
else imgBits = getTilesMergedJpeg(tiles, tracker.getTileWidth(), tracker.getTileHeight());
if (imgBits == null) {
} else {


log.info("generated jpeg image size");
}
}

};