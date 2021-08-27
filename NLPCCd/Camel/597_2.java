//,temp,sample_3586.java,2,7,temp,sample_3587.java,2,8
//,3
public class xxx {
public void execute() {
SelectRequest request = new SelectRequest() .withSelectExpression(determineSelectExpression()) .withConsistentRead(determineConsistentRead()) .withNextToken(determineNextToken());
SelectResult result = this.sdbClient.select(request);


log.info("received result");
}

};