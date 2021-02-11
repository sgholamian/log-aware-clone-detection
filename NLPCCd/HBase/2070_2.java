//,temp,sample_2341.java,2,11,temp,sample_3011.java,2,18
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