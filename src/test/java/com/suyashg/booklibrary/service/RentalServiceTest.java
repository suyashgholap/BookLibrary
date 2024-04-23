package com.suyashg.booklibrary.service;

import com.suyashg.booklibrary.model.Rental;
import com.suyashg.booklibrary.repository.RentalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RentalServiceTest {

    @Mock
    private RentalRepository rentalRepository;

    @InjectMocks
    private RentalService rentalService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllRentals() {
        Rental rental1 = new Rental();
        rental1.setId(1L);
        rental1.setRenterName("Renter 1");

        Rental rental2 = new Rental();
        rental2.setId(2L);
        rental2.setRenterName("Renter 2");

        when(rentalRepository.findAll()).thenReturn(Arrays.asList(rental1, rental2));

        List<Rental> rentals = rentalService.getAllRentals();

        assertNotNull(rentals);
        assertEquals(2, rentals.size());
        assertEquals("Renter 1", rentals.get(0).getRenterName());
        assertEquals("Renter 2", rentals.get(1).getRenterName());
    }

    @Test
    void testGetRentalById() {
        Rental rental = new Rental();
        rental.setId(1L);
        rental.setRenterName("Renter 1");

        when(rentalRepository.findById(1L)).thenReturn(Optional.of(rental));

        Optional<Rental> optionalRental = rentalService.getRentalById(1L);

        assertTrue(optionalRental.isPresent());
        assertEquals("Renter 1", optionalRental.get().getRenterName());
    }

    @Test
    void testSaveRental() {
        Rental rental = new Rental();
        rental.setRenterName("New Renter");

        when(rentalRepository.save(any(Rental.class))).thenReturn(rental);

        Rental savedRental = rentalService.saveRental(rental);

        assertNotNull(savedRental);
        assertEquals("New Renter", savedRental.getRenterName());
    }

    @Test
    void testUpdateRental() {
        Rental existingRental = new Rental();
        existingRental.setId(1L);
        existingRental.setRenterName("Existing Renter");

        Rental updatedRental = new Rental();
        updatedRental.setId(1L);
        updatedRental.setRenterName("Updated Renter");

        when(rentalRepository.existsById(1L)).thenReturn(true);
        when(rentalRepository.save(updatedRental)).thenReturn(updatedRental);

        Rental returnedRental = rentalService.updateRental(1L, updatedRental);

        assertNotNull(returnedRental);
        assertEquals("Updated Renter", returnedRental.getRenterName());
    }

    @Test
    void testUpdateRental_RentalNotFound() {
        Rental updatedRental = new Rental();
        updatedRental.setId(1L);
        updatedRental.setRenterName("Updated Renter");

        when(rentalRepository.existsById(1L)).thenReturn(false);

        Rental returnedRental = rentalService.updateRental(1L, updatedRental);

        assertNull(returnedRental);
    }

    @Test
    void testDeleteRental() {
        doNothing().when(rentalRepository).deleteById(1L);

        assertDoesNotThrow(() -> rentalService.deleteRental(1L));
    }
}
