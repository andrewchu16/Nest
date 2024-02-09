import { derived, type Readable } from 'svelte/store';
import { browser, dev } from '$app/environment';
import type { Auth } from 'firebase/auth';
import type { FirebaseApp } from 'firebase/app';
import { app } from './app';

const createAuth = () => {
	let auth: Auth;

	const { subscribe } = derived<Readable<FirebaseApp>, Auth>(app, ($app, set) => {
		async function init() {
			if ($app && !auth) {
				const { getAuth, connectAuthEmulator } = await import('firebase/auth');
				auth = getAuth($app);
				if (dev) {
					connectAuthEmulator(auth, 'http://localhost:9099');
				}
			}
			set(auth);
		}
		
		if (browser) init();
	});

	return {
		subscribe,
	};
};

export const auth = createAuth();