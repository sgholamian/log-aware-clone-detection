//,temp,sample_4220.java,2,16,temp,sample_4219.java,2,12
//,3
public class xxx {
public static Set<String> getSQLCompleters(BeeLine beeLine, boolean skipmeta) throws IOException, SQLException {
Set<String> completions = new TreeSet<String>();
String keywords = new BufferedReader(new InputStreamReader( SQLCompleter.class.getResourceAsStream( "/sql-keywords.properties"))).readLine();
try {
keywords += "," + beeLine.getDatabaseConnection().getDatabaseMetaData().getSQLKeywords();
} catch (Exception e) {
}
try {
keywords += "," + beeLine.getDatabaseConnection().getDatabaseMetaData().getStringFunctions();
} catch (Exception e) {


log.info("fail to get string function names from database metadata due to the exception");
}
}

};