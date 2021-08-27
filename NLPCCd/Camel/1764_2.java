//,temp,sample_3140.java,2,10,temp,sample_1157.java,2,10
//,2
public class xxx {
public static void assertPredicateDoesNotMatch(Predicate predicate, Exchange exchange) {
try {
PredicateAssertHelper.assertMatches(predicate, "Predicate should match: ", exchange);
} catch (AssertionError e) {


log.info("caught expected assertion error");
}
}

};