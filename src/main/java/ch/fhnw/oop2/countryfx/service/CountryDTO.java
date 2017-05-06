package ch.fhnw.oop2.countryfx.service;

/**
 *  1 DTO = eine Zeile im .csv
 */
public class CountryDTO {

    private final int       ID;
    private final String    NAME;
    private final String    NAME_LONG;
    private final String    CAPITAL;
    private final int       POPULATION;
    private final double    AREA;
    private final double    POPULATION_KM2;
    private final String    FLAG;
    private final String    ISO_3;
    private final String    ISO_2;
    private final String    TLD;
    private final String    NAME_ENGLISH;
    private final String    NAME_LOCAL;
    private final String    CONTINENT;

    public CountryDTO(String... args){
        ID = Integer.parseInt(args[0]);
        NAME = args[1];
        NAME_LONG = args[2];
        CAPITAL = args[3];
        POPULATION = Integer.parseInt(args[4]);
        AREA = Double.parseDouble(args[5]);
        POPULATION_KM2 = Double.parseDouble(args[6]);
        FLAG = args[7];
        ISO_3 = args[8];
        ISO_2 = args[9];
        TLD = args[10];
        NAME_ENGLISH = args[11];
        NAME_LOCAL = args[12];
        CONTINENT = args[13];
    }

    /******************** getters *****************************/

    public int getID() {
        return ID;
    }

    public String getNAME() {
        return NAME;
    }

    public String getNAME_LONG() {
        return NAME_LONG;
    }

    public String getCAPITAL() {
        return CAPITAL;
    }

    public int getPOPULATION() {
        return POPULATION;
    }

    public double getAREA() {
        return AREA;
    }

    public double getPOPULATION_KM2() {
        return POPULATION_KM2;
    }

    public String getFLAG() {
        return FLAG;
    }

    public String getISO_3() {
        return ISO_3;
    }

    public String getISO_2() {
        return ISO_2;
    }

    public String getTLD() {
        return TLD;
    }

    public String getNAME_ENGLISH() {
        return NAME_ENGLISH;
    }

    public String getNAME_LOCAL() {
        return NAME_LOCAL;
    }

    public String getCONTINENT() {
        return CONTINENT;
    }
}
