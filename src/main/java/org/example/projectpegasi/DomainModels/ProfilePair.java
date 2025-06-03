package org.example.projectpegasi.DomainModels;

/**
 * Represents a pair of profiles along with their calculated similarity score.
 * Used to compare two profiles in the matching process.
 */
public class ProfilePair
{
    private Profile profile1;
    private Profile profile2;
    private double similarityScore;

    /**
     * Constructs a ProfilePair with two profiles and their similarity score.
     *
     * @param profile1         the first profile
     * @param profile2         the second profile
     * @param similarityScore  the calculated similarity score between the profiles
     */
    public ProfilePair(Profile profile1, Profile profile2, double similarityScore)
    {
        this.profile1 = profile1;
        this.profile2 = profile2;
        this.similarityScore = similarityScore;
    }

    //region GettersSetters
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

    //endregion
}
