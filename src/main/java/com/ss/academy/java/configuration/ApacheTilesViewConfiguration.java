package com.ss.academy.java.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

@Configuration
public class ApacheTilesViewConfiguration {
	
	@Bean
	public TilesConfigurer tilesConfigurer() {
		TilesConfigurer tilesConfigurer= new TilesConfigurer();
		tilesConfigurer.setCompleteAutoload(true);
		tilesConfigurer.setDefinitions(new String[]{"/WEB-INF/tiles.xml"});
		return tilesConfigurer;
	}

	@Bean
	public TilesViewResolver tilesViewResolver() {
		TilesViewResolver tilesViewResolver = new TilesViewResolver();
		tilesViewResolver.setContentType("text/html; charset=UTF-8");
		tilesViewResolver.setOrder(1);
		return tilesViewResolver;
	}	
}
