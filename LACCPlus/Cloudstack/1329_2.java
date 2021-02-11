//,temp,VirtualMachineMO.java,3101,3191,temp,VirtualMachineMO.java,201,275
//,3
public class xxx {
    public boolean powerOn() throws Exception {
        if (getResetSafePowerState() == VirtualMachinePowerState.POWERED_ON)
            return true;

        ManagedObjectReference morTask = _context.getService().powerOnVMTask(_mor, null);
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
                            s_logger.info("Question id: " + question.getId());
                            s_logger.info("Question text: " + question.getText());
                            if (question.getMessage() != null) {
                                for (VirtualMachineMessage msg : question.getMessage()) {
                                    if (s_logger.isInfoEnabled()) {
                                        s_logger.info("msg id: " + msg.getId());
                                        s_logger.info("msg text: " + msg.getText());
                                    }
                                    if ("msg.uuid.altered".equalsIgnoreCase(msg.getId())) {
                                        s_logger.info("Found that VM has a pending question that we need to answer programmatically, question id: " + msg.getId()
                                                + ", we will automatically answer as 'moved it' to address out of band HA for the VM");
                                        vmMo.answerVM(question.getId(), "1");
                                        break;
                                    }
                                }
                            }

                            if (s_logger.isTraceEnabled())
                                s_logger.trace("These are the choices we can have just in case");
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
                        s_logger.debug("[ignored] interupted while dealing with vm questions.");
                    }
                }
                s_logger.info("VM Question monitor stopped");
            }
        });

        try {
            boolean result = _context.getVimClient().waitForTask(morTask);
            if (result) {
                _context.waitForTaskProgressDone(morTask);
                return true;
            } else {
                s_logger.error("VMware powerOnVM_Task failed due to " + TaskMO.getTaskFailureInfo(_context, morTask));
            }
        } finally {
            // make sure to let VM question monitor exit
            flags[0] = true;
        }

        return false;
    }

};