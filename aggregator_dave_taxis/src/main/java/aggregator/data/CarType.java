package aggregator.data;

public enum CarType
{
    STANDARD("standard", 4),
    EXECUTIVE("executive", 4),
    LUXURY("luxury", 4),
    PEOPLE_CARRIER("people carrier", 6),
    LUXURY_PEOPLE_CARRIER("luxury people carrier", 6),
    MINIBUS("minibus", 16);

    public final String name;
    public final int capacity;

    CarType(final String name, final int capacity)
    {
        this.name = name;
        this.capacity = capacity;
    }
}
