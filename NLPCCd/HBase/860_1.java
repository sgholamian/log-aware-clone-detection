//,temp,sample_3007.java,2,18,temp,sample_4723.java,2,18
//,2
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