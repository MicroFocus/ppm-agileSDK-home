package com.ppm.integration.agilesdk.connector.sample;

import com.ppm.integration.agilesdk.ValueSet;
import com.ppm.integration.agilesdk.agiledata.*;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * This class provides {@link AgileDataIntegration} features to import Agile entities into the PPM DB to be later shown in the Agile Portlets of the PPM Dashboard.
 * All the data returned here is fake and for educational purposes only; in a real connector, you would retrieve all the infromation from the Agile tools, for example through a REST API.
 */
public class SampleAgileDataIntegration extends AgileDataIntegration {

    private Calendar nextMonday = null;

    private String projectId;

    public SampleAgileDataIntegration() {
        nextMonday = Calendar.getInstance();
        nextMonday.set(Calendar.MILLISECOND, 0);
        nextMonday.set(Calendar.SECOND, 0);
        nextMonday.set(Calendar.MINUTE, 0);
        nextMonday.set(Calendar.HOUR_OF_DAY, 8);

        while (nextMonday.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            nextMonday.add(Calendar.DATE, 1);
        }
    }

    @Override
    public void setUp(ValueSet instanceConfiguration, String projectId) {
        // If you can get performance improvement from retrieving all entities at once, you can put all your logic here.
        // Otherwise, you can put the business logic in each of the method returning the various agile entities (teams, releases, Epics, backlog items, etc.)
        this.projectId = projectId;
        // We don't make any use of the ValueSet parameter, but supposedly this is where you would get all the configuration information to connect to the Agile Tool.
    }

    @Override
    public List<AgileDataBacklogConfig> getAgileDataBacklogConfig(ValueSet instanceConfiguration) {
        AgileDataBacklogConfig bc_open_defect = new AgileDataBacklogConfig();
        bc_open_defect.setBacklogStatus("open");
        bc_open_defect.setBacklogType("defect");
        bc_open_defect.setIsFinishStatus(false);
        bc_open_defect.setColor("red");

        AgileDataBacklogConfig bc_closed_defect = new AgileDataBacklogConfig();
        bc_closed_defect.setBacklogStatus("closed");
        bc_closed_defect.setBacklogType("defect");
        bc_closed_defect.setIsFinishStatus(true);
        bc_closed_defect.setColor("blue");

        AgileDataBacklogConfig bc_not_done_story = new AgileDataBacklogConfig();
        bc_not_done_story.setBacklogStatus("not done");
        bc_not_done_story.setBacklogType("story");
        bc_not_done_story.setIsFinishStatus(false);
        bc_not_done_story.setColor("red");


        AgileDataBacklogConfig bc_done_story = new AgileDataBacklogConfig();
        bc_done_story.setBacklogStatus("done");
        bc_done_story.setBacklogType("story");
        bc_done_story.setIsFinishStatus(true);
        bc_done_story.setColor("green");

        return Arrays.asList(new AgileDataBacklogConfig[] {bc_open_defect, bc_closed_defect, bc_not_done_story, bc_done_story});

    }

    @Override
    public List<AgileDataBacklogSeverity> getAgileDataBacklogSeverity(ValueSet instanceConfiguration) {
        AgileDataBacklogSeverity defectSev1 = new AgileDataBacklogSeverity();
        defectSev1.setBacklogType("defect");
        defectSev1.setSeverity("Low");
        defectSev1.setSeverityIndex(1);

        AgileDataBacklogSeverity defectSev2 = new AgileDataBacklogSeverity();
        defectSev2.setBacklogType("defect");
        defectSev2.setSeverity("Medium");
        defectSev2.setSeverityIndex(2);

        AgileDataBacklogSeverity defectSev3 = new AgileDataBacklogSeverity();
        defectSev3.setBacklogType("defect");
        defectSev3.setSeverity("High");
        defectSev3.setSeverityIndex(3);

        return Arrays.asList(new AgileDataBacklogSeverity[] {defectSev1, defectSev2, defectSev3});
    }

    @Override
    public AgileDataProject getProject() {
        // We store the projects key values as ap1 or ap2 but their full name is "sample agile project #1".
        AgileDataProject project = new AgileDataProject();
        project.setProjectId(projectId);
        project.setName("Sample Agile Project #"+project.getProjectId());
        return project;
    }

    @Override
    public List<AgileDataProgram> getPrograms() {
        // As a reminder, this code returns all the programs in which the project returned in #getProject() belongs.
        AgileDataProgram myProduct = new AgileDataProgram();
        myProduct.setName("My Sample Product");
        myProduct.setProgramId("1");
        return Arrays.asList(new AgileDataProgram[]{myProduct});
    }

    @Override
    public List<AgileDataTeam> getTeams() {
        AgileDataTeam team1 = new AgileDataTeam();
        team1.setName("Team Alpha");
        team1.setEstimatedVelocity(120); // Velocity in SP per Sprint
        team1.setMembersCapacity(100);
        team1.setNumOfMembers(10);
        team1.setTeamId("1");
        team1.setTeamLeader("joseph.banks@advantageinc.com");

        return Arrays.asList(new AgileDataTeam[]{team1});
    }

    @Override
    public List<AgileDataRelease> getReleases() {
        AgileDataRelease release10 = new AgileDataRelease();
        release10.setName("Release 10.0");
        release10.setReleaseId("1");

        AgileDataRelease release11 = new AgileDataRelease();
        release11.setName("Release 11.0");
        release11.setReleaseId("2");

        return Arrays.asList(new AgileDataRelease[] {release10, release11});
    }

    @Override
    public List<AgileDataReleaseTeam> getReleaseTeams() {
        AgileDataReleaseTeam rt1 = new AgileDataReleaseTeam();
        rt1.setTeamId("1");
        rt1.setReleaseId("1");
        rt1.setReleaseTeamId("1");

        AgileDataReleaseTeam rt2 = new AgileDataReleaseTeam();
        rt2.setTeamId("1");
        rt2.setReleaseId("2");
        rt2.setReleaseTeamId("2");

        return Arrays.asList(new AgileDataReleaseTeam[] {rt1, rt2});
    }

    @Override
    public List<AgileDataSprint> getSprints() {
        AgileDataSprint sprint10_1 = new AgileDataSprint();
        sprint10_1.setName("Sprint 1");
        sprint10_1.setSprintId("1");
        sprint10_1.setReleaseId("1");
        sprint10_1.setStartDate(nextMonday.getTime());
        Calendar nextDate = ((Calendar)nextMonday.clone());
        nextDate.add(Calendar.DATE, 12);
        sprint10_1.setFinishDate(nextDate.getTime());

        AgileDataSprint sprint10_2 = new AgileDataSprint();
        sprint10_2.setName("Sprint 2");
        sprint10_2.setSprintId("2");
        sprint10_2.setReleaseId("1");
        nextDate.add(Calendar.DATE, 2);
        sprint10_2.setStartDate(nextMonday.getTime());
        nextDate.add(Calendar.DATE, 12);
        sprint10_2.setFinishDate(nextMonday.getTime());

        return Arrays.asList(new AgileDataSprint[] {sprint10_2, sprint10_2});
    }

    @Override
    public List<AgileDataEpic> getEpics() {
        AgileDataEpic epic = new AgileDataEpic();
        epic.setPlannedStoryPoints(80);
        epic.setAuthor("carly.sayer@advantageinc.com");
        epic.setEpicId("1");
        epic.setName("Advantage Mobile Banking Site");
        epic.setAggFeatureStoryPoints(55);
        epic.setTotalStoryPoints(80);

        return Arrays.asList(new AgileDataEpic[]{epic});
    }

    @Override
    public List<AgileDataFeature> getFeatures() {
        AgileDataFeature feature1 = new AgileDataFeature();
        feature1.setName("Password Management");
        feature1.setEpicId("1");
        feature1.setAggStoryPoints(35);
        feature1.setFeatureId("1");
        feature1.setNumOfDefects(10);
        feature1.setNumOfUserStories(5);
        feature1.setReleaseId("1");
        feature1.setFeaturePoints(30);
        feature1.setStatus("in progress");

        AgileDataFeature feature2 = new AgileDataFeature();
        feature2.setName("Personal Preferences");
        feature2.setEpicId("1");
        feature2.setAggStoryPoints(20);
        feature2.setFeatureId("1");
        feature2.setNumOfDefects(2);
        feature2.setNumOfUserStories(3);
        feature2.setReleaseId("2");
        feature2.setFeaturePoints(25);
        feature2.setStatus("in planning");

        return Arrays.asList(new AgileDataFeature[] {feature1, feature2});
    }

    @Override
    public List<AgileDataBacklogItem> getBacklogItems() {

        AgileDataBacklogItem defect1 = new AgileDataBacklogItem();
        defect1.setName("Server error when username contains quotes");
        defect1.setReleaseId("1");
        defect1.setSprintId("2");
        defect1.setFeatureId("1");
        defect1.setStatus("open");
        defect1.setStoryPoints(5);
        defect1.setBacklogType("defect");
        defect1.setBacklogItemId("1");
        defect1.setSeverity("high");
        defect1.setPriority("normal");
        defect1.setTeamId("1");
        defect1.setEpicId("1");

        AgileDataBacklogItem story1 = new AgileDataBacklogItem();
        story1.setName("As a user, when using a credit card on file, I must supply my CCV details");
        story1.setReleaseId("1");
        story1.setSprintId("1");
        story1.setFeatureId("2");
        story1.setStatus("not done");
        story1.setStoryPoints(10);
        story1.setBacklogType("story");
        story1.setBacklogItemId("2");
        story1.setSeverity("medium");
        story1.setPriority("low");
        story1.setTeamId("1");
        story1.setEpicId("1");

        return Arrays.asList(new AgileDataBacklogItem[] {defect1, story1});
    }

    @Override
    public String getProjectIdFromAgileProjectValue(String agileProjectValue) {
        // We store the projects key values as sap1 or sap2 but the ID should be the number.
        return agileProjectValue.substring(3);
    }
}
