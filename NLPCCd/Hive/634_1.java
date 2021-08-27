//,temp,sample_2667.java,2,9,temp,sample_18.java,2,9
//,3
public class xxx {
public String[] mapToHiveType(String clientTypeName) {
Collection<String> hiveTableType = clientToHiveMap.get(clientTypeName.toUpperCase());
if (hiveTableType == null) {


log.info("not supported client table type");
}
}

};