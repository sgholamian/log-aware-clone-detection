//,temp,sample_2326.java,2,17,temp,sample_2324.java,2,17
//,3
public class xxx {
public void dummy_method(){
HintParser parser = new HintParser(tokens);
parser.setTreeAdaptor(adaptor);
HintParser.hint_return r = null;
try {
r = parser.hint();
} catch (RecognitionException e) {
e.printStackTrace();
throw new ParseException(parser.errors);
}
if (lexer.getErrors().size() == 0 && parser.errors.size() == 0) {


log.info("parse completed");
}
}

};