import type { FirebaseApp } from 'firebase/app';
import { type FirebaseStorage } from 'firebase/storage';
import { derived, type Readable } from 'svelte/store';
import { app } from './app';
import { browser, dev } from '$app/environment';

const createStorage = () => {
	let storage: FirebaseStorage;

	const { subscribe } = derived<Readable<FirebaseApp>, FirebaseStorage>(app, ($app, set) => {
		async function init() {
			const { getStorage, connectStorageEmulator } = await import('firebase/storage');
			storage = getStorage($app);
			if (dev) {
				connectStorageEmulator(storage, 'http://localhost', 9199);
			}
			set(storage);
		}

		if (browser) init();
	});

	return { subscribe };
};

export const storage = createStorage();
