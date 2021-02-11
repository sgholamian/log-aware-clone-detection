//,temp,sample_3110.java,2,10,temp,sample_3027.java,2,10
//,2
public class xxx {
public void remove(Node node) {
if (node==null) return;
if( node instanceof InnerNode ) {
throw new IllegalArgumentException( "Not allow to remove an inner node: "+NodeBase.getPath(node));
}


log.info("removing a node");
}

};