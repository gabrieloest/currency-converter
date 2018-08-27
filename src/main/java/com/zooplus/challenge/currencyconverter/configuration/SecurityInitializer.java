package com.zooplus.challenge.currencyconverter.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

import com.zooplus.challenge.currencyconverter.security.SecurityConfiguration;

@Configuration
public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {

	public SecurityInitializer() {
		super(SecurityConfiguration.class);
	}

}