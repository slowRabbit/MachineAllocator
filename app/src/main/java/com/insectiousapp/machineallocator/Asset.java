package com.insectiousapp.machineallocator;

/**
 * Created by cyris on 1/4/17.
 */

public class Asset {

    String assetId;
    String assetMake;
    int yearOfMaking;
    String allocatedTo;
    String allocatedTill;

    public Asset( String assetId, String assetMake, int yearOfMaking, String allocatedTo, String allocatedTill)
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

    public String getAllocatedTo() {
        return allocatedTo;
    }

    public String getAssetId() {
        return assetId;
    }

    public String getAssetMake() {
        return assetMake;
    }

    public void setAllocatedTill(String allocatedTill) {
        this.allocatedTill = allocatedTill;
    }

    public void setAllocatedTo(String allocatedTo) {
        this.allocatedTo = allocatedTo;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public void setAssetMake(String assetMake) {
        this.assetMake = assetMake;
    }

    public void setYearOfMaking(int yearOfMaking) {
        this.yearOfMaking = yearOfMaking;
    }
}
