//,temp,sample_871.java,2,9,temp,sample_870.java,2,9
//,3
public class xxx {
public static Map<String, Map<String, Table>> getTempTables() {
SessionState ss = SessionState.get();
if (ss == null) {


log.info("no current sessionstate skipping temp tables");
}
}

};