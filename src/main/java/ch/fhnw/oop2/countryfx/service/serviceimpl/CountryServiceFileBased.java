package ch.fhnw.oop2.countryfx.service.serviceimpl;

import ch.fhnw.oop2.countryfx.service.CountryDTO;
import ch.fhnw.oop2.countryfx.service.CountryService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Degonas on 29.04.2017.
 */
public class CountryServiceFileBased implements CountryService {

    private static final String FILE_NAME = "/data/countries.csv";
    private static final String DELIMITER = ";";

    /**
     * 13x Delimiter (';') and 14 columsn.
     * @return
     */
    @Override
    public List<CountryDTO> findAll() {
        try (Stream<String> streamOfLines = getStreamOfLines(FILE_NAME)) {        // try-with-resources schliesst automatisch den Stream
            return streamOfLines.skip(1)                                                 // erste Zeile ist die Headerzeile; ueberspringen
                    .map(s -> new CountryDTO(s.split(DELIMITER, 13)))                // aus jeder Zeile ein DTO machen
                    .collect(Collectors.toList());
        }
    }



    private Stream<String> getStreamOfLines(String fileName) {
        try {
            return Files.lines(getPath(fileName), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private Path getPath(String fileName)  {
        try {
            return Paths.get(getClass().getResource(fileName).toURI());
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
