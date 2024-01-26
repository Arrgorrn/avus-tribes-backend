package org.gfa.avustribesbackend.services.Resource;

import org.gfa.avustribesbackend.models.Building;
import org.gfa.avustribesbackend.models.Resource;
import org.gfa.avustribesbackend.repositories.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceServiceImpl implements ResourceService {
  private final ResourceRepository resourceRepository;

  @Autowired
  public ResourceServiceImpl(ResourceRepository resourceRepository) {
    this.resourceRepository = resourceRepository;
  }

  @Override
  @Scheduled(fixedRate = 60000)
  public void gainingResources() {
    List<Resource> resources = resourceRepository.findAll();
    for (Resource resource : resources) {
      for (Building building : resource.getResourceType().getBuildingType().getBuildings()) {
        if (building.getLevel() == 1) {
          resource.setAmount(resource.getAmount() + 10);
        } else {
          resource.setAmount(resource.getAmount() + (10 + (5 * building.getLevel())));
        }
      }
    }
  }
}
