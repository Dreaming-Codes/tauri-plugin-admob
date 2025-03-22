import { MobileAd, MobileAdOptions } from "./common";
export declare class RewardedAd extends MobileAd<RewardedAdOptions> {
    static cls: string;
    isLoaded(): Promise<unknown>;
    load(): Promise<void>;
    show(): Promise<void>;
}
export declare class RewardedInterstitialAd extends MobileAd<RewardedAdOptions> {
    static cls: string;
    isLoaded(): Promise<unknown>;
    load(): Promise<void>;
    show(): Promise<void>;
}
export interface RewardedAdOptions extends MobileAdOptions {
    serverSideVerification?: {
        userId?: string;
        customData?: string;
    };
}
