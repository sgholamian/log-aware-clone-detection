//,temp,sample_2240.java,2,15,temp,sample_3092.java,2,15
//,3
public class xxx {
protected Object evaluateExpression(String expressionText, String expectedValue) {
Language language = assertResolveLanguage(getLanguageName());
Expression expression = language.createExpression(expressionText);
assertNotNull("No Expression could be created for text: " + expressionText + " language: " + language, expression);
Object value;
if (expectedValue != null) {
value = expression.evaluate(exchange, expectedValue.getClass());
} else {
value = expression.evaluate(exchange, Object.class);
}


log.info("evaluated expression on exchange result");
}

};