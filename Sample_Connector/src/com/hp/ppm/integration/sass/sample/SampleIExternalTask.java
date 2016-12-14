package com.hp.ppm.integration.sass.sample;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.hp.ppm.integration.pm.IExternalTask;
import com.hp.ppm.integration.pm.ITaskActual;
import com.hp.ppm.integration.pm.IExternalTask.TaskStatus;

public class SampleIExternalTask implements IExternalTask{
	private SampleTaskData octData=null;
	private TaskStatus status=TaskStatus.UNKNOWN;
	public SampleIExternalTask(SampleTaskData octData, TaskStatus statue) {
		this.octData=octData;
		this.status=statue;
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return octData.strValue("id");
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return octData.strValue("name");
	}

	@Override
	public TaskStatus getStatus() {
		// TODO Auto-generated method stub
		return status;
	}

	@Override
	public Date getScheduleStart() {
		// TODO Auto-generated method stub
		return octData.dateValue("start-date", new Date(116,9,01));
	}

	@Override
	public Date getScheduleFinish() {
		// TODO Auto-generated method stub
		return octData.dateValue("end-date", new Date(116,9,06));
	}

	@Override
	public String getOwnerRole() {
		// TODO Auto-generated method stub
		return  octData.strValue("owner-role");
	}

	@Override
	public List<ITaskActual> getActuals() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getOwnerId() {
		// TODO Auto-generated method stub
		return octData.intValue("owner-id",-1);
	}

	@Override
	public boolean isMilestone() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<IExternalTask> getChildren() {
		// TODO Auto-generated method stub
		return null;
	}

}
