package superhelo.icrutils.api;

public enum CoreEnum {

    ALCHEMICAL_BRASS(0, 0),
    TERRA_STEEL(0, 0),
    OSMIUM(0, 0),
    PLATINUM(0, 0),
    DRAGON(0, 0);

    int absorptionRate;
    int absorptionQuantity;

    CoreEnum(int absorptionRate, int absorptionQuantity) {
        this.absorptionRate = absorptionRate;
        this.absorptionQuantity = absorptionQuantity;
    }

    public int getAbsorptionRate() {
        return absorptionRate;
    }

    public int getAbsorptionQuantity() {
        return absorptionQuantity;
    }
}
