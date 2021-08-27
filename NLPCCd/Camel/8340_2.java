//,temp,sample_3139.java,2,12,temp,sample_1156.java,2,12
//,2
public class xxx {
public static Object assertExpression(Expression expression, Exchange exchange, Object expected) {
Object value;
if (expected != null) {
value = expression.evaluate(exchange, expected.getClass());
} else {
value = expression.evaluate(exchange, Object.class);
}


log.info("evaluated expression on exchange result");
}

};