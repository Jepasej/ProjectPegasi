package org.example.projectpegasi.DomainModels;

/**
 * Class modelling a company
 */
public class Company
{
    private String name, address;

    public Company(String name)
    {
        this.name = name;
    }
    public Company(String name, String address)
    {
        this.name = name;
        this.address = address;
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
}
