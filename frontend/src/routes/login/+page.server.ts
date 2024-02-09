import { auth } from '$lib/admin.server';
import { redirect, type Actions } from '@sveltejs/kit';
import type { PageServerLoad } from './$types';
import { get } from 'svelte/store';
import type { Auth } from 'firebase-admin/auth';

export const load: PageServerLoad = async ({ cookies }) => {
	const token = cookies.get('token')?.toString();

	return { token: token };
};

export const actions: Actions = {
	default: async ({ cookies, request }) => {
		const formData = await request.formData();
		const uid = formData.get('uid')?.toString();
		
		if (uid) {
			console.log(`user credentials: ${uid}`);
			const token = await get(auth).createCustomToken(uid);
			const expiryDate = new Date();
			expiryDate.setTime(expiryDate.getTime() + 1000 * 60 * 60);
			cookies.set('token', token, { expires: expiryDate, path: '/' });
			return redirect(302, '/');
		} 

		redirect(302, "/login");
	}
};
