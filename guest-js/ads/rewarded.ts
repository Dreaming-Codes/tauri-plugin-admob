import { MobileAd, type MobileAdOptions } from "./common";

export class RewardedAd extends MobileAd<RewardedAdOptions> {
	static cls = "RewardedAd";

	isLoaded() {
		return super.isLoaded();
	}

	async load() {
		return super.load();
	}

	async show() {
		return super.show();
	}
}

export class RewardedInterstitialAd extends MobileAd<RewardedAdOptions> {
	static cls = "RewardedInterstitialAd";

	isLoaded() {
		return super.isLoaded();
	}

	async load() {
		return super.load();
	}

	async show() {
		return super.show();
	}
}

export interface RewardedAdOptions extends MobileAdOptions {
	serverSideVerification?: {
		userId?: string;
		customData?: string;
	};
}
