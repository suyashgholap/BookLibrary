package com.suyashg.booklibrary.service;

import com.suyashg.booklibrary.model.Rental;
import com.suyashg.booklibrary.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    public Optional<Rental> getRentalById(Long id) {
        return rentalRepository.findById(id);
    }

    public Rental saveRental(Rental rental) {
        return rentalRepository.save(rental);
    }

    public Rental updateRental(Long id, Rental updatedRental) {
        if (rentalRepository.existsById(id)) {
            updatedRental.setId(id); // Ensure the ID is set
            return rentalRepository.save(updatedRental);
        }
        return null; // Or throw an exception indicating rental not found
    }

    public void deleteRental(Long id) {
        rentalRepository.deleteById(id);
    }
}
