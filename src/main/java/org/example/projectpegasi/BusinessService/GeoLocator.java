package org.example.projectpegasi.BusinessService;

import org.example.projectpegasi.DomainModels.Profile;

/**
 * Class in charge of calculation distance between two addresses
 */
public class GeoLocator
{
    /**
     * Method calculating a distance between two addresses.
     * @param addressA starting point of the calculation.
     * @param addressB end point of the calculation.
     * @return distance between two addresses.
     */
    public double getDistance(String addressA, String addressB)
    {
        return 5.00;
    }
}
