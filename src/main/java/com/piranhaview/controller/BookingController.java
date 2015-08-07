package com.piranhaview.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.piranhaview.domain.Booking;
import com.piranhaview.service.BookingService;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Api(value = "bookings", description = "Operations for bookings")
@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @ApiOperation(value = "Create a booking", response = Booking.class)
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public HttpEntity<Resource<Booking>> create(
    		@ApiParam(value = "Example with required elements:\n"
					+ "<pre><code>{\n"
					+ "  \"timeslot_id\": 1,\n"
					+ "  \"size\": 6\n"
					+ "}</pre></code>", required = true)
    		@RequestBody @Valid Booking booking) {
        Booking created = bookingService.create(booking);
        Resource<Booking> resource = new Resource<Booking>(created);
        resource.add(linkTo(methodOn(BookingController.class).findOne(created.getId())).withSelfRel());

        return new HttpEntity<Resource<Booking>>(resource);
    }

    @ApiOperation(value = "Get a booking by id", response = Booking.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public HttpEntity<Resource<Booking>> findOne(@PathVariable Long id) {
        Booking booking = bookingService.findOne(id);

        if (booking != null) {
	        Resource<Booking> resource = new Resource<Booking>(booking);
	        resource.add(linkTo(methodOn(BookingController.class).findOne(booking.getId())).withSelfRel());
	        return new HttpEntity<Resource<Booking>>(resource);
        }
        else {
        	return new ResponseEntity<Resource<Booking>>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Get all bookings", response = Booking.class, responseContainer = "List")
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public HttpEntity<Resources<Resource<Booking>>> findAll() {
        List<Booking> bookings = bookingService.findAll();
        
        List<Resource<Booking>> resources = new ArrayList<Resource<Booking>>();
        for (Booking booking : bookings) {
            Resource<Booking> resource = new Resource<Booking>(booking);
            resource.add(linkTo(methodOn(BookingController.class).findOne(booking.getId())).withSelfRel());
            resources.add(resource);
        }

        return new HttpEntity<Resources<Resource<Booking>>>(new Resources<Resource<Booking>>(resources));
    }

    @ApiOperation(value = "Delete a booking by id")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOne(@PathVariable Long id) {
        bookingService.removeOne(id);
    }
}
