package org.example.projectpegasi.DomainModels;

import org.example.projectpegasi.Persistence.DataAccessObject;

import java.util.ArrayList;
import java.util.List;

public class MatchMaker
{
    /**
     * Matching Algorithm for matching profiles based on our score system
     * @param profiles List of profiles
     * @return a List of profiles sorted by score
     */
    public List<ProfilePair> matchProfiles(List<Profile> profiles) {
        List<ProfilePair> matchedPairs = new ArrayList<>();
        DataAccessObject dao = new DataAccessObject();

        for (Profile profile1 : profiles) {
            for (Profile profile2 : profiles) {
                if (profile1.getProfileID() != profile2.getProfileID() && !alreadyMatched(profile1, profile2, matchedPairs)) {
                    double similarityScore = calculateSimilarity(profile1, profile2);
                    System.out.println("Similarity Score between " + profile1.getJobFunction() + " and " + profile2.getJobFunction() + " : " + similarityScore);

                    if (similarityScore >= 0.7) {
                        ProfilePair pair = new ProfilePair(profile1, profile2, similarityScore);
                        matchedPairs.add(pair);
                        dao.saveMatch(profile1.getProfileID(), profile2.getProfileID());
                    }
                }
            }
        }
        return sortByScore(matchedPairs);
    }


    /**
     * Method for calculating the similarity scores between two profiles
     * (which is a made up score system intended for expansion later on, like adding distance into the match)
     * @param profile1 First profile to match
     * @param profile2 Second profile to match
     * @return the similarity score between the profiles
     */
    public double calculateSimilarity(Profile profile1, Profile profile2)
    {
        System.out.println("Calculating similarity between:");
        System.out.println("Profile 1 - Job Function: " + profile1.getJobFunction() + ", Pay Pref: " + profile1.getPayPref());
        System.out.println("Profile 2 - Job Function: " + profile2.getJobFunction() + ", Pay Pref: " + profile2.getPayPref());

        double jobFunctionScore = compareJobFunctions(profile1.getJobFunction(), profile2.getJobFunction());
        double payPrefScore = comparePayPreferences(profile1.getPayPref(), profile2.getPayPref());

        double similarityScore = 0.7 * jobFunctionScore + 0.3 * payPrefScore;
        System.out.println("Similarity score: " + similarityScore);
        return similarityScore;
    }

    private double comparePayPreferences(int payPref1, int payPref2)
    {
        return payPref1 == payPref2 ? 1.0 : 0.0;
    }

    private double compareJobFunctions(String jobFunction1, String jobFunction2)
    {
        if(jobFunction1 == null || jobFunction2 == null)
        {
            return 0.0;
        }
        return jobFunction1.equals(jobFunction2) ? 1 : 0;
    }

    private boolean alreadyMatched(Profile profile1, Profile profile2, List<ProfilePair> matchedPairs) {
        for (ProfilePair pair : matchedPairs) {
            if ((pair.getProfile1().getProfileID() == profile1.getProfileID() &&
                    pair.getProfile2().getProfileID() == profile2.getProfileID()) ||
                    (pair.getProfile1().getProfileID() == profile2.getProfileID() &&
                            pair.getProfile2().getProfileID() == profile1.getProfileID())) {
                return true;
            }
        }
        return false;
    }
    private List<ProfilePair> sortByScore(List<ProfilePair> matchedPairs)
    {
        matchedPairs.sort((pair1, pair2) ->
                Double.compare(pair2.getSimilarityScore(), pair1.getSimilarityScore()));
        return matchedPairs;
    }

}
