package ch.fhnw.oop2.countryfx.service.serviceimpl;

import ch.fhnw.oop2.countryfx.service.CountryDTO;
import ch.fhnw.oop2.countryfx.service.CountryService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Degonas on 21.05.2017.
 */
public class CountryServiceFileBasedJunit extends CountryServiceFileBased implements CountryService{

    private static final String FILE_NAME = "/data/testdata/countriesSource.csv";
    private static final String SAVE_TO   = "/data/testdata/countriesTarget.csv";
    private static final String DELIMITER = ";";

    /**
     * 13x Delimiter (';') and 14 columsn.
     * @return
     */
    @Override
    public List<CountryDTO> findAll() {
        try (Stream<String> streamOfLines = super.getStreamOfLines(FILE_NAME)) {        // try-with-resources schliesst automatisch den Stream
            return streamOfLines.skip(1)                                                 // erste Zeile ist die Headerzeile; ueberspringen
                    .map(s -> new CountryDTO(s.split(DELIMITER, 14)))                // aus jeder Zeile ein DTO machen
                    .collect(Collectors.toList());
        }
    }



}
