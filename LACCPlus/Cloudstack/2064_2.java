//,temp,VirtualMachineMO.java,3101,3191,temp,VirtualMachineMO.java,1559,1669
//,3
public class xxx {
    public int detachIso(String isoDatastorePath, final boolean force) throws Exception {
        if (s_logger.isTraceEnabled())
            s_logger.trace("vCenter API trace - detachIso(). target MOR: " + _mor.getValue() + ", isoDatastorePath: " + isoDatastorePath);

        VirtualDevice device = getIsoDevice(isoDatastorePath);
        if (device == null) {
            if (s_logger.isTraceEnabled())
                s_logger.trace("vCenter API trace - detachIso() done(failed)");
            throw new Exception("Unable to find a CDROM device");
        }

        VirtualCdromRemotePassthroughBackingInfo backingInfo = new VirtualCdromRemotePassthroughBackingInfo();
        backingInfo.setDeviceName("");
        device.setBacking(backingInfo);

        VirtualMachineConfigSpec reConfigSpec = new VirtualMachineConfigSpec();
        //VirtualDeviceConfigSpec[] deviceConfigSpecArray = new VirtualDeviceConfigSpec[1];
        VirtualDeviceConfigSpec deviceConfigSpec = new VirtualDeviceConfigSpec();

        deviceConfigSpec.setDevice(device);
        deviceConfigSpec.setOperation(VirtualDeviceConfigSpecOperation.EDIT);

        //deviceConfigSpecArray[0] = deviceConfigSpec;
        reConfigSpec.getDeviceChange().add(deviceConfigSpec);

        ManagedObjectReference morTask = _context.getService().reconfigVMTask(_mor, reConfigSpec);

        // Monitor VM questions
        final Boolean[] flags = {false};
        final VirtualMachineMO vmMo = this;
        Future<?> future = MonitorServiceExecutor.submit(new Runnable() {
            @Override
            public void run() {
                s_logger.info("VM Question monitor started...");

                while (!flags[0]) {
                    try {
                        VirtualMachineRuntimeInfo runtimeInfo = vmMo.getRuntimeInfo();
                        VirtualMachineQuestionInfo question = runtimeInfo.getQuestion();
                        if (question != null) {
                            if (s_logger.isTraceEnabled()) {
                                s_logger.trace("Question id: " + question.getId());
                                s_logger.trace("Question text: " + question.getText());
                            }
                            if (question.getMessage() != null) {
                                for (VirtualMachineMessage msg : question.getMessage()) {
                                    if (s_logger.isTraceEnabled()) {
                                        s_logger.trace("msg id: " + msg.getId());
                                        s_logger.trace("msg text: " + msg.getText());
                                    }
                                    if ("msg.cdromdisconnect.locked".equalsIgnoreCase(msg.getId())) {
                                        s_logger.info("Found that VM has a pending question that we need to answer programmatically, question id: " + msg.getId() +
                                                ", for safe operation we will automatically decline it");
                                        vmMo.answerVM(question.getId(), force ? ANSWER_YES : ANSWER_NO);
                                        break;
                                    }
                                }
                            } else if (question.getText() != null) {
                                String text = question.getText();
                                String msgId;
                                String msgText;
                                if (s_logger.isDebugEnabled()) {
                                    s_logger.debug("question text : " + text);
                                }
                                String[] tokens = text.split(":");
                                msgId = tokens[0];
                                msgText = tokens[1];
                                if ("msg.cdromdisconnect.locked".equalsIgnoreCase(msgId)) {
                                    s_logger.info("Found that VM has a pending question that we need to answer programmatically, question id: " + question.getId() +
                                            ". Message id : " + msgId + ". Message text : " + msgText + ", for safe operation we will automatically decline it.");
                                    vmMo.answerVM(question.getId(), force ? ANSWER_YES : ANSWER_NO);
                                }
                            }

                            ChoiceOption choice = question.getChoice();
                            if (choice != null) {
                                for (ElementDescription info : choice.getChoiceInfo()) {
                                    if (s_logger.isTraceEnabled()) {
                                        s_logger.trace("Choice option key: " + info.getKey());
                                        s_logger.trace("Choice option label: " + info.getLabel());
                                    }
                                }
                            }
                        }
                    } catch (Throwable e) {
                        s_logger.error("Unexpected exception: ", e);
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        s_logger.debug("[ignored] interupted while handling vm question about iso detach.");
                    }
                }
                s_logger.info("VM Question monitor stopped");
            }
        });
        try {
            boolean result = _context.getVimClient().waitForTask(morTask);
            if (!result) {
                if (s_logger.isDebugEnabled())
                    s_logger.trace("vCenter API trace - detachIso() done(failed)");
                throw new Exception("Failed to detachIso due to " + TaskMO.getTaskFailureInfo(_context, morTask));
            }
            _context.waitForTaskProgressDone(morTask);
            s_logger.trace("vCenter API trace - detachIso() done(successfully)");
        } finally {
            flags[0] = true;
            future.cancel(true);
        }
        return device.getKey();
    }

};