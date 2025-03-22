export declare function isPrivacyOptionsRequired(): Promise<boolean>;
export declare function showPrivacyOptionsForm(): Promise<void>;
export declare class MobileAd<T extends MobileAdOptions = MobileAdOptions> {
    #private;
    private static allAdds;
    private static idCounter;
    readonly id: number;
    protected readonly opts: T;
    constructor(opts: T);
    private static nextId;
    get adUnitId(): string;
    protected isLoaded(): Promise<unknown>;
    protected load(): Promise<void>;
    protected show(): Promise<void>;
    protected hide(): Promise<void>;
    protected init(): Promise<void>;
}
export interface MobileAdOptions {
    adUnitId: string;
}
