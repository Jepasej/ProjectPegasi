package org.example.projectpegasi.DomainModels;

import org.example.projectpegasi.Persistence.DataAccessObject;

import java.util.List;

/**
 * Class modelling managing matches
 */
public class MatchManager
{
    private DataAccessObject dao;
    private MatchMaker matchMaker;

    /**
     * Default Constructor
     */
    public MatchManager()
    {
        this.dao = new DataAccessObject();
        this.matchMaker = new MatchMaker();
    }

    /**
     * Makes a list of profiles, and gets all profiles in the database
     * @return List sorted through the matching algorithm
     */
    public List<ProfilePair> findAllMatches()
    {
        List<Profile> profiles = dao.getAllProfiles();
        List<ProfilePair> matchedPairs = matchMaker.matchProfiles(profiles);
        return matchedPairs;
    }
}
