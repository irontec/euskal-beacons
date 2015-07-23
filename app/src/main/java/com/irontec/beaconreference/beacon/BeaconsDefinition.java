package com.irontec.beaconreference.beacon;

import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.Region;

/**
 * Created by Matt Tyler on 4/18/14.
 */
public class BeaconsDefinition {

    private final static String BLUECAT_ID = "61687109-905f-4436-91f8-e602f514c96d";

    //Blue
    public final static String BLUE_ID1 = "4876E267-668D-4C34-91A7-32A2AA8E9AA1";
    public final static String BLUE_ID2 = "33";
    public final static String BLUE_ID3 = "9380";

    //Blue
    public final static String BLACK_ID1 = "8EA84828-A7C6-4CF2-BD01-10222E2D0C7A";
    public final static String BLACK_ID2 = "1";
    public final static String BLACK_ID3 = "14260";

    //Blue
    public final static String WHITE_ID1 = "1BD0BDBB-25C2-4C10-8251-651B6FAE908E";
    public final static String WHITE_ID2 = "7";
    public final static String WHITE_ID3 = "14249";

    public final static Region BLUECAT_BLUE =
            new Region("com.irontec.betour", Identifier.parse(BLUE_ID1), Identifier.parse(BLUE_ID2), Identifier.parse(BLUE_ID3));

    public final static Region BLUECAT_BLACK =
            new Region("com.irontec.betour", Identifier.parse(BLACK_ID1), Identifier.parse(BLACK_ID2), Identifier.parse(BLACK_ID3));

    public final static Region BLUECAT_WHITE =
            new Region("com.irontec.betour", Identifier.parse(WHITE_ID1), Identifier.parse(WHITE_ID2), Identifier.parse(WHITE_ID3));

    public final static Region ALL_REGIONS =
            new Region("com.irontec.betour", null, null, null);

}