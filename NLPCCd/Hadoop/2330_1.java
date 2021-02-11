//,temp,sample_6572.java,2,16,temp,sample_6574.java,2,16
//,3
public class xxx {
public void dummy_method(){
doAnEdit(fsn, 1);
fsn.setSafeMode(SafeModeAction.SAFEMODE_ENTER);
fsn.saveNamespace();
assertTrue("Savenamespace should have marked one directory as bad." + " But found " + storage.getRemovedStorageDirs().size() + " bad directories.", storage.getRemovedStorageDirs().size() == 1);
fs.setPermission(rootPath, permissionAll);
fsn.saveNamespace();
assertTrue("Savenamespace should have been successful in removing " + " bad directories from Image."  + " But found " + storage.getRemovedStorageDirs().size() + " bad directories.", storage.getRemovedStorageDirs().size() == 0);
originalImage.close();
fsn.close();
fsn = null;


log.info("loading new fsmage from disk");
}

};