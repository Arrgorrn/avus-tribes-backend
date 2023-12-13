package org.gfa.avustribesbackend.services.Player;

import org.gfa.avustribesbackend.dtos.PlayerRegistrationBody;
import org.gfa.avustribesbackend.models.MyError;
import org.gfa.avustribesbackend.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

@Service
public class PlayerServiceImpl implements PlayerService{

    private final PlayerRepository playerRepository;
    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public ResponseEntity<Object> registerPlayer(PlayerRegistrationBody request) {
        MyError error = new MyError();
        if (request.getUsername() == null){
            error.setError("Username is required");
            return ResponseEntity.status(400).body(error);
        }if (request.getPassword() == null){
            error.setError("Password is required");
            return ResponseEntity.status(400).body(error);
        }if (request.getEmail() == null){
            error.setError("Email is required");
            return ResponseEntity.status(400).body(error);
        }if (playerRepository.existsByUserName(request.getUsername())){
            return ResponseEntity.status(409).body("Username is already taken");
        }if (request.getUsername().length() < 4){
            error.setError("Username must be at least 4 characters long");
            return ResponseEntity.status(400).body(error);
        }if (request.getPassword().length() < 8){
            error.setError("Password must be at least 8 characters long");
            return ResponseEntity.status(400).body(error);
        }if (!validateEmail(request.getEmail())){
            error.setError("Invalid email");
            return ResponseEntity.status(400).body(error);
        }

      //        Request with valid data but the creation of
        //        a new player fails returns the “Unknown
      // error” message - 400

      return null;
    }

    public boolean validateEmail(String email) {
        boolean isValid = false;
        try {
            InternetAddress internetAddress = new InternetAddress(email);
            internetAddress.validate();
            isValid = true;
        } catch (AddressException e) {
            e.printStackTrace();
        }
        return isValid;
    }
}
