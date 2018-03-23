package com.example.lewho.projectappmobile;

/**
 * Created by p1601248 on 23/03/2018.
 */

public class Station {
    private final int id;
    private final String name;
    private final String address;
    private final Position position;
    private final boolean banking;
    private final boolean bonus;
    private final String status;
    private final String contractName;
    private final int bikeStands;
    private final int availableBikeStands;
    private final int availableBikes;

    public Station(int id, String name, String address, Position position, boolean banking, boolean bonus, String status, String contractName, int bikeStands, int availableBikeStands, int availableBikes) {

        this.id = id;
        this.name = name;
        this.address = address;
        this.position = position;
        this.banking = banking;
        this.bonus = bonus;
        this.status = status;
        this.contractName = contractName;
        this.bikeStands = bikeStands;
        this.availableBikeStands = availableBikeStands;
        this.availableBikes = availableBikes;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Position getPosition() {
        return position;
    }

    public boolean isBanking() {
        return banking;
    }

    public boolean isBonus() {
        return bonus;
    }

    public String getStatus() {
        return status;
    }

    public String getContractName() {
        return contractName;
    }

    public int getBikeStands() {
        return bikeStands;
    }

    public int getAvailableBikeStands() {
        return availableBikeStands;
    }

    public int getAvailableBikes() {
        return availableBikes;
    }
}
