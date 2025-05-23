package org.example.projectpegasi.BusinessService;

/**
 * This class keeps track of the currently logged-in user and profile.
 * Values are stored as static so they can be accessed anywhere in the system.
 */
public class LoginCredentialsSession {
    private static final int NO_ONE_LOGGED_IN = -1;

    // Stores the userID of the logged-in user (from tblUser)
    private static int userID = NO_ONE_LOGGED_IN;

    // Stores the profileID linked to the logged-in user (from tblProfile)
    private static int profileID = NO_ONE_LOGGED_IN;

    /**
     * Sets the userID from the database after login.
     * @param id the userID to store
     */
    public static void setUserID(int id) {
        userID = id;
    }

    /**
     * Gets the userID of the current login session.
     * @return the stored userID
     */
    public static int getUserID() {
        return userID;
    }

    /**
     * Sets the profileID linked to the user who is logged in.
     * @param id the profileID to store
     */
    public static void setProfileID(int id) {
        profileID = id;
    }

    /**
     * Gets the profileID for the current login session.
     *  Used for all profile-related operations (e.g., matches, requests).
     * @return the stored profileID
     */
    public static int getProfileID() {
        return profileID;
    }

    /**
     * Clears the session data.
     * Can be called on logout or system reset session.
     */
    public static void clear() {
        userID = NO_ONE_LOGGED_IN;
        profileID = NO_ONE_LOGGED_IN;
    }
}
