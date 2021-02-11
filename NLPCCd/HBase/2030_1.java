//,temp,sample_1864.java,2,18,temp,sample_5719.java,2,18
//,3
public class xxx {
public void dummy_method(){
try {
task = queue.take();
} catch (InterruptedException e) {
break;
}
if (task != null) {
boolean succeed;
try {
succeed = this.fs.delete(task.filePath, false);
} catch (IOException e) {


log.info("failed to delete file");
}
}
}

};