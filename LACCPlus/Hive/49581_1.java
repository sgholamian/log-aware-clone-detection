//,temp,ParseDriver.java,144,167,temp,ParseDriver.java,110,139
//,3
public class xxx {
  public ASTNode parseHint(String command) throws ParseException {
    LOG.debug("Parsing hint: {}", command);

    GenericHiveLexer lexer = GenericHiveLexer.of(command, null);
    TokenRewriteStream tokens = new TokenRewriteStream(lexer);
    HintParser parser = new HintParser(tokens);
    parser.setTreeAdaptor(adaptor);
    HintParser.hint_return r = null;
    try {
      r = parser.hint();
    } catch (RecognitionException e) {
      throw new ParseException(parser.errors);
    }

    if (lexer.getErrors().size() == 0 && parser.errors.size() == 0) {
      LOG.debug("Parse Completed");
    } else if (lexer.getErrors().size() != 0) {
      throw new ParseException(lexer.getErrors());
    } else {
      throw new ParseException(parser.errors);
    }

    return (ASTNode) r.getTree();
  }

};