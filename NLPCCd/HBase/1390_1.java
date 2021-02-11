//,temp,sample_451.java,2,13,temp,sample_449.java,2,13
//,3
public class xxx {
public void stop(CoprocessorEnvironment env) throws IOException {
String fileName = null;
if (env instanceof MasterCoprocessorEnvironment) {
fileName = MASTER_FILE;
} else if (env instanceof RegionServerCoprocessorEnvironment) {
fileName = REGIONSERVER_FILE;
} else if (env instanceof RegionCoprocessorEnvironment) {


log.info("on regioncoprocessorenvironment");
}
}

};