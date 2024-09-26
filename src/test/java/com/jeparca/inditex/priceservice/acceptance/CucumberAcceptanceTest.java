package com.jeparca.inditex.priceservice.acceptance;

import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.springframework.test.context.ActiveProfiles;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@SelectPackages("com.jeparca.inditex.priceservice.acceptance")
@ActiveProfiles("test")
public class CucumberAcceptanceTest {
}