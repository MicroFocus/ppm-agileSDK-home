package com.hp.ppm.integration.sample;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.hp.ppm.integration.ValueSet;
import com.hp.ppm.integration.pm.IExternalTask;
import com.hp.ppm.integration.pm.IExternalWorkPlan;
import com.hp.ppm.integration.pm.ITaskActual;
import com.hp.ppm.integration.pm.WorkPlanIntegration;
import com.hp.ppm.integration.pm.WorkPlanIntegrationContext;
import com.hp.ppm.integration.ui.Field;
import com.hp.ppm.integration.ui.PlainText;
import com.hp.ppm.integration.ui.SelectList;
import com.hp.ppm.integration.ui.SelectList.Option;

public class SampleWorkPlanIntegration implements WorkPlanIntegration {

	@Override
	public List<Field> getMappingConfigurationFields(WorkPlanIntegrationContext context,ValueSet values) {

		SelectList list = new SelectList();
		list.addLevel("Entity", "EXTERNAL_ENTITY");

		list.addOption(new Option("3000","Sample Entity 0"));
		list.addOption(new Option("3001","Sample Entity 1"));
		list.addOption(new Option("3002","Sample Entity 2"));

		return Arrays.asList(new Field[]{
			new PlainText("Label","XXXXX","",true),
			new PlainText("Label","XXXXX","",true),
			new PlainText("Label","XXXXX","",true),
			list
		});
	}


	@Override
	public boolean linkTaskWithExternal(WorkPlanIntegrationContext context,ValueSet values) {
		return false;
	}

	@Override
	public IExternalWorkPlan getExternalWorkPlan(WorkPlanIntegrationContext context,ValueSet values) {

		return new IExternalWorkPlan(){

			@Override
			public List<IExternalTask> getRootTasks() {
				return Arrays.asList(new IExternalTask[]{
					new IExternalTask(){

						@Override
						public String getId() {
							return "x";
						}

						@Override
						public String getName() {
							return "Sample Task " + (int)(Math.random() * 10000);
						}

						@Override
						public TaskStatus getStatus() {
							return TaskStatus.READY;
						}

						@Override
						public Date getScheduleStart() {
							return new Date();
						}

						@Override
						public Date getScheduleFinish() {
							return new Date();
						}

						@Override
						public long getOwnerId() {
							return -1;
						}

						@Override
						public String getOwnerRole() {
							return null;
						}

						@Override
						public List<IExternalTask> getChildren() {
							return Arrays.asList(new IExternalTask[]{
								new IExternalTask(){

									@Override
									public String getId() {
										return "y";
									}

									@Override
									public String getName() {
										return "Sample Sub Task 1 " + (int)(Math.random() * 10000);
									}

									@Override
									public TaskStatus getStatus() {
										return TaskStatus.READY;
									}

									@Override
									public Date getScheduleStart() {
										return new Date();
									}

									@Override
									public Date getScheduleFinish() {
										return null;
									}

									@Override
									public long getOwnerId() {
										return -1;
									}

									@Override
									public String getOwnerRole() {
										return null;
									}

									@Override
									public List<IExternalTask> getChildren() {
										return null;
									}

									@Override
									public List<ITaskActual> getActuals() {
										List<ITaskActual> actuals = new LinkedList<ITaskActual>();
										actuals.add(new ITaskActual(){

											@Override
											public Date getActualStart() {

												return new Date();
											}

											@Override
											public Date getActualFinish() {
												return new Date();
											}

											@Override
											public double getActualEfforts() {
												return 3;
											}

											@Override
											public float getCompletePercent() {
												return 0.31f;
											}

											@Override
											public long getResourceId() {
												return -1;
											}

											@Override
											public double getScheduleEfforts() {
												return 0;
											}

										});

										return actuals;
									}

									@Override
									public boolean isMilestone() {
										// TODO Auto-generated method stub
										return false;
									}
								}
							});
						}

						@Override
						public List<ITaskActual> getActuals() {
							return null;
						}

						@Override
						public boolean isMilestone() {
							// TODO Auto-generated method stub
							return false;
						}
					}
				});
			}

		};
	}


	@Override
	public boolean unlinkTaskWithExternal(WorkPlanIntegrationContext context,ValueSet values) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public String getCustomDetailPage() {
		// TODO Auto-generated method stub
		return null;
	}

}
