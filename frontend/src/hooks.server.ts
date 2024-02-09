import { auth as authStore } from '$lib/admin.server';
import type { Handle } from '@sveltejs/kit';
import type { Auth } from 'firebase-admin/auth';

export const handle: Handle = async ({ event, resolve }) => {
	let auth: Auth;
	authStore.subscribe((value) => auth = value);
	const { cookies, locals } = event;

	locals.user = null; // default if session cookie fails

	const session = cookies.get('session');

	if (session) {
		try {
			const user = await auth.verifySessionCookie(session);
			locals.user = user;
		} catch (err) {
			console.error('error verifying session cookie', session, err);
		}
	}

	return resolve(event);
};
