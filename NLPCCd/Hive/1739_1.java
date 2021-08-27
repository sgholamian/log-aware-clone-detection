//,temp,sample_2326.java,2,17,temp,sample_2324.java,2,17
//,3
public class xxx {
public void dummy_method(){
HiveParser parser = new HiveParser(tokens);
parser.setTreeAdaptor(adaptor);
HiveParser_SelectClauseParser.selectClause_return r = null;
try {
r = parser.selectClause();
} catch (RecognitionException e) {
e.printStackTrace();
throw new ParseException(parser.errors);
}
if (lexer.getErrors().size() == 0 && parser.errors.size() == 0) {


log.info("parse completed");
}
}

};