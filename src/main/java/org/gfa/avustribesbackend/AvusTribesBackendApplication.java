package org.gfa.avustribesbackend;

import org.gfa.avustribesbackend.models.Kingdom;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.converter.json.GsonBuilderUtils;

@SpringBootApplication
public class AvusTribesBackendApplication {

  public static void main(String[] args) {
    SpringApplication.run(AvusTribesBackendApplication.class, args);
  }
}
