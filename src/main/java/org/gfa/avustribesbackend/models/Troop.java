package org.gfa.avustribesbackend.models;

import jakarta.persistence.*;

@Entity
@Table(name = "troops")
public class Troop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long troopId;
    private int level;
    private int hp;
    private int attack;
    private int defense;
    private long startedAt;
    private long finishedAt;
    @ManyToOne
    @JoinColumn(name = "kingdom_id")
    private Kingdom kingdom;

    public Troop(Kingdom kingdom, long startedAt, long finishedAt) {
        this.level = 1;
        this.hp = 20;
        this.attack = 10;
        this.defense = 5;
        this.kingdom = kingdom;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
    }

    public Troop() {
        this.level = 1;
        this.hp = 20;
        this.attack = 10;
        this.defense = 5;
    }

    public long getTroopId() {
        return troopId;
    }

    public void setTroopId(long troopId) {
        this.troopId = troopId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public long getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(long startedAt) {
        this.startedAt = startedAt;
    }

    public long getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(long finishedAt) {
        this.finishedAt = finishedAt;
    }

    public Kingdom getKingdom() {
        return kingdom;
    }

    public void setKingdom(Kingdom kingdom) {
        this.kingdom = kingdom;
    }
}
