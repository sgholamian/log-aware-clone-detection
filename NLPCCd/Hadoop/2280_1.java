//,temp,sample_4298.java,2,13,temp,sample_4297.java,2,12
//,3
public class xxx {
private boolean checkCompressArrayIndexOutOfBoundsException( Compressor compressor, byte[] rawData) {
try {
compressor.setInput(rawData, 0, rawData.length);
compressor.compress(new byte[rawData.length], 0, -1);
} catch (ArrayIndexOutOfBoundsException e) {
return true;
} catch (Exception e) {


log.info("checkcompressarrayindexoutofboundsexception error");
}
}

};