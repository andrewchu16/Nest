import { auth } from '$lib/admin.server';
import { redirect } from '@sveltejs/kit';
import type { PageServerLoad } from './$types';
import { get } from 'svelte/store';

export const load: PageServerLoad = async ({ cookies }) => {
	const token = cookies.get('token')?.toString();

	if (!token) {
		return redirect(302, "/login");
	}

	return { token: token };
};
