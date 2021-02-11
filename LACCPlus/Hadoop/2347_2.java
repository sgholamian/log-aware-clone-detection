//,temp,RpcClient.java,305,341,temp,RpcClient.java,169,211
//,3
public class xxx {
  @Override
  public void createVolume(String volumeName, VolumeArgs volArgs)
      throws IOException {
    HddsClientUtils.verifyResourceName(volumeName);
    Preconditions.checkNotNull(volArgs);

    String admin = volArgs.getAdmin() == null ?
        ugi.getUserName() : volArgs.getAdmin();
    String owner = volArgs.getOwner() == null ?
        ugi.getUserName() : volArgs.getOwner();
    long quota = volArgs.getQuota() == null ?
        OzoneConsts.MAX_QUOTA_IN_BYTES :
        OzoneQuota.parseQuota(volArgs.getQuota()).sizeInBytes();
    List<OzoneAcl> listOfAcls = new ArrayList<>();
    //User ACL
    listOfAcls.add(new OzoneAcl(OzoneAcl.OzoneACLType.USER,
            owner, userRights));
    //Group ACLs of the User
    List<String> userGroups = Arrays.asList(UserGroupInformation
        .createRemoteUser(owner).getGroupNames());
    userGroups.stream().forEach((group) -> listOfAcls.add(
        new OzoneAcl(OzoneAcl.OzoneACLType.GROUP, group, groupRights)));
    //ACLs from VolumeArgs
    if(volArgs.getAcls() != null) {
      listOfAcls.addAll(volArgs.getAcls());
    }

    OmVolumeArgs.Builder builder = OmVolumeArgs.newBuilder();
    builder.setVolume(volumeName);
    builder.setAdminName(admin);
    builder.setOwnerName(owner);
    builder.setQuotaInBytes(quota);

    //Remove duplicates and add ACLs
    for (OzoneAcl ozoneAcl :
        listOfAcls.stream().distinct().collect(Collectors.toList())) {
      builder.addOzoneAcls(OMPBHelper.convertOzoneAcl(ozoneAcl));
    }

    LOG.info("Creating Volume: {}, with {} as owner and quota set to {} bytes.",
        volumeName, owner, quota);
    ozoneManagerClient.createVolume(builder.build());
  }

};