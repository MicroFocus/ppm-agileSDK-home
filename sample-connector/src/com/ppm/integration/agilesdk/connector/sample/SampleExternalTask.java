package com.ppm.integration.agilesdk.connector.sample;

import com.ppm.integration.agilesdk.pm.ExternalTask;
import com.ppm.integration.agilesdk.pm.ExternalTaskActuals;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


public class SampleExternalTask extends ExternalTask {
    private SampleTaskData octData=null;
    private TaskStatus status=TaskStatus.UNKNOWN;
    public SampleExternalTask(SampleTaskData octData, TaskStatus statue) {
        this.octData=octData;
        this.status=statue;
    }

    @Override
    public String getId() {
        return octData.strValue("id");
    }

    @Override
    public String getName() {
        return octData.strValue("name");
    }

    @Override
    public TaskStatus getStatus() {
        return status;
    }

    @Override
    public Date getScheduledStart() {
        return octData.dateValue("start-date", new Date(116,9,01));
    }

    @Override
    public Date getScheduledFinish() {
        return octData.dateValue("end-date", new Date(116,9,06));
    }

    @Override
    public String getOwnerRole() {
        return  octData.strValue("owner-role");
    }

    @Override
    public List<ExternalTaskActuals> getActuals() {
        return null;
    }

    @Override
    public long getOwnerId() {
        return octData.intValue("owner-id",-1);
    }

    @Override
    public boolean isMilestone() {
        return false;
    }

    @Override
    public List<ExternalTask> getChildren() {
        return null;
    }

}
