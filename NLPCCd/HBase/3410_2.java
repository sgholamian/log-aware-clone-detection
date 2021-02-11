//,temp,sample_2926.java,2,18,temp,sample_2924.java,2,18
//,3
public class xxx {
public void dummy_method(){
if (subproc == null) {
return false;
}
String procName = subproc.getName();
if (procName == null || procName.length() == 0) {
return false;
}
Subprocedure rsub = subprocs.get(procName);
if (rsub != null) {
if (!rsub.isComplete()) {


log.info("subproc is already running bailing out");
}
}
}

};