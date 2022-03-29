package com.vivino.acceptance.config;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class FrameworkProperties {

    public static final Logger log = LoggerFactory.getLogger(FrameworkProperties.class);

    @Value("${application.under.test.url}")
    private String appUrl;

    @Value("${test.browser.name}")
    private String browserName;
}
