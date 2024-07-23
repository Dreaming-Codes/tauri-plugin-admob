import {MobileAd} from "./common";

export class InterstitialAd extends MobileAd {
    static cls = 'InterstitialAd'

    isLoaded() {
        return super.isLoaded()
    }

    async load() {
        return super.load()
    }

    async show() {
        return super.show()
    }
}

export class RewardedInterstitialAd extends MobileAd {
    static cls = 'RewardedInterstitialAd'

    isLoaded() {
        return super.isLoaded()
    }

    async load() {
        return super.load()
    }

    async show() {
        return super.show()
    }
}
