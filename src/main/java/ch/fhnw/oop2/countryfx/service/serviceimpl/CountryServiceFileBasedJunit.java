package ch.fhnw.oop2.countryfx.service.serviceimpl;

import ch.fhnw.oop2.countryfx.service.CountryDTO;
import ch.fhnw.oop2.countryfx.service.CountryService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
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
    private static final String HEADLINE = "ID;NAME;NAME_LONG;CAPITAL;POPULATION;AREA;POPULATION_KM2;FLAG;ISO_3;ISO_2;TLD;NAME_ENGLISH;NAME_LOCAL;CONTINENT";


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


    /************************** Save Countries To File **************************/
    @Override
    public void save(List<CountryDTO> dtos){
        try{
            //1 dto -> einem String in form id;name;...
            List<String> dtosAsString = dtos.stream()
                    .map(dto -> convertToString(dto))
                    .collect(Collectors.toList());


            dtosAsString.add(0, HEADLINE);      // Add Headline to List at the beginning

            Files.write(getPath(SAVE_TO), dtosAsString, StandardCharsets.UTF_8, StandardOpenOption.WRITE);    // Write everything into file

        }catch (IOException e){
            throw new IllegalStateException(e);
        }
    }

    /************************** for validation of save() **************************/

    /**
     * for JUnit testing (used after a save() has been used)
     *
     * Difference to findAll(): SAVE_TO instead of FILE_NAME.
     * @return
     */
    public List<CountryDTO> findAllFromTarget() {
        try (Stream<String> streamOfLines = super.getStreamOfLines(SAVE_TO)) {        // try-with-resources schliesst automatisch den Stream
            return streamOfLines.skip(1)                                                 // erste Zeile ist die Headerzeile; ueberspringen
                    .map(s -> new CountryDTO(s.split(DELIMITER, 14)))                // aus jeder Zeile ein DTO machen
                    .collect(Collectors.toList());
        }
    }
}
