//,temp,UserConsumerHandler.java,38,43,temp,UserListConsumerHandler.java,37,42
//,3
public class xxx {
    @Override
    protected List<Status> doPoll() throws TwitterException {
        Paging paging = getLastIdPaging();
        log.trace("doPoll.getUserTimeline(user={}, sinceId={})", user, paging.getSinceId());
        return getTwitter().getUserTimeline(user, paging);
    }

};