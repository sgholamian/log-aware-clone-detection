//,temp,sample_1312.java,2,13,temp,sample_1313.java,2,13
//,2
public class xxx {
public void init() {
String exp = expression;
if (predicate && isAllowEasyPredicate()) {
EasyPredicateParser parser = new EasyPredicateParser();
exp = parser.parse(expression);
if (!exp.equals(expression)) {


log.info("easypredicateparser parsed");
}
}
}

};