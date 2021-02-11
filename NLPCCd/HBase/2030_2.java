//,temp,sample_1864.java,2,18,temp,sample_5719.java,2,18
//,3
public class xxx {
public void dummy_method(){
boolean succeed = false;
boolean interrupted = false;
try {
context = pendingDelete.take();
if (context != null) {
FileStatus toClean = context.getTargetToClean();
succeed = this.fs.delete(toClean.getPath(), false);
}
} catch (InterruptedException ite) {
if (context != null) {


log.info("interrupted while cleaning oldwals try to clean it next round");
}
}
}

};