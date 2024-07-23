import {MobileAd, MobileAdOptions} from "./common";

export class BannerAd extends MobileAd<BannerAdOptions> {
    static cls = 'BannerAd';
    #loaded = false;

    constructor(opts: BannerAdOptions) {
        super({
            position: 'bottom',
            ...opts,
        })
    }

    isLoaded() {
        return super.isLoaded();
    }

    async load() {
        await super.load();
        this.#loaded = true;
    }

    async show() {
        if (!this.#loaded) await this.load();
        await super.show();
    }

    hide() {
        return super.hide();
    }
}

export type BannerPosition = 'top' | 'bottom';

export interface BannerAdOptions extends MobileAdOptions {
    position?: BannerPosition;
}
