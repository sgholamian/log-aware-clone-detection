//,temp,sample_5583.java,2,18,temp,sample_5576.java,2,18
//,2
public class xxx {
public void dummy_method(){
if (bufferedReader != null) {
try {
bufferedReader.close();
} catch (IOException e) {
}
}
if (inputStreamReader != null) {
try {
inputStreamReader.close();
} catch (IOException e) {


log.info("not able to close the inputstreamreader");
}
}
}

};