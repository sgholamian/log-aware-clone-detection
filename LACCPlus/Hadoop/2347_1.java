//,temp,RpcClient.java,305,341,temp,RpcClient.java,169,211
//,3
public class xxx {
  @Override
  public void createBucket(
      String volumeName, String bucketName, BucketArgs bucketArgs)
      throws IOException {
    HddsClientUtils.verifyResourceName(volumeName, bucketName);
    Preconditions.checkNotNull(bucketArgs);

    Boolean isVersionEnabled = bucketArgs.getVersioning() == null ?
        Boolean.FALSE : bucketArgs.getVersioning();
    StorageType storageType = bucketArgs.getStorageType() == null ?
        StorageType.DEFAULT : bucketArgs.getStorageType();
    List<OzoneAcl> listOfAcls = new ArrayList<>();
    //User ACL
    listOfAcls.add(new OzoneAcl(OzoneAcl.OzoneACLType.USER,
        ugi.getUserName(), userRights));
    //Group ACLs of the User
    List<String> userGroups = Arrays.asList(UserGroupInformation
        .createRemoteUser(ugi.getUserName()).getGroupNames());
    userGroups.stream().forEach((group) -> listOfAcls.add(
        new OzoneAcl(OzoneAcl.OzoneACLType.GROUP, group, groupRights)));
    //ACLs from BucketArgs
    if(bucketArgs.getAcls() != null) {
      listOfAcls.addAll(bucketArgs.getAcls());
    }

    OmBucketInfo.Builder builder = OmBucketInfo.newBuilder();
    builder.setVolumeName(volumeName)
        .setBucketName(bucketName)
        .setIsVersionEnabled(isVersionEnabled)
        .setStorageType(storageType)
        .setAcls(listOfAcls.stream().distinct().collect(Collectors.toList()));

    LOG.info("Creating Bucket: {}/{}, with Versioning {} and " +
            "Storage Type set to {}", volumeName, bucketName, isVersionEnabled,
            storageType);
    ozoneManagerClient.createBucket(builder.build());
  }

};