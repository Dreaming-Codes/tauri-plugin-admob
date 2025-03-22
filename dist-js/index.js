import { invoke } from '@tauri-apps/api/core';

/******************************************************************************
Copyright (c) Microsoft Corporation.

Permission to use, copy, modify, and/or distribute this software for any
purpose with or without fee is hereby granted.

THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH
REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF MERCHANTABILITY
AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT,
INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM
LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR
OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR
PERFORMANCE OF THIS SOFTWARE.
***************************************************************************** */
/* global Reflect, Promise, SuppressedError, Symbol, Iterator */


function __classPrivateFieldGet(receiver, state, kind, f) {
    if (kind === "a" && !f) throw new TypeError("Private accessor was defined without a getter");
    if (typeof state === "function" ? receiver !== state || !f : !state.has(receiver)) throw new TypeError("Cannot read private member from an object whose class did not declare it");
    return kind === "m" ? f : kind === "a" ? f.call(receiver) : f ? f.value : state.get(receiver);
}

function __classPrivateFieldSet(receiver, state, value, kind, f) {
    if (kind === "m") throw new TypeError("Private method is not writable");
    if (kind === "a" && !f) throw new TypeError("Private accessor was defined without a setter");
    if (typeof state === "function" ? receiver !== state || !f : !state.has(receiver)) throw new TypeError("Cannot write private member to an object whose class did not declare it");
    return (kind === "a" ? f.call(receiver, value) : f ? f.value = value : state.set(receiver, value)), value;
}

typeof SuppressedError === "function" ? SuppressedError : function (error, suppressed, message) {
    var e = new Error(message);
    return e.name = "SuppressedError", e.error = error, e.suppressed = suppressed, e;
};

var _MobileAd_created, _MobileAd_init;
async function isPrivacyOptionsRequired() {
    return (await invoke("plugin:admob|isPrivacyOptionsRequired")).isPrivacyOptionsRequired;
}
async function showPrivacyOptionsForm() {
    await invoke("plugin:admob|showPrivacyOptionsForm");
}
class MobileAd {
    constructor(opts) {
        _MobileAd_created.set(this, false);
        _MobileAd_init.set(this, null);
        this.opts = opts;
        this.id = MobileAd.nextId();
        MobileAd.allAdds[this.id] = this;
    }
    static nextId() {
        return MobileAd.idCounter++;
    }
    get adUnitId() {
        return this.opts.adUnitId;
    }
    async isLoaded() {
        await this.init();
        return await invoke("plugin:admob|adIsLoaded", {
            id: this.id,
        });
    }
    async load() {
        await this.init();
        await invoke("plugin:admob|adLoad", {
            ...this.opts,
            id: this.id,
        });
    }
    async show() {
        await this.init();
        await invoke("plugin:admob|adShow", {
            id: this.id,
        });
    }
    async hide() {
        await this.init();
        await invoke("plugin:admob|adHide", {
            id: this.id,
        });
    }
    async init() {
        if (__classPrivateFieldGet(this, _MobileAd_created, "f"))
            return;
        if (__classPrivateFieldGet(this, _MobileAd_init, "f") === null) {
            const cls = this.constructor.cls ??
                this.constructor.name;
            await invoke("plugin:admob|adCreate", {
                ...this.opts,
                id: this.id,
                cls,
            });
        }
        await __classPrivateFieldGet(this, _MobileAd_init, "f");
        __classPrivateFieldSet(this, _MobileAd_created, true, "f");
    }
}
_MobileAd_created = new WeakMap(), _MobileAd_init = new WeakMap();
MobileAd.allAdds = {};
MobileAd.idCounter = 0;

var _BannerAd_loaded;
class BannerAd extends MobileAd {
    constructor(opts) {
        super({
            position: "bottom",
            ...opts,
        });
        _BannerAd_loaded.set(this, false);
    }
    isLoaded() {
        return super.isLoaded();
    }
    async load() {
        await super.load();
        __classPrivateFieldSet(this, _BannerAd_loaded, true, "f");
    }
    async show() {
        if (!__classPrivateFieldGet(this, _BannerAd_loaded, "f"))
            await this.load();
        await super.show();
    }
    hide() {
        return super.hide();
    }
}
_BannerAd_loaded = new WeakMap();
BannerAd.cls = "BannerAd";

class InterstitialAd extends MobileAd {
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
InterstitialAd.cls = "InterstitialAd";

class RewardedAd extends MobileAd {
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
RewardedAd.cls = "RewardedAd";
class RewardedInterstitialAd extends MobileAd {
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
RewardedInterstitialAd.cls = "RewardedInterstitialAd";

export { BannerAd, InterstitialAd, MobileAd, RewardedAd, RewardedInterstitialAd, isPrivacyOptionsRequired, showPrivacyOptionsForm };
