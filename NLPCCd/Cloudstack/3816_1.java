//,temp,sample_8915.java,2,18,temp,sample_8914.java,2,18
//,2
public class xxx {
public void dummy_method(){
if (os != null) {
try {
os.close();
} catch (Throwable e) {
}
}
if (socket != null) {
try {
socket.close();
} catch (Throwable e) {


log.info("ignored failed to get close resource for socket");
}
}
}

};