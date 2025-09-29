package net.arkamc.simpleStaffCore.profile;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ProfileManager {

    private final Set<Profile> profiles;

    public ProfileManager() {
        this.profiles = new HashSet<>();
    }

    public Profile getProfileByUuid(UUID uuid) {
        return profiles.stream().filter(profile -> profile.getUuid().equals(uuid)).findFirst().orElse(null);
    }

    public void addProfile(Profile profile) {
        profiles.add(profile);
    }

    public void removeProfile(Profile profile) {
        profiles.remove(profile);
    }

    public Set<Profile> getProfiles() {
        return profiles;
    }
}
