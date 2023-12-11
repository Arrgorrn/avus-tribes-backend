package org.gfa.avustribesbackend.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    public void player_is_created_after_calling_constructor() {
        Long id = 1L;
        String userName = "Michal";
        String email = "abcd@email.com";
        String password = "awd3aw63d5aw3";
        Date verifiedAt = new Date(System.currentTimeMillis());
        String verificationToken = "awdaegl;snrg;srng[wspriaquefahnuw";
        Date verificationTokenExpiresAt = new Date(System.currentTimeMillis() + 1000 * 60 * 60);
        String forgottenPasswordToken = "agsrgwserfaqw";
        Date forgottenPasswordTokenExpiresAt = new Date(System.currentTimeMillis() + 1000 * 60 * 60);

        Player player = new Player();

        Date createdAt = player.getCreatedAt();

        player.setId(id);
        player.setUserName(userName);
        player.setEmail(email);
        player.setPassword(password);
        player.setVerifiedAt(verifiedAt);
        player.setVerificationToken(verificationToken);
        player.setVerificationTokenExpiresAt(verificationTokenExpiresAt);
        player.setForgottenPasswordToken(forgottenPasswordToken);
        player.setForgottenPasswordTokenExpiresAt(forgottenPasswordTokenExpiresAt);

        assertNotNull(player);
        assertEquals(id, player.getId());
        assertEquals(userName, player.getUserName());
        assertEquals(email, player.getEmail());
        assertEquals(password, player.getPassword());
        assertEquals(verifiedAt, player.getVerifiedAt());
        assertEquals(verificationToken, player.getVerificationToken());
        assertEquals(verificationTokenExpiresAt, player.getVerificationTokenExpiresAt());
        assertEquals(forgottenPasswordToken, player.getForgottenPasswordToken());
        assertEquals(forgottenPasswordTokenExpiresAt, player.getForgottenPasswordTokenExpiresAt());
        assertEquals(createdAt, player.getCreatedAt());
    }
}