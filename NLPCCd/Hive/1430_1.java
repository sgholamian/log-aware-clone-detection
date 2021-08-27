//,temp,sample_5342.java,2,9,temp,sample_3706.java,2,10
//,3
public class xxx {
private static boolean createChildColumnRef(Tree child, String alias, List<ASTNode> newChildren, HashSet<String> aliases) {
String colAlias = child.getText();
if (!aliases.add(colAlias)) {


log.info("replacing setcolref with allcolref because of duplicate alias");
}
}

};