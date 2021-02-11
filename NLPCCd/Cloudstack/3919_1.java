//,temp,sample_414.java,2,17,temp,sample_413.java,2,17
//,2
public class xxx {
public void dummy_method(){
List<String> allVlans = getStrippedVlans();
if (!allVlans.contains(vlanName)) {
String[] vlanNames = genStringArray(vlanName);
long[] vlanTags = genLongArray(vlanTag);
CommonEnabledState[] commonEnabledState = {CommonEnabledState.STATE_DISABLED};
NetworkingVLANMemberEntry[][] vlanMemberEntries = {{new NetworkingVLANMemberEntry()}};
vlanMemberEntries[0][0].setMember_type(NetworkingMemberType.MEMBER_INTERFACE);
vlanMemberEntries[0][0].setTag_state(NetworkingMemberTagType.MEMBER_TAGGED);
vlanMemberEntries[0][0].setMember_name(_privateInterface);
_vlanApi.create(vlanNames, vlanTags, vlanMemberEntries, commonEnabledState, new long[] {10L}, new String[] {"00:00:00:00:00:00"});


log.info("getstrippedvlans");
}
}

};