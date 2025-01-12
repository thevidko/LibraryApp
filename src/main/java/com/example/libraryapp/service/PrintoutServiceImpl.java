package com.example.libraryapp.service;

import com.example.libraryapp.model.Printout;
import com.example.libraryapp.repository.PrintoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PrintoutServiceImpl implements PrintoutService {
    private PrintoutRepository printoutRepository;
    @Autowired
    public PrintoutServiceImpl(PrintoutRepository printoutRepository) {
        this.printoutRepository = printoutRepository;
    }

    @Override
    public Printout getPrintoutById(int id) {
        Optional<Printout> printout = printoutRepository.findById((long) id);
        return printout.orElse(null);
    }

    @Override
    public Printout changePrintoutStatus(Integer id) {
        Optional<Printout> printoutOptional = printoutRepository.findById((long) id);

        // Pokud výtisk existuje, změníme jeho status
        if (printoutOptional.isPresent()) {
            Printout printout = printoutOptional.get();

            // Příklad změny statusu (předpokládáme, že máte boolean status dostupnosti)
            printout.setAvailable(!printout.isAvailable()); // Např. přepne dostupnost výtisku
            return printoutRepository.save(printout); // Uložíme změněný výtisk
        }

        // Pokud výtisk neexistuje, vyhodíme výjimku (nebo můžeme vrátit nějakou indikaci chyby)
        throw new IllegalArgumentException("Výtisk s tímto ID neexistuje.");
    }

    @Override
    public void deletePrintoutById(int id) {
        printoutRepository.deleteById((long) id);
    }
}
