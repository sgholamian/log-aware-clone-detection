//,temp,sample_5117.java,2,11,temp,sample_3545.java,2,12
//,3
public class xxx {
public void testExpressionWithHeaderVariable() throws Exception {
Expression expression = sql("SELECT * FROM org.apache.camel.builder.sql.Person where name = :fooHeader");
List<?> value = expression.evaluate(exchange, List.class);
assertEquals("List size", 1, value.size());
for (Object person : value) {


log.info("found");
}
}

};