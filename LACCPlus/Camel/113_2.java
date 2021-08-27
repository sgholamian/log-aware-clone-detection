//,temp,MentionsConsumerHandler.java,35,40,temp,HomeConsumerHandler.java,35,40
//,2
public class xxx {
    @Override
    protected List<Status> doPoll() throws TwitterException {
        Paging paging = getLastIdPaging();
        log.trace("doPoll.getHomeTimeline(sinceId={})", paging.getSinceId());
        return getTwitter().getHomeTimeline(paging);
    }

};