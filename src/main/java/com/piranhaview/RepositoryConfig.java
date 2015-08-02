package com.piranhaview;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

import com.piranhaview.domain.Assignment;
import com.piranhaview.domain.Boat;
import com.piranhaview.domain.Booking;
import com.piranhaview.domain.TimeSlot;

@Configuration
public class RepositoryConfig extends RepositoryRestMvcConfiguration {

    @Override
    protected void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
	    config.setBaseUri("/api");
	    config.setReturnBodyOnCreate(true);
	    config.setReturnBodyOnUpdate(true);
        config.exposeIdsFor(TimeSlot.class, Boat.class, Assignment.class, Booking.class);
    }
}