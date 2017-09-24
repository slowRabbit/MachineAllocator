package com.insectiousapp.machineallocator;

/**
 * Created by cyris on 1/4/17.
 */

public class Asset {

    int assetId;
    String assetMake;
    int yearOfMaking;
    int allocatedTo;
    String allocatedTill;

    public Asset( int assetId, String assetMake, int yearOfMaking, int allocatedTo, String allocatedTill)
    {
        this.assetId=assetId;
        this.assetMake=assetMake;
        this.yearOfMaking=yearOfMaking;
        this.allocatedTo=allocatedTo;
        this.allocatedTill=allocatedTill;
    }

    public int getYearOfMaking() {
        return yearOfMaking;
    }

    public String getAllocatedTill() {
        return allocatedTill;
    }

    public int getAllocatedTo() {
        return allocatedTo;
    }

    public int getAssetId() {
        return assetId;
    }

    public String getAssetMake() {
        return assetMake;
    }

    public void setAllocatedTill(String allocatedTill) {
        this.allocatedTill = allocatedTill;
    }

    public void setAllocatedTo(int allocatedTo) {
        this.allocatedTo = allocatedTo;
    }

    public void setAssetId(int assetId) {
        this.assetId = assetId;
    }

    public void setAssetMake(String assetMake) {
        this.assetMake = assetMake;
    }

    public void setYearOfMaking(int yearOfMaking) {
        this.yearOfMaking = yearOfMaking;
    }
}
