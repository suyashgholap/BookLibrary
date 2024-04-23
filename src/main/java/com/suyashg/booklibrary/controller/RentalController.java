package com.suyashg.booklibrary.controller;

import com.suyashg.booklibrary.model.Rental;
import com.suyashg.booklibrary.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @GetMapping
    public ResponseEntity<List<Rental>> getAllRentals() {
        List<Rental> rentals = rentalService.getAllRentals();
        return new ResponseEntity<>(rentals, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rental> getRentalById(@PathVariable("id") Long id) {
        Optional<Rental> rental = rentalService.getRentalById(id);
        return rental.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Rental> addRental(@RequestBody Rental rental) {
        Rental savedRental = rentalService.saveRental(rental);
        return new ResponseEntity<>(savedRental, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rental> updateRental(@PathVariable("id") Long id, @RequestBody Rental updatedRental) {
        Rental rental = rentalService.updateRental(id, updatedRental);
        if (rental != null) {
            return new ResponseEntity<>(rental, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRental(@PathVariable("id") Long id) {
        rentalService.deleteRental(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
