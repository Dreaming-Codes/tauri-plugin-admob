import { readFileSync } from "node:fs";
import { join } from "node:path";
import { cwd } from "node:process";
import typescript from "@rollup/plugin-typescript";
import { defineConfig } from "rolldown";

const pkg = JSON.parse(readFileSync(join(cwd(), "package.json"), "utf8"));

export default defineConfig({
	input: "guest-js/index.ts",
	output: [
		{
			file: pkg.exports.import,
			format: "esm",
		},
		{
			file: pkg.exports.require,
			format: "cjs",
		},
	],
	plugins: [
		typescript({
			declaration: true,
			declarationDir: `./${pkg.exports.import.split("/")[0]}`,
		}),
	],
	external: [
		/^@tauri-apps\/api/,
		...Object.keys(pkg.dependencies || {}),
		...Object.keys(pkg.peerDependencies || {}),
	],
});
