package ch.fhnw.oop2.countryfx.presentationmodel;

import ch.fhnw.oop2.countryfx.service.CountryService;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.stream.Collectors;

public class RootPM {
    private final StringProperty applicationTitle = new SimpleStringProperty("CountryFX");

    private final ObservableList<CountryPM> allCountries = FXCollections.observableArrayList(); // Alle PMs in einer Collection/Liste
    private final CountryService service;

    //for TableView<ContinentPM
    private final ObservableList<ContinentPM> allContinents = FXCollections.observableArrayList();

    //#SelectionHandling
    private final IntegerProperty selectedCountryId = new SimpleIntegerProperty(1); //Statusinformation: Welches Country angezeigt wird.

    /************************** Constructors **************************/

    public RootPM(CountryService service){
        this.service = service;

        allCountries.addAll(service.findAll().stream()
                .map(dto -> new CountryPM(dto))         //Mapping von DTOs zu PMs
                .collect(Collectors.toList()));         //Alle PMs zur ObservableList

        for(String contName : this.getAllContinentNames()){
            allContinents.add(new ContinentPM(contName,
                    getContinentArea(contName),
                    getContinentPopulation(contName),
                    getContinentAmountOfCountries(contName)));
        }
    }

    /************************** Add New Country **************************/
    public void createNewCountry(){
        int index = allCountries.size() + 1;
        allCountries.add(new CountryPM(index));
        setSelectedCountryId(index);
    }

    /************************** Remove Selected Country **************************/
    public void removeSelectedCountry(){
        // .csv - Index:    starts at 1
        // ListIndex:       starts at 0
        // List.size():     1 to n elements.

        if (getSelectedCountryId() == 1) {
            removeFirstCountry();
        } else if (getSelectedCountryId() == allCountries.size()){
            removeLastCountry();
        } else {
            removeCountryInBetween();
        }

        for(CountryPM c : allCountries){
            System.out.println(c.getName() + "\t- oldId:\t" +  c.getId());
        }

        /* 2,3,4
        *  2,4
        * */
    }

    private void removeLastCountry(){
        System.out.println( getSelectedCountryId());
        int deleteIndex = getSelectedCountryId() - 1; //.csv index -> ObservableList - index (d.h. 1 -> 0)
        System.out.println( allCountries.get(deleteIndex).getName());

        allCountries.remove(deleteIndex);
        setSelectedCountryId(deleteIndex); //last element's index

        for(int i = deleteIndex; i < allCountries.size(); i++){
            allCountries.get(i).setId(i + 1); // ListIndex -> .csv - Index
        }
    }

    private void removeCountryInBetween(){
        removeFirstCountry();
    }

    private void removeFirstCountry(){
        System.out.println( getSelectedCountryId());
        int deleteIndex = getSelectedCountryId() - 1; //.csv index -> ObservableList - index (d.h. 1 -> 0)
        System.out.println( allCountries.get(deleteIndex).getName());

        allCountries.remove(deleteIndex);
        setSelectedCountryId(deleteIndex + 1);

        for(int i = deleteIndex; i < allCountries.size(); i++){
            allCountries.get(i).setId(i + 1); // ListIndex -> .csv - Index
        }
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
                .orElseThrow(IllegalArgumentException::new);

        //Hinweis 1: allCountries.get(int index) könnte auch klappen, WENN die IDs == der Reihenfolge der Elemente wäre.
        //Hinweis 2: man könnte auch mit foreach(), aber gäbe mehr code und erweiterungen sind aufwändiger.
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


    private void addValueChangeListener(){
        allCountries.addListener((ListChangeListener) change -> {
            allContinents.clear();
            for(String contName : this.getAllContinentNames()){
                allContinents.add(new ContinentPM(contName,
                        getContinentArea(contName),
                        getContinentPopulation(contName),
                        getContinentAmountOfCountries(contName)));
            }
        });

//        allCountries.addListener(new ListChangeListener<CountryPM>() {
//            @Override
//            public void onChanged(Change<? extends CountryPM> c) {
        //          allContinents.clear();
//                for(String contName : getAllContinentNames()){
//                    allContinents.add(new ContinentPM(contName,
//                            getContinentArea(contName),
//                            getContinentPopulation(contName),
//                            getContinentAmountOfCountries(contName)));
//                }
//            }
//        });
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
}
