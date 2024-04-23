package com.suyashg.booklibrary.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.suyashg.booklibrary.model.Rental;
import com.suyashg.booklibrary.service.RentalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class RentalControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RentalService rentalService;

    @InjectMocks
    private RentalController rentalController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(rentalController).build();
    }

    @Test
    void testGetAllRentals() throws Exception {
        Rental rental1 = new Rental();
        rental1.setId(1L);
        rental1.setRenterName("Renter 1");

        Rental rental2 = new Rental();
        rental2.setId(2L);
        rental2.setRenterName("Renter 2");

        when(rentalService.getAllRentals()).thenReturn(Arrays.asList(rental1, rental2));

        mockMvc.perform(get("/api/rentals"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].renterName").value("Renter 1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].renterName").value("Renter 2"));
    }

    @Test
    void testGetRentalById() throws Exception {
        Rental rental = new Rental();
        rental.setId(1L);
        rental.setRenterName("Renter 1");

        when(rentalService.getRentalById(1L)).thenReturn(Optional.of(rental));

        mockMvc.perform(get("/api/rentals/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.renterName").value("Renter 1"));
    }

    @Test
    void testAddRental() throws Exception {
        Rental rental = new Rental();
        rental.setRenterName("New Renter");

        when(rentalService.saveRental(any(Rental.class))).thenReturn(rental);

        mockMvc.perform(post("/api/rentals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(rental)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.renterName").value("New Renter"));
    }

    @Test
    void testUpdateRental() throws Exception {
        Rental existingRental = new Rental();
        existingRental.setId(1L);
        existingRental.setRenterName("Existing Renter");

        Rental updatedRental = new Rental();
        updatedRental.setId(1L);
        updatedRental.setRenterName("Updated Renter");

        when(rentalService.updateRental(anyLong(), any(Rental.class))).thenReturn(updatedRental);


        mockMvc.perform(put("/api/rentals/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updatedRental)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.renterName").value("Updated Renter"));
    }

    @Test
    void testDeleteRental() throws Exception {
        mockMvc.perform(delete("/api/rentals/1"))
                .andExpect(status().isNoContent());
    }
}
