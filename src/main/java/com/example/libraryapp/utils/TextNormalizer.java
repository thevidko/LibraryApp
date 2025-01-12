package com.example.libraryapp.utils;

import java.text.Normalizer;

public class TextNormalizer {
    public static String removeDiacritics(String input) {
        // Normalizuje text a odstraní diakritiku
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        return normalized.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
    }
}
