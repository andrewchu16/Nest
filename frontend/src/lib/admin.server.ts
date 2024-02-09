import { Auth } from 'firebase-admin/auth';
import admin from 'firebase-admin';
import { readable } from 'svelte/store';

import serviceAccountKey from '$lib/server/serviceAccountKey.json';

function createAuth() {
	let auth: Auth;

	const { subscribe } = readable<Auth>(undefined, (set) => {
		async function init() {
			if (!auth) {
				const { initializeApp } = await import('firebase-admin/app');
				const { getAuth } = await import('firebase-admin/auth');
				const app = initializeApp({
					credential: admin.credential.cert(serviceAccountKey)
				});
				auth = getAuth(app);
			}
			set(auth);
		}

		init();
	});

	return { subscribe };
}

export const auth = createAuth();
