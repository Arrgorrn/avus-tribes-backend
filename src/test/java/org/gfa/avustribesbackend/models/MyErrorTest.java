package org.gfa.avustribesbackend.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyErrorTest {

  @Test
  void create_error_with_message() {
    // Arrange
    String errorMessage = "Invalid test";

    // Act
    MyError error = new MyError(errorMessage);

    // Assert
    assertNotNull(error);
    assertEquals(errorMessage, error.getError());
  }
}
