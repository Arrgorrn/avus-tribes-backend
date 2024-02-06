package org.gfa.avustribesbackend.services.Troop;

import org.gfa.avustribesbackend.exceptions.CreationException;
import org.gfa.avustribesbackend.models.Kingdom;

public interface TroopService {
    void creating(Kingdom kingdom);
    void eating();
    void upgrading(Kingdom kingdom);
}
