package com.sxkl.flyway.app;

import javax.annotation.PostConstruct;

import org.flywaydb.core.Flyway;


public class FlywayApp {  
	
	private Flyway flyway;
	
    @SuppressWarnings("deprecation")
	@PostConstruct
    public void run() {
        System.out.println("[Start] DbMigration run .. ");
        flyway.setInitOnMigrate(true);
        flyway.migrate();
        System.out.println("[End] DbMigration run .. ");
    }
    
    public void setFlyway(Flyway flyway) {
        this.flyway = flyway;
    }
    
}  