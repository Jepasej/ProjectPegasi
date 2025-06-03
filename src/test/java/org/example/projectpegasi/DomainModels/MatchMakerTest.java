package org.example.projectpegasi.DomainModels;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MatchMakerTest
{

    /**
     * Method to test if algorithm for matching works after every change made
     */
    @Test
    public void testMatchProfiles()
    {
        // Arrange
        Profile profile1 = new Profile("Developer", 1);
        Profile profile2 = new Profile("Developer", 1);
        Profile profile3 = new Profile("Designer", 3);

        List<Profile> profiles = new ArrayList<>();
        profiles.add(profile1);
        profiles.add(profile2);
        profiles.add(profile3);

        MatchMaker matchMaker = new MatchMaker();

        // Act
        List<ProfilePair> matchProfiles = matchMaker.matchProfiles(profiles);

        // Assert
        assertEquals(1, matchProfiles.size());
        assertEquals(1.0, matchProfiles.get(0).getSimilarityScore());
    }

    /**
     * Method to test if the profiles matched got the right value
     */
    @Test
    public void testCalculateSimilarity()
    {
        // Arrange
        Profile profile1 = new Profile("Developer", 1);
        Profile profile2 = new Profile("Developer", 1);
        Profile profile3 = new Profile("Designer", 1);
        Profile profile4 = new Profile("Designer", 3);
        MatchMaker matchMaker = new MatchMaker();

        // Act
        double similarityScore1 = matchMaker.calculateSimilarity(profile1, profile2);
        double similarityScore2 = matchMaker.calculateSimilarity(profile1, profile3);
        double similarityScore3 = matchMaker.calculateSimilarity(profile3, profile4);


        // Assert
        assertEquals(1.0, similarityScore1); // Same Job function and pay preference
        assertEquals(0.3, similarityScore2); // Different Job function and same pay preference
        assertEquals(0.7, similarityScore3); // Same Job function and different pay preference
    }
}