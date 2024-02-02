package org.gfa.avustribesbackend.services.Troop;

import org.gfa.avustribesbackend.repositories.TroopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TroopServiceImpl implements TroopService{
    private final TroopRepository troopRepository;

    @Autowired
    public TroopServiceImpl(TroopRepository troopRepository) {
        this.troopRepository = troopRepository;
    }
}
