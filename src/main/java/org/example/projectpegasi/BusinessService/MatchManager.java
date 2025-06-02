package org.example.projectpegasi.BusinessService;

import org.example.projectpegasi.DomainModels.MatchMaker;
import org.example.projectpegasi.DomainModels.Profile;
import org.example.projectpegasi.DomainModels.ProfilePair;
import org.example.projectpegasi.Persistence.DataAccessObject;

import java.util.ArrayList;
import java.util.List;

public class MatchManager
{
    private DataAccessObject dao;
    private MatchMaker matchMaker;

    public MatchManager()
    {
        this.dao = new DataAccessObject();
        this.matchMaker = new MatchMaker();
    }

    public List<ProfilePair> findAllMatches()
    {
        System.out.println("Find All Matches");
        List<Profile> profiles = dao.getAllProfiles();
        System.out.println("Found " + profiles.size() + " profiles");
        List<ProfilePair> matchedPairs = matchMaker.matchProfiles(profiles);
        return matchedPairs;
    }
}
