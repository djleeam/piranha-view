package com.piranhaview.domain;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.piranhaview.service.BoatService;

@RestController
@RequestMapping("/api/boats")
public class BoatController {

    private final BoatService boatService;

    @Autowired
    public BoatController(BoatService boatService) {
        this.boatService = boatService;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public HttpEntity<Resource<Boat>> createForm(@Valid Boat entity) {
    	return createBoat(entity);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public HttpEntity<Resource<Boat>> createJson(@RequestBody @Valid Boat entity) {
    	return createBoat(entity);
    }

	private HttpEntity<Resource<Boat>> createBoat(Boat entity) {
		Boat boat = boatService.create(entity);
        Resource<Boat> resource = new Resource<Boat>(boat);
        resource.add(linkTo(methodOn(BoatController.class).findOne(boat.getId())).withSelfRel());

        return new HttpEntity<Resource<Boat>>(resource);
	}

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public HttpEntity<Resource<Boat>> findOne(@PathVariable Long id) {
    	Boat boat = boatService.findOne(id);
        Resource<Boat> resource = new Resource<Boat>(boat);
        resource.add(linkTo(methodOn(BoatController.class).findOne(boat.getId())).withSelfRel());

        return new HttpEntity<Resource<Boat>>(resource);
    }

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
}
