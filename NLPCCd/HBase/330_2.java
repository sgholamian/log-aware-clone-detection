//,temp,sample_4875.java,2,16,temp,sample_3007.java,2,18
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