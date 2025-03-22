import { MobileAd } from "./common";
export declare class InterstitialAd extends MobileAd {
    static cls: string;
    isLoaded(): Promise<unknown>;
    load(): Promise<void>;
    show(): Promise<void>;
}
