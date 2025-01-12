package com.example.libraryapp.service;

import com.example.libraryapp.model.Printout;

public interface PrintoutService {
    Printout getPrintoutById(int id);
    Printout changePrintoutStatus(Integer id);

}
