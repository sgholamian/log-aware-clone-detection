//,temp,sample_7608.java,2,16,temp,sample_7609.java,2,16
//,2
public class xxx {
public void dummy_method(){
ModelDatabase db = new ModelDatabase();
NetworkVO network = mock(NetworkVO.class);
VirtualNetworkModel storageModel = new VirtualNetworkModel(network, null, ContrailManager.managementNetworkName, TrafficType.Storage);
db.getVirtualNetworks().add(storageModel);
VirtualNetworkModel mgmtModel = new VirtualNetworkModel(network, null, ContrailManager.managementNetworkName, TrafficType.Management);
db.getVirtualNetworks().add(mgmtModel);
VirtualNetworkModel guestModel1 = new VirtualNetworkModel(network, UUID.randomUUID().toString(), "test", TrafficType.Guest);
db.getVirtualNetworks().add(guestModel1);
VirtualNetworkModel guestModel2 = new VirtualNetworkModel(network, UUID.randomUUID().toString(), "test", TrafficType.Guest);
db.getVirtualNetworks().add(guestModel2);


log.info("networks");
}

};