package hu.bme.mit.spaceship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore mockPrimary;
  private TorpedoStore mockSecondary;

  @BeforeEach
  public void init(){
    mockPrimary = mock(TorpedoStore.class);
    mockSecondary = mock(TorpedoStore.class);

    this.ship = new GT4500(mockPrimary,mockSecondary);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(mockPrimary.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockPrimary, times(1)).fire(1);
    assertTrue(result);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(mockPrimary.fire(1)).thenReturn(true);
    when(mockSecondary.fire(1)).thenReturn(true);
    when(mockPrimary.isEmpty()).thenReturn(false);
    when(mockSecondary.isEmpty()).thenReturn(false);

    // Act

    boolean result = ship.fireTorpedo(FiringMode.ALL);

    verify(mockPrimary, times(1)).fire(1);
    verify(mockSecondary, times(1)).fire(1);

    // Assert
    assertTrue(result);
  }

  @Test
  public void fireTorpedo_All_Success_OnlyFirst(){
    // Arrange
    when(mockPrimary.fire(1)).thenReturn(true);
    when(mockSecondary.fire(0)).thenReturn(false);
    when(mockPrimary.isEmpty()).thenReturn(false);
    when(mockSecondary.isEmpty()).thenReturn(true);

    // Act

    boolean result = ship.fireTorpedo(FiringMode.ALL);

    verify(mockPrimary, times(1)).fire(1);
    verify(mockSecondary, times(1)).isEmpty();

    // Assert
    assertTrue(result);
  }

  @Test
  public void fireTorpedo_All_Success_OnlySecond(){
    // Arrange
    when(mockPrimary.fire(0)).thenReturn(false);
    when(mockSecondary.fire(1)).thenReturn(true);
    when(mockPrimary.isEmpty()).thenReturn(true);
    when(mockSecondary.isEmpty()).thenReturn(false);

    // Act

    boolean result = ship.fireTorpedo(FiringMode.ALL);

    verify(mockPrimary, times(1)).isEmpty();
    verify(mockSecondary, times(1)).fire(1);

    // Assert
    assertTrue(result);
  }

  @Test
  public void fireTorpedo_All_FAIL_BothEmpty(){
    // Arrange
    when(mockPrimary.fire(0)).thenReturn(false);
    when(mockSecondary.fire(0)).thenReturn(false);
    when(mockPrimary.isEmpty()).thenReturn(true);
    when(mockSecondary.isEmpty()).thenReturn(true);

    // Act

    boolean result = ship.fireTorpedo(FiringMode.ALL);

    verify(mockPrimary, times(1)).isEmpty();
    verify(mockSecondary, times(1)).isEmpty();

    // Assert
    assertFalse(result);
  }

  @Test
  public void fireTorpedo_SINGLE_Success_Should_first_but_second(){
    // Arrange
    when(mockPrimary.fire(0)).thenReturn(false);
    when(mockSecondary.fire(1)).thenReturn(true);
    when(mockPrimary.isEmpty()).thenReturn(true);
    when(mockSecondary.isEmpty()).thenReturn(false);

    // Act

    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    verify(mockPrimary, times(1)).isEmpty();
    verify(mockSecondary, times(1)).fire(1);

    // Assert
    assertTrue(result);
  }

  @Test
  public void fireTorpedo_SINGLE_FAIL_Both_empty(){
    // Arrange
    when(mockPrimary.fire(0)).thenReturn(false);
    when(mockSecondary.fire(0)).thenReturn(false);
    when(mockPrimary.isEmpty()).thenReturn(true);
    when(mockSecondary.isEmpty()).thenReturn(false);

    // Act

    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    verify(mockPrimary, times(1)).isEmpty();
    verify(mockSecondary, times(1)).fire(1);

    // Assert
    assertFalse(result);
  }

  @Test
  public void fireTorpedo_SINGLE_SUCCES_First_was_last(){
    // Arrange
    when(mockPrimary.fire(1)).thenReturn(true);
    when(mockSecondary.fire(1)).thenReturn(true);
    when(mockPrimary.isEmpty()).thenReturn(false);
    when(mockSecondary.isEmpty()).thenReturn(false);


    // Act

    ship.fireTorpedo(FiringMode.SINGLE);
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    verify(mockPrimary, times(1)).fire(1);
    verify(mockSecondary, times(1)).fire(1);

    // Assert
    assertTrue(result);
  }



  @Test
  public void fireTorpedo_SINGLE_Fail_Both_Empty(){
    // Arrange
    when(mockPrimary.fire(1)).thenReturn(true);
    when(mockSecondary.fire(0)).thenReturn(true);
    when(mockPrimary.isEmpty()).thenReturn(false);
    when(mockSecondary.isEmpty()).thenReturn(true);

    ship.fireTorpedo(FiringMode.SINGLE);
    when(mockPrimary.fire(1)).thenReturn(true);


    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    verify(mockPrimary, times(1)).fire(1);
    verify(mockSecondary, times(1)).isEmpty();

    // Assert
    assertFalse(result);
  }

  @Test
  public void fireTorpedo_SINGLE_AS(){
    // Arrange
    when(mockPrimary.fire(1)).thenReturn(true);
    when(mockSecondary.fire(0)).thenReturn(true);
    when(mockPrimary.isEmpty()).thenReturn(false);
    when(mockSecondary.isEmpty()).thenReturn(true);


    // Act
    ship.fireTorpedo(FiringMode.SINGLE);

    when(mockPrimary.isEmpty()).thenReturn(true);
    when(mockSecondary.isEmpty()).thenReturn(true  );
    boolean result= ship.fireTorpedo(FiringMode.SINGLE);

    verify(mockPrimary, times(1)).fire(1);
    verify(mockSecondary, times(1)).isEmpty();

    // Assert
    assertFalse(result);
  }

}
