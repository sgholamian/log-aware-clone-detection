//,temp,sample_5583.java,2,18,temp,sample_5580.java,2,18
//,2
public class xxx {
public void dummy_method(){
if (output != null) {
try {
output.close();
} catch (IOException e) {
}
}
if (in != null) {
try {
in.close();
} catch (IOException e) {


log.info("not able to close the inputstream");
}
}
}

};