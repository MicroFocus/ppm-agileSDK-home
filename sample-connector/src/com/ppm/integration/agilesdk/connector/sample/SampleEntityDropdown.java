package com.ppm.integration.agilesdk.connector.sample;

import java.util.ArrayList;
import java.util.List;

import com.hp.ppm.integration.ValueSet;
import com.hp.ppm.integration.ui.DynamicalDropdown;

public abstract class SampleEntityDropdown extends DynamicalDropdown {
	public SampleEntityDropdown(String name, String labelKey, String display,
			boolean isRequired) {
		super(name, labelKey, display, isRequired);
	}

	public SampleEntityDropdown(String name, String labelKey, boolean isRequired) {
		super(name, labelKey, null, isRequired);
	}

	public abstract List<String> getDependencies();

	public abstract List<DynamicalDropdown.Option> fetchDynamicalOptions(
			ValueSet paramValueSet);

	public List<DynamicalDropdown.Option> getDynamicalOptions(ValueSet values) {
		try {
			return fetchDynamicalOptions(values);
		} catch (Throwable e) {
			//
		}
		return new ArrayList(0);
	}
	

}
