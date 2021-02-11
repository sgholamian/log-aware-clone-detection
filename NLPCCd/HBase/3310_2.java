//,temp,sample_3016.java,2,19,temp,sample_3011.java,2,18
//,3
public class xxx {
public void dummy_method(){
if (fileInputStream != null) {
try {
fileInputStream.close();
} catch (Exception e) {
}
}
if (bufferedInputStream != null) {
try {
bufferedInputStream.close();
} catch (Exception e) {


log.info("error closing bufferedinputstream");
}
}
}

};