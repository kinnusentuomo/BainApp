package tuomomees.bain_soundboard_app;

import android.content.Context;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

/**
 * Luokan on luonut tuomo päivämäärällä 11.11.2017.
 */

class AdHandler {
    private InterstitialAd mInterstitialAd;

    AdHandler(Context context)
    {
        mInterstitialAd = new InterstitialAd(context);

        // set the ad unit ID
        mInterstitialAd.setAdUnitId(context.getString(R.string.FullScreenAd));
    }

    void showAd()
    {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("4143E9C56383B280C13BEAB61D27819D")
                .build();

        // Load ads into Interstitial Ads
        mInterstitialAd.loadAd(adRequest);

        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
            }
        });
    }
}
