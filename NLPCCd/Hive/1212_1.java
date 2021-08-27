//,temp,sample_5397.java,2,18,temp,sample_1160.java,2,13
//,3
public class xxx {
public void dummy_method(){
Exception caughtException = null;
try {
din.close();
} catch (Exception err) {
caughtException = err;
}
if (client != null) {
try {
client.close();
} catch (Exception err) {


log.info("error closing client");
}
}
}

};