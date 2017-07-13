package com.ppm.integration.agilesdk.connector.sample;

import com.hp.ppm.pfm.model.Kpi;
import com.ppm.integration.agilesdk.ValueSet;
import com.ppm.integration.agilesdk.kpi.KpiImportIntegration;

import java.util.HashMap;
import java.util.Map;

/**
 * This class demonstrate the KPI Import Integration.
 *
 * It expects for a KPI named "Sample KPI" to have been created in PPM, otherwise it will not do anything.
 *
 */
public class SampleKpiImportIntegration extends KpiImportIntegration {

    @Override public Map<Long, Double> getKpiValues(ValueSet instanceConfiguration) {

        Map<Long, Double> kpisToImport = new HashMap<Long, Double>();

        // You need the KPI IDs but you'll usually know a KPI by name. Use #getKpiByName() in order to retrieve the Kpi object and get the ID.
        Kpi sampleKpi = getKpiByName("Sample KPI");

        if (sampleKpi != null) {
            kpisToImport.put(sampleKpi.getId(), new Double(123));
        }

        return kpisToImport;
    }
}
