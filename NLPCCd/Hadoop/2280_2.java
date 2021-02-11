//,temp,sample_4298.java,2,13,temp,sample_4297.java,2,12
//,3
public class xxx {
private boolean checkSetInputArrayIndexOutOfBoundsException( Compressor compressor) {
try {
compressor.setInput(new byte[] { (byte) 0 }, 0, -1);
} catch (ArrayIndexOutOfBoundsException e) {
return true;
} catch (Exception e) {


log.info("checksetinputarrayindexoutofboundsexception error");
}
}

};