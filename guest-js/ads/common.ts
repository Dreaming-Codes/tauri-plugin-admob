import {invoke} from '@tauri-apps/api/core'

export class MobileAd<T extends MobileAdOptions = MobileAdOptions> {
    private static allAdds: { [s: number]: MobileAd } = {};
    private static idCounter = 1;

    public readonly id: number;

    protected readonly opts: T;

    #created = false;
    #init: Promise<void> | null = null;

    constructor(opts: T) {
        this.opts = opts;

        this.id = MobileAd.nextId();
        MobileAd.allAdds[this.id] = this;
    }

    private static nextId() {
        return MobileAd.idCounter++;
    }

    public get adUnitId() {
        return this.opts.adUnitId;
    }

    protected async isLoaded() {
        await this.init();
        return await invoke('plugin:admob|adIsLoaded', {
            id: this.id
        } as unknown as Record<string, unknown>)
    }

    protected async load() {
        await this.init();
        await invoke('plugin:admob|adLoad', {
            ...this.opts, id: this.id
        } as unknown as Record<string, unknown>)
    }

    protected async show() {
        await this.init();
        await invoke('plugin:admob|adShow', {
            id: this.id
        })
    }

    protected async hide() {
        await this.init();
        await invoke('plugin:admob|adHide', {
            id: this.id
        })
    }

    protected async init() {
        if (this.#created) return;

        if (this.#init === null) {
            const cls = (this.constructor as unknown as { cls?: string }).cls ?? this.constructor.name;

            await invoke('plugin:admob|adCreate', {
                ...this.opts, id: this.id, cls
            } as unknown as Record<string, unknown>);
        }

        await this.#init;
        this.#created = true;
    }
}

export interface MobileAdOptions {
    adUnitId: string;
}
