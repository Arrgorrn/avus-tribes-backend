package org.gfa.avustribesbackend.models;

import jakarta.persistence.*;

@Entity
@Table(name = "worlds")
public class World {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long Id;
    @Column(unique = true, nullable = false)
    private String Name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "world_player",
            joinColumns = @JoinColumn(name = "world_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id"))
    private List<Player> players;

    public World(String name) {
        Name = name;
    }

    public World() {
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
