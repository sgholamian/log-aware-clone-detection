//,temp,sample_451.java,2,13,temp,sample_449.java,2,13
//,3
public class xxx {
public void start(CoprocessorEnvironment env) throws IOException {
String where = null;
if (env instanceof MasterCoprocessorEnvironment) {
where = "master";
} else if (env instanceof RegionServerCoprocessorEnvironment) {
where = "regionserver";
} else if (env instanceof RegionCoprocessorEnvironment) {


log.info("on regioncoprocessorenvironment");
}
}

};