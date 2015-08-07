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

import com.piranhaview.domain.Boat;
import com.piranhaview.service.BoatService;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Api(value = "boats", description = "Operations for boats")
@RestController
@RequestMapping("/api/boats")
public class BoatController {

    private final BoatService boatService;

    @Autowired
    public BoatController(BoatService boatService) {
        this.boatService = boatService;
    }

    @ApiOperation(value = "Create a boat", response = Boat.class)
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public HttpEntity<Resource<Boat>> create(
    		@ApiParam(value = "Example with required elements:\n"
					+ "<pre><code>{\n"
					+ "  \"capacity\": 8,\n"
					+ "  \"name\": Amazon Express\n"
					+ "}</pre></code>", required = true)
    		@RequestBody @Valid Boat boat) {
        Boat created = boatService.create(boat);
        Resource<Boat> resource = new Resource<Boat>(created);
        resource.add(linkTo(methodOn(BoatController.class).findOne(created.getId())).withSelfRel());

        return new HttpEntity<Resource<Boat>>(resource);
    }

    @ApiOperation(value = "Get a boat by id", response = Boat.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public HttpEntity<Resource<Boat>> findOne(@PathVariable Long id) {
        Boat boat = boatService.findOne(id);

        if (boat != null) {
	        Resource<Boat> resource = new Resource<Boat>(boat);
	        resource.add(linkTo(methodOn(BoatController.class).findOne(boat.getId())).withSelfRel());
	        return new HttpEntity<Resource<Boat>>(resource);
        }
        else {
        	return new ResponseEntity<Resource<Boat>>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Get all boats", response = Boat.class, responseContainer = "List")
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public HttpEntity<Resources<Resource<Boat>>> findAll() {
        List<Boat> boats = boatService.findAll();
        
        List<Resource<Boat>> resources = new ArrayList<Resource<Boat>>();
        for (Boat boat : boats) {
            Resource<Boat> resource = new Resource<Boat>(boat);
            resource.add(linkTo(methodOn(BoatController.class).findOne(boat.getId())).withSelfRel());
            resources.add(resource);
        }

        return new HttpEntity<Resources<Resource<Boat>>>(new Resources<Resource<Boat>>(resources));
    }

    @ApiOperation(value = "Delete a boat by id")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOne(@PathVariable Long id) {
        boatService.removeOne(id);
    }
}
