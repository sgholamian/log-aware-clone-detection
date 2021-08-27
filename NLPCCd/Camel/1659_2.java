//,temp,sample_4354.java,2,17,temp,sample_2518.java,2,13
//,3
public class xxx {
protected synchronized void initializeChat() throws InterruptedException, SmackException, XMPPException, XmppStringprepException {
if (chat == null) {
room = endpoint.resolveRoom(connection);
MultiUserChatManager chatManager = MultiUserChatManager.getInstanceFor(connection);
chat = chatManager.getMultiUserChat(JidCreate.entityBareFrom(room));
MucEnterConfiguration mucc = chat.getEnterConfigurationBuilder(Resourcepart.from(endpoint.getNickname())) .requestNoHistory() .build();
chat.join(mucc);


log.info("joined room as");
}
}

};