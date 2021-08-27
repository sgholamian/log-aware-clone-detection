//,temp,sample_2810.java,2,16,temp,sample_2809.java,2,17
//,3
public class xxx {
public void dummy_method(){
freezeArgs.setWaittime(3600);
try {
sc.actionFreeze(name, freezeArgs);
} catch (UnknownApplicationInstanceException ex) {
}
ActionDestroyArgs destroyArg = new ActionDestroyArgs();
destroyArg.force = true;
try {
sc.actionDestroy(name, destroyArg);
} catch (UnknownApplicationInstanceException ex) {


log.info("there was no old application instance to destroy");
}
}

};