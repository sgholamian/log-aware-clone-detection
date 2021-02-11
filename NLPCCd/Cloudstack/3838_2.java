//,temp,sample_2441.java,2,18,temp,sample_2440.java,2,18
//,2
public class xxx {
public void dummy_method(){
if (foc != null) {
try {
foc.close();
} catch (IOException e) {
}
}
if (fis != null) {
try {
fis.close();
} catch (IOException e) {


log.info("ignore error while closing file input stream");
}
}
}

};