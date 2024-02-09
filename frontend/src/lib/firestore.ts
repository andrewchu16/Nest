import { Firestore } from 'firebase/firestore';
import { app } from './app';
import { type Readable, derived } from 'svelte/store';
import type { FirebaseApp } from 'firebase/app';
import { browser, dev } from '$app/environment';

const createFirestore = () => {
	let firestore: Firestore;

	const { subscribe } = derived<Readable<FirebaseApp>, Firestore>(app, ($app, set) => {
		async function init() {
			if ($app && !firestore) {
				const { getFirestore, connectFirestoreEmulator } = await import('firebase/firestore');
				firestore = getFirestore($app);
				if (dev) {
					connectFirestoreEmulator(firestore, 'localhost', 8080);
				}
			}
			set(firestore);
		}

		if (browser) init();
	});

	return { subscribe };
};

export const firestore = createFirestore();
