package org.example.projectpegasi.BusinessService;

import org.example.projectpegasi.DomainModels.MatchMaker;
import org.example.projectpegasi.DomainModels.Profile;
import org.example.projectpegasi.DomainModels.ProfilePair;
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
        return matchMaker.matchProfiles(profiles);
    }
}
