//,temp,sample_4482.java,2,19,temp,sample_4481.java,2,19
//,3
public class xxx {
public void dummy_method(){
PathDeletionContext context = null;
while (true) {
try {
context = queue.take();
if (!deletePath(context)) {
}
else if (LOG.isDebugEnabled()) {
}
} catch (InterruptedException t) {
if (context == null) {


log.info("interrupted deletion of an invalid path path deletion context is null");
}
}
}
}

};