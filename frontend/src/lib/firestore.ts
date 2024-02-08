import { Firestore } from 'firebase/firestore';
import { app } from './app';
import { type Readable, derived } from 'svelte/store';
import type { FirebaseApp } from 'firebase/app';
import { browser, dev } from '$app/environment';

export const firestore = derived<Readable<FirebaseApp>, Firestore>(app, ($app, set) => {
	async function init() {
		const { getFirestore, connectFirestoreEmulator } = await import('firebase/firestore');
		const firestore = getFirestore($app);
		if (dev) {
			connectFirestoreEmulator(firestore, 'http://localhost', 8080);
		}
		set(firestore);
	}

    if (browser) init();
});
