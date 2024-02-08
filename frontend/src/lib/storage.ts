import type { FirebaseApp } from "firebase/app";
import { type FirebaseStorage } from "firebase/storage";
import { derived, type Readable } from "svelte/store";
import { app } from "./app";
import { browser, dev } from "$app/environment";

export const storage = derived<Readable<FirebaseApp>, FirebaseStorage>(app, ($app, set) => {
    async function init() {
        const { getStorage, connectStorageEmulator } = await import("firebase/storage");
        const firebaseStorage = getStorage($app);
        if (dev) {
            connectStorageEmulator(firebaseStorage, 'http://localhost', 9199);
        }
        set(firebaseStorage);
    }

    if (browser) init();
})