package org.example.projectpegasi.DomainModels;

/**
 * Class modelling a company
 */
public class Company
{
    private String name, address;
    private int ID;

    /**
     * Overloaded Constructor
     * @param name
     */
    public Company(String name)
    {
        this.name = name;
    }

    /**
     * Overloaded Constructor
     * @param name
     * @param address
     */
    public Company(String name, String address)
    {
        this.name = name;
        this.address = address;
    }

    /**
     * Default Constructor
     */
    public Company()
    {

    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public int getID()
    {
        return ID;
    }

    public void setID(int ID)
    {
        this.ID = ID;
    }
}
