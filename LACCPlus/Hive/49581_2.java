//,temp,ParseDriver.java,144,167,temp,ParseDriver.java,110,139
//,3
public class xxx {
  public ParseResult parse(String command, Configuration configuration)
      throws ParseException {
    if (LOG.isDebugEnabled()) {
      LOG.debug("Parsing command: " + command);
    }

    GenericHiveLexer lexer = GenericHiveLexer.of(command, configuration);
    TokenRewriteStream tokens = new TokenRewriteStream(lexer);
    HiveParser parser = new HiveParser(tokens);
    parser.setTreeAdaptor(adaptor);
    parser.setHiveConf(configuration);
    ParserRuleReturnScope r;
    try {
      r = parser.statement();
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

    ASTNode tree = (ASTNode) r.getTree();
    tree.setUnknownTokenBoundaries();
    return new ParseResult(tree, tokens);
  }

};