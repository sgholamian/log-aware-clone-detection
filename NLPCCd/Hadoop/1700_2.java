//,temp,sample_4295.java,2,13,temp,sample_4296.java,2,12
//,3
public class xxx {
private boolean checkSetInputNullPointerException( Decompressor decompressor) {
try {
decompressor.setInput(null, 0, 1);
} catch (NullPointerException npe) {
return true;
} catch (Exception ex) {


log.info("checksetinputnullpointerexception error");
}
}

};