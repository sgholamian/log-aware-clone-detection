//,temp,sample_2439.java,2,18,temp,sample_2440.java,2,18
//,2
public class xxx {
public void dummy_method(){
if (fic != null) {
try {
fic.close();
} catch (IOException e) {
}
}
if (foc != null) {
try {
foc.close();
} catch (IOException e) {


log.info("ignore error while closing file output channel");
}
}
}

};