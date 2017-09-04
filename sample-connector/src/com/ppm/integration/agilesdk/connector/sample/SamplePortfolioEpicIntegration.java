package com.ppm.integration.agilesdk.connector.sample;

import com.kintana.core.util.URLEncoder;
import com.ppm.integration.agilesdk.ValueSet;
import com.ppm.integration.agilesdk.epic.PortfolioEpicCreationInfo;
import com.ppm.integration.agilesdk.epic.PortfolioEpicIntegration;
import com.ppm.integration.agilesdk.epic.PortfolioEpicSyncInfo;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * This class simulates {@link PortfolioEpicIntegration} feature to create a PPM Portfolio Epics into an Agile Tool for execution and later brings back story points progression.
 */
public class SamplePortfolioEpicIntegration extends PortfolioEpicIntegration {
    @Override
    public String createEpicInAgileProject(PortfolioEpicCreationInfo epicInfo, String agileProjectValue, ValueSet instanceConfigurationParameters) {
        // This is where the connector is supposed to call the Agile Tool's APIs to create the Epic, and return the Epic ID to PPM.
        return "EpicId_"+RandomStringUtils.randomAlphanumeric(5);
    }

    @Override
    public PortfolioEpicSyncInfo getPortfolioEpicSyncInfo(String epicId, String agileProjectValue, ValueSet instanceConfigurationParameters) {
        // Let's return an Epic that has between 50 and 100 story points total and between 10 and 49 story points done.
        // This will change upon every sync, but here's you're supposed to call the Agile Tool's API and get the information.
        PortfolioEpicSyncInfo epicInfo = new PortfolioEpicSyncInfo();
        epicInfo.setEpicName("Epic with ID "+epicId);
        epicInfo.setTotalStoryPoints(RandomUtils.nextInt(51)+50);
        epicInfo.setDoneStoryPoints(RandomUtils.nextInt(40)+10);
        return epicInfo;
    }

    @Override
    public String getEpicURI(String epicId, String agileProjectValue) {
        // We don't have a real Agile tool to send you to, so let's just look for it. This will only work if you've entered Google as the base URL for your sample connector
        return "/search?q="+URLEncoder.encode(epicId+" for agile project "+agileProjectValue);
    }
}
