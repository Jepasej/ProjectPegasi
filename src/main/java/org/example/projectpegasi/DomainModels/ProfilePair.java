package org.example.projectpegasi.DomainModels;

public class ProfilePair
{
    private Profile profile1;
    private Profile profile2;
    private double similarityScore;

    public ProfilePair(Profile profile1, Profile profile2, double similarityScore)
    {
        this.profile1 = profile1;
        this.profile2 = profile2;
        this.similarityScore = similarityScore;
    }

    public Profile getProfile1()
    {
        return profile1;
    }

    public Profile getProfile2()
    {
        return profile2;
    }

    public double getSimilarityScore()
    {
        return similarityScore;
    }
}
