package org.perennial.utils.api;

import static org.perennial.utils.PUtils.userdata;

public final class Userdata {

    public static int getStatBlocksBroken(String playerName) {
        if (userdata.getProperty(".stats.blocks-broken") == null) {
            return 0;
        }
        return userdata.getDataInt(playerName + ".stats.blocks-broken");
    }

    public static int getStatBlocksPlaced(String playerName) {
        if (userdata.getProperty(".stats.blocks-placed") == null) {
            return 0;
        }
        return userdata.getDataInt(playerName + ".stats.blocks-placed");
    }

    public static long getStatPlaytime(String playerName) {
        if (userdata.getProperty(".stats.time-played") == null) {
            return 0;
        }
        return userdata.secondsToHours(playerName + ".stats.time-played");
    }
}
