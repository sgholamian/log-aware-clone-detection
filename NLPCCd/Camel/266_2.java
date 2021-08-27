//,temp,sample_5117.java,2,11,temp,sample_3545.java,2,12
//,3
public class xxx {
public void testExpression() throws Exception {
Language language = assertResolveLanguage(getLanguageName());
Expression expression = language.createExpression("SELECT * FROM org.apache.camel.builder.sql.Person where city = 'London'");
List<?> value = expression.evaluate(exchange, List.class);
assertEquals("List size", 2, value.size());
for (Object person : value) {


log.info("found");
}
}

};