//,temp,sample_7142.java,2,11,temp,sample_7144.java,2,11
//,3
public class xxx {
public List<Exchange> directConsume() throws TwitterException {
String keywords = this.keywords;
if (keywords == null || keywords.trim().length() == 0) {
return Collections.emptyList();
}
Query query = new Query(keywords);


log.info("searching twitter with keywords");
}

};