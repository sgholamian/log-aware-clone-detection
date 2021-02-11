//,temp,sample_4295.java,2,13,temp,sample_4296.java,2,12
//,3
public class xxx {
private boolean checkCompressNullPointerException( Decompressor decompressor, byte[] rawData) {
try {
decompressor.setInput(rawData, 0, rawData.length);
decompressor.decompress(null, 0, 1);
} catch (NullPointerException npe) {
return true;
} catch (Exception ex) {


log.info("checkcompressnullpointerexception error");
}
}

};