//,temp,sample_4354.java,2,17,temp,sample_2518.java,2,13
//,3
public class xxx {
public void dummy_method(){
if (endpoint.getRoom() == null) {
privateChat = chatManager.chatWith(JidCreate.entityBareFrom(endpoint.getChatId()));
} else {
final AndFilter packetFilter = new AndFilter(new StanzaTypeFilter(Presence.class));
connection.addSyncStanzaListener(this, packetFilter);
MultiUserChatManager mucm = MultiUserChatManager.getInstanceFor(connection);
muc = mucm.getMultiUserChat(JidCreate.entityBareFrom(endpoint.resolveRoom(connection)));
muc.addMessageListener(this);
MucEnterConfiguration mucc = muc.getEnterConfigurationBuilder(Resourcepart.from(endpoint.getNickname())) .requestNoHistory() .build();
muc.join(mucc);


log.info("joined room as");
}
}

};