import { MobileAd, MobileAdOptions } from "./common";
export declare class BannerAd extends MobileAd<BannerAdOptions> {
    #private;
    static cls: string;
    constructor(opts: BannerAdOptions);
    isLoaded(): Promise<unknown>;
    load(): Promise<void>;
    show(): Promise<void>;
    hide(): Promise<void>;
}
export type BannerPosition = "top" | "bottom";
export interface BannerAdOptions extends MobileAdOptions {
    position?: BannerPosition;
}
