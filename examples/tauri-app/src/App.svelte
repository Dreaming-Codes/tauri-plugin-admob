<script lang="ts">
import { BannerAd, InterstitialAd } from "tauri-plugin-admob-api";
import Greet from "./lib/Greet.svelte";

let response = "";

function updateResponse(returnValue) {
	response += `[${new Date().toLocaleTimeString()}] ${
		typeof returnValue === "string" ? returnValue : JSON.stringify(returnValue)
	}<br>`;
}

async function _ping() {
	const banner = new BannerAd({
		adUnitId: "ca-app-pub-3940256099942544/9214589741",
		position: "top",
	});
	if (!(await banner.isLoaded())) {
		await banner.load();
	}
	await banner.show();
}
</script>

<main class="container">
    <h1>Welcome to Tauri!</h1>

    <div class="row">
        <a href="https://vitejs.dev" target="_blank">
            <img alt="Vite Logo" class="logo vite" src="/vite.svg"/>
        </a>
        <a href="https://tauri.app" target="_blank">
            <img alt="Tauri Logo" class="logo tauri" src="/tauri.svg"/>
        </a>
        <a href="https://svelte.dev" target="_blank">
            <img alt="Svelte Logo" class="logo svelte" src="/svelte.svg"/>
        </a>
    </div>

    <p>
        Click on the Tauri, Vite, and Svelte logos to learn more.
    </p>

    <div class="row">
        <Greet/>
    </div>

    <div>
        <button on:click="{_ping}">Ping</button>
        <div>{@html response}</div>
    </div>

</main>

<style>
    .logo.vite:hover {
        filter: drop-shadow(0 0 2em #747bff);
    }

    .logo.svelte:hover {
        filter: drop-shadow(0 0 2em #ff3e00);
    }
</style>
