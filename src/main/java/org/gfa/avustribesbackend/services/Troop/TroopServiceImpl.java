package org.gfa.avustribesbackend.services.Troop;

import org.gfa.avustribesbackend.exceptions.CreationException;
import org.gfa.avustribesbackend.models.Building;
import org.gfa.avustribesbackend.models.Kingdom;
import org.gfa.avustribesbackend.models.Troop;
import org.gfa.avustribesbackend.models.enums.BuildingTypeValue;
import org.gfa.avustribesbackend.repositories.BuildingRepository;
import org.gfa.avustribesbackend.repositories.TroopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TroopServiceImpl implements TroopService{
    private final TroopRepository troopRepository;
    private final BuildingRepository buildingRepository;

    @Autowired
    public TroopServiceImpl(TroopRepository troopRepository,
                            BuildingRepository buildingRepository) {
        this.troopRepository = troopRepository;
        this.buildingRepository = buildingRepository;
    }

    @Override
    public void creating(Kingdom kingdom) {
        //checks academy
       boolean hasAcademy = false;
       for (Building building : kingdom.getBuildings()){
           if(building.getType().equals(BuildingTypeValue.ACADEMY)){
               hasAcademy = true;
               break;
           }
       }
       if (!hasAcademy){
           throw new CreationException("Need academy to train troops!");
       }
       //checks if there is enough food
       int totalFoodPerMinute = 0;
       for (Building building : buildingRepository.findAllByKingdomAndType(kingdom,
               BuildingTypeValue.FARM)){
            totalFoodPerMinute += (building.getLevel() * 5) + 5;
       }
       int totalEating = 0;
       for(Troop troop : troopRepository.findAll()){
           totalEating += troop.getLevel() * 5;
       }
       if(totalEating >= totalFoodPerMinute){
           throw new CreationException("No enough food to feed another hungry troop");
       }
        //creating
       Troop troop = new Troop(kingdom, System.currentTimeMillis(),
               System.currentTimeMillis() + 30);
        // Pause for 30 seconds
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        troopRepository.save(troop);
    }
}
