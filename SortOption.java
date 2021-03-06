public enum SortOption {
    Brightness_Ascending("Brightness (Ascending)"),
    Brightness_Descending("Brightness (Descending)"),
    Strangeness_Ascending("Strangeness (Ascending)"),
    Strangeness_Descending("Strangeness (Descending)"),
    Interval_Oddness_Ascending("Interval Oddness (Ascending)"),
    Interval_Oddness_Descending("Interval Oddness (Descending)");

    private final String string;

    // constructor to set the string
    SortOption(String name){string = name;}

    @Override
    public String toString()
    {
        return string;
    }
}
