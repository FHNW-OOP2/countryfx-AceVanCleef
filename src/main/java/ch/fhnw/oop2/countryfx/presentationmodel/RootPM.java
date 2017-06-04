package ch.fhnw.oop2.countryfx.presentationmodel;

import ch.fhnw.oop2.countryfx.service.CountryDTO;
import ch.fhnw.oop2.countryfx.service.CountryService;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class RootPM {
    private final StringProperty applicationTitle = new SimpleStringProperty("CountryFX");

    private final ObservableList<CountryPM> allCountries = FXCollections.observableArrayList(); // Alle PMs in einer Collection/Liste
    private final CountryService service;

    //for TableView<ContinentPM>
    private final ObservableList<ContinentPM> allContinents = FXCollections.observableArrayList();

    //#SelectionHandling
    private final IntegerProperty selectedCountryId = new SimpleIntegerProperty(1); //Statusinformation: Welches Country angezeigt wird.

    //#stableSelection (Advanced Selection Handling)
    private final CountryPM countryProxy = new CountryPM();

    //#CountriesToContinentBinding
    private final ContinentPM proxyContinent = new ContinentPM(); //todo


    //#Flagge
    private static String FLAGS_IMGAGE_PATH = "https://dieterholz.github.io/StaticResources/flags_iso/";
    private static String IMAGE_FORMAT_PNG = ".png";

    //for createNewCountry: in order to add always at the end of the list with correct CountryPM.idProperty
    private int idOfNextNewCountry;

    /************************** Constructors **************************/

    public RootPM(CountryService service){
        this.service = service;

        allCountries.addAll(service.findAll().stream()
                .map(dto -> new CountryPM(dto))         //Mapping von DTOs zu PMs
                .collect(Collectors.toList()));         //Alle PMs zur ObservableList

        idOfNextNewCountry = allCountries.size() + 1;

        for(String contName : this.getAllContinentNames()){
            allContinents.add(new ContinentPM(contName,
                    getContinentArea(contName),
                    getContinentPopulation(contName),
                    getContinentAmountOfCountries(contName)));
        }


        setupBindings();
        addValueChangeListener();

        //populate proxyContinent for the 1st time:
        ContinentPM temp = allContinents.stream()
                        .filter(continentPM -> continentPM.getContinentName().equals(countryProxy.getContinent()))
                        .findFirst()
                        .get();
        proxyContinent.setContinentName(temp.getContinentName());
        proxyContinent.setPopulation(temp.getPopulation());
        proxyContinent.setArea(temp.getArea());
        proxyContinent.setAmountOfCountries(temp.getAmountOfCountries());



            //  .collect(Collectors.toCollection(FXCollections::observableArrayList)));
            /* What is the difference?
             * .collect(Collectors.toList()) --> List<T> => keine Listeners anwendbar :(
             *                                      [vs.]
             * .collect(Collectors.toCollection(FXCollections::observableArrayList)) --> ObservableList<T> => Listeners? Ja, geht.
             *
             * Was bedeutet <class>::<Function>?
             * FXCollections::observableArrayList == FXCollections.observableArrayList()
             */

    }

    /**
     * sets up the initial values for CountryPM countryProxy
     * @LessonLearned: required because value change listeners are passive. They only act when a value has been changed.
     */
    private void setupBindings(){
        //#stableSelection (Advanced Selection Handling)
        CountryPM currentCountry = getCurrentCountry();
        if (currentCountry != null) {
            countryProxy.idProperty().bindBidirectional(currentCountry.idProperty());
            countryProxy.nameProperty().bindBidirectional(currentCountry.nameProperty());
            countryProxy.name_longProperty().bindBidirectional(currentCountry.name_longProperty());
            countryProxy.capitalProperty().bindBidirectional(currentCountry.capitalProperty());
            countryProxy.populationProperty().bindBidirectional(currentCountry.populationProperty());
            countryProxy.areaProperty().bindBidirectional(currentCountry.areaProperty());
            countryProxy.population_km2Property().bindBidirectional(currentCountry.population_km2Property());
            countryProxy.flagProperty().bindBidirectional(currentCountry.flagProperty());
            countryProxy.iso_3Property().bindBidirectional(currentCountry.iso_3Property());
            countryProxy.iso_2Property().bindBidirectional(currentCountry.iso_2Property());
            countryProxy.tldProperty().bindBidirectional(currentCountry.tldProperty());
            countryProxy.name_englishProperty().bindBidirectional(currentCountry.name_englishProperty());
            countryProxy.name_localProperty().bindBidirectional(currentCountry.name_localProperty());
            countryProxy.continentProperty().bindBidirectional(currentCountry.continentProperty());
        }
    }

    private void addValueChangeListener(){
        //#stableSelection (Advanced Selection Handling)
        AddSelectionChangeListener();

        // TableView continentInfo / ContinentPM
        AddValueChangeListenerForContinentInfo();

        //Änderungen von "Einwohner" und "Fläche" berechnet "Einwohner pro km2" neu
        AddPopulationPerKM2Updater();
    }

    private void AddPopulationPerKM2Updater(){
        countryProxy.populationProperty().addListener((observable, oldValue, newValue) -> {             // In case the population value is changed by the user
            int newPopulation = newValue.intValue();
            if(newPopulation < 0){
                newPopulation = Math.abs(newPopulation);
                countryProxy.setPopulation(newPopulation);
            }
            countryProxy.setPopulation_km2(newPopulation / countryProxy.getArea());      // then recalculate the populationPerSquaremeter
        });

        countryProxy.areaProperty().addListener((observable, oldValue, newValue) -> {                   // In case the area value is changed by the user
            double newArea = newValue.doubleValue();
            if (newArea == 0){  //keine Division durch 0.
                newArea = 1;
                countryProxy.setArea(newArea);
            }
            if(newArea < 0){
                newArea = Math.abs(newArea);
                countryProxy.setArea(newArea);
            }
            countryProxy.setPopulation_km2(countryProxy.getPopulation() / newArea);      // then recalculate the populationPerSquaremeter
        });
    }

    private void AddValueChangeListenerForContinentInfo(){
        // 1) selected countryProxy changes:
        AddContinentSelectionListener();

        // 2) wenn countryProxy-Werte geändert, dann Werte in proxyContinent anpassen
        //todo: continentInfo updaten
        countryProxy.populationProperty().addListener((observable, oldValue, newValue) -> {
            proxyContinent.setPopulation(getContinentPopulation(countryProxy.getContinent()));
            System.out.println(getContinentPopulation(countryProxy.getContinent()));
        });

        //Todo: react upon value change in selectedCountryPM which is stored in CountryPM proxyCountry

        //allContinents reacts upon allCountries.remove() and allCountries.add()
        allCountries.addListener((ListChangeListener) change -> {
            allContinents.clear();
            for(String contName : this.getAllContinentNames()){
                allContinents.add(new ContinentPM(contName,
                        getContinentArea(contName),
                        getContinentPopulation(contName),
                        getContinentAmountOfCountries(contName)));
            }
        });
    }

    //todo: continentInfo updaten
    private void AddContinentSelectionListener(){
        countryProxy.continentProperty().addListener((observable, oldValue, newValue) -> {
            ContinentPM newproxyContinent = allContinents.stream()
                    .filter(continentPM -> continentPM.getContinentName().equals(newValue))
                    .findFirst()
                    .get();
            //proxyContinent = newproxyContinent:
            proxyContinent.setContinentName(newproxyContinent.getContinentName());
            proxyContinent.setPopulation(newproxyContinent.getPopulation());
            proxyContinent.setArea(newproxyContinent.getArea());
            proxyContinent.setAmountOfCountries(newproxyContinent.getAmountOfCountries());
        });
    }


    /**
     * #stableSelection (Advanced Selection Handling)
     *
     * updates CountryPM proxyCountry.
     */
    private void AddSelectionChangeListener(){
        selectedCountryId.addListener((observable, oldValue, newValue) -> {
            CountryPM oldSelection = this.getCountry(oldValue.intValue()); //unbind from old CountryPM
            CountryPM newSelection = this.getCountry(newValue.intValue()); //rebind to newly selected CountryPM

            //unbind from old CountryPM
            if (oldSelection != null) {
                countryProxy.idProperty().unbindBidirectional(oldSelection.idProperty());
                countryProxy.nameProperty().unbindBidirectional(oldSelection.nameProperty());
                countryProxy.name_longProperty().unbindBidirectional(oldSelection.name_longProperty());
                countryProxy.capitalProperty().unbindBidirectional(oldSelection.capitalProperty());
                countryProxy.populationProperty().unbindBidirectional(oldSelection.populationProperty());
                countryProxy.areaProperty().unbindBidirectional(oldSelection.areaProperty());
                countryProxy.population_km2Property().unbindBidirectional(oldSelection.population_km2Property());
                countryProxy.flagProperty().unbindBidirectional(oldSelection.flagProperty());
                countryProxy.iso_3Property().unbindBidirectional(oldSelection.iso_3Property());
                countryProxy.iso_2Property().unbindBidirectional(oldSelection.iso_2Property());
                countryProxy.tldProperty().unbindBidirectional(oldSelection.tldProperty());
                countryProxy.name_englishProperty().unbindBidirectional(oldSelection.name_englishProperty());
                countryProxy.name_localProperty().unbindBidirectional(oldSelection.name_localProperty());
                countryProxy.continentProperty().unbindBidirectional(oldSelection.continentProperty());
            }

            //rebind to newly selected CountryPM
            if (newSelection != null) {
                countryProxy.idProperty().bindBidirectional(newSelection.idProperty());
                countryProxy.nameProperty().bindBidirectional(newSelection.nameProperty());
                countryProxy.name_longProperty().bindBidirectional(newSelection.name_longProperty());
                countryProxy.capitalProperty().bindBidirectional(newSelection.capitalProperty());
                countryProxy.populationProperty().bindBidirectional(newSelection.populationProperty());
                countryProxy.areaProperty().bindBidirectional(newSelection.areaProperty());
                countryProxy.population_km2Property().bindBidirectional(newSelection.population_km2Property());
                countryProxy.flagProperty().bindBidirectional(newSelection.flagProperty());
                countryProxy.iso_3Property().bindBidirectional(newSelection.iso_3Property());
                countryProxy.iso_2Property().bindBidirectional(newSelection.iso_2Property());
                countryProxy.tldProperty().bindBidirectional(newSelection.tldProperty());
                countryProxy.name_englishProperty().bindBidirectional(newSelection.name_englishProperty());
                countryProxy.name_localProperty().bindBidirectional(newSelection.name_localProperty());
                countryProxy.continentProperty().bindBidirectional(newSelection.continentProperty());
            }

        });
    }

    /************************** Add New Country **************************/
    public void createNewCountry(){
        //Wenn Index schon vergeben...
        if(getCountry(idOfNextNewCountry) != null) {
            //..finde mir einen, der noch nicht vergeben ist.
            idOfNextNewCountry = allCountries.stream().mapToInt(CountryPM::getId).max().getAsInt();
            idOfNextNewCountry++;
        }

        allCountries.add(new CountryPM(idOfNextNewCountry));
        setSelectedCountryId(idOfNextNewCountry);
        idOfNextNewCountry++;
    }

    /************************** Remove Selected Country **************************/
    public void removeSelectedCountry(){
        CountryPM selected = getCountry(getSelectedCountryId());
        allCountries.remove(selected);

    }

    /************************** Save Countries To File **************************/
    public void saveToFile(){
        List<CountryDTO> allCountryDTOs = allCountries.stream()
                .map(countryPM -> new CountryDTO(countryPM))
                .collect(Collectors.toList());

        for (CountryDTO dto : allCountryDTOs){
            System.out.println(dto.getNAME());
        }
        service.save(allCountryDTOs);
    }


    /************************** #SelectionHandling **************************/

    //#SelectionHandling
    public CountryPM getCurrentCountry(){
        return getCountry(getSelectedCountryId());

        //Hinweis 3: non-duplicate code? => return getCountry(getSelectedCountryId())
    }

    //#SelectionHandling
    public CountryPM getCountry(int searchId){
        return allCountries.stream()
                .filter(countryPM -> countryPM.getId() == searchId)
                .findAny()
                .orElse(null);

        //Hinweis 1: allCountries.get(int index) könnte auch klappen, WENN die IDs == der Reihenfolge der Elemente wäre.
        //Hinweis 2: man könnte auch mit foreach(), aber gäbe mehr code und erweiterungen sind aufwändiger.
    }

    //#stableSelection (Advamced Selection Handling)
    public CountryPM getCountryProxy(){
        return countryProxy;
    }

    /************************** TableView continentInfo / ContinentPM **************************/

    public ObservableList<ContinentPM> getAllContinents(){
        return allContinents;
    }

    /**
     * returns each continent name only once as an element of ObservableList<String>.
     * @return the continent names
     */
    public ObservableList<String> getAllContinentNames(){
        return allCountries.stream()
                .map(countryPM -> countryPM.getContinent())
                .distinct()
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

    /**
     * returns the countries of a continent.
     *
     * [collecting to ObservableList]
     * Source: http://stackoverflow.com/questions/33849538/collectors-lambda-return-observable-list
     * @param continent
     * @return
     */
    public ObservableList<CountryPM> getCountriesOf(String continent){
        return allCountries.stream()
                            .filter(countryPM -> countryPM.getContinent().equals(continent))        // Filtern und vergleichen
                            .collect(Collectors.toCollection(FXCollections::observableArrayList));


        /* How it works
         * ---------------
         * [FXCollections::observableArrayList]:
         * observableArrayList() Creates a new empty observable list that is backed by an arraylist.
         *
         * [Collectors.toCollection(Supplier<C> collectionFactory)]:
         * toCollection() Returns a Collector that accumulates the input elements into a new Collection, in encounter order.
         *
         * [Stream's .collect(Collector<? super T,A,R> collector)]:
         * .collect() uses a Collector in order to gather and return a collection.
         */
    }

    public Double getContinentArea(String continent){
        return getCountriesOf(continent).stream()
                                        .mapToDouble(countryPM -> countryPM.getArea())
                                        .sum();
    }

    public Integer getContinentPopulation(String continent){
        return getCountriesOf(continent).stream()
                                        .mapToInt(countryPM -> countryPM.getPopulation())
                                        .sum();
    }

    public Integer getContinentAmountOfCountries(String continent){
        return (int)getCountriesOf(continent).stream()
                .count();
    }

    /********************* #Flagge ***********************/

    /**
     *
     * @param flagSize Form: "64/"
     * @return
     */
    public String getCurrentFlagURL(String flagSize){
        if("".equals(flagSize) || flagSize == null){
            flagSize = "24/";                       //fallback size
        }

        String iso_2 = this.getCountryProxy().getIso_2();
        if ("".equals(iso_2) || iso_2 == null){
            return "http://icons.iconarchive.com/icons/gosquared/flag/64/United-Nations-icon.png";  // bei CreateNewCountry()
        } else {
            iso_2 = iso_2.toLowerCase(); //from "CH" to "ch"
        }

        String url = FLAGS_IMGAGE_PATH +  // "https://dieterholz.github.io/StaticResources/flags_iso/";
                flagSize +          // "128/" oder "64/" usw.
                iso_2 +             // "ch" oder "de" usw.
                IMAGE_FORMAT_PNG;   // ".png"
        return url;
    }

    /********************* getters and setters ***********************/

    public String getApplicationTitle() {
        return applicationTitle.get();
    }
    public StringProperty applicationTitleProperty() {
        return applicationTitle;
    }
    public void setApplicationTitle(String applicationTitle) {
        this.applicationTitle.set(applicationTitle);
    }

    public ObservableList<CountryPM> getAllCountries() {
        return allCountries;
    }

    public int getSelectedCountryId() {
        return selectedCountryId.get();
    }

    public IntegerProperty selectedCountryIdProperty() {
        return selectedCountryId;
    }

    public void setSelectedCountryId(int selectedCountryId) {
        this.selectedCountryId.set(selectedCountryId);
    }

    public ContinentPM getProxyContinent() {
        return proxyContinent;
    }
}
