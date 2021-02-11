//,temp,sample_4482.java,2,19,temp,sample_4481.java,2,19
//,3
public class xxx {
public void dummy_method(){
while (true) {
try {
context = queue.take();
if (!deletePath(context)) {
}
else if (LOG.isDebugEnabled()) {
}
} catch (InterruptedException t) {
if (context == null) {
} else {


log.info("interrupted deletion of");
}
}
}
}

};