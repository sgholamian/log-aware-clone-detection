//,temp,sample_8226.java,2,17,temp,sample_1800.java,2,17
//,2
public class xxx {
public void dummy_method(){
if (vmIdList != null) {
vmIds = new String[vmIdList.size()];
vmIdList.toArray(vmIds);
String vmIdLogStr = "";
if ((vmIds != null) && (vmIds.length > 0)) {
vmIdLogStr = vmIds[0];
for (int i = 1; i < vmIds.length; i++) {
vmIdLogStr = vmIdLogStr + "," + vmIds[i];
}
}


log.info("got virtual machine ids");
}
}

};