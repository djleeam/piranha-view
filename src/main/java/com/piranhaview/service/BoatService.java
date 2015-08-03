package com.piranhaview.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.piranhaview.data.BoatRepository;
import com.piranhaview.domain.Boat;

@Service
public class BoatService implements IBoatService {

	private final BoatRepository boatRepository;

    @Autowired
    public BoatService(BoatRepository boatRepository) {
        this.boatRepository = boatRepository;
    }

	@Override
	public Boat create(Boat boat) {
		return boatRepository.save(boat);
	}

	@Override
	public Boat findOne(Long id) {
		return boatRepository.findOne(id);
	}

	@Override
	public List<Boat> findAll() {
		return Lists.newArrayList(boatRepository.findAll());
	}

	@Override
	public void removeOne(Long id) {
		boatRepository.delete(id);
	}
}
