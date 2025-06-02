package org.example.projectpegasi.DomainModels;

import org.example.projectpegasi.Persistence.DataAccessObject;

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
        List<Profile> profiles = dao.getAllProfiles();
        List<ProfilePair> matchedPairs = matchMaker.matchProfiles(profiles);
        return matchedPairs;
    }
}
