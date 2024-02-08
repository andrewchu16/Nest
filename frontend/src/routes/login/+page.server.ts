import { auth } from '$lib/admin.server';
import { redirect } from '@sveltejs/kit';
import type { PageServerLoad } from './$types';

export const load: PageServerLoad = async ({ cookies }) => {
	const token = cookies.get('token')?.toString();
	let uid: string | undefined;
	let isLoggedIn = false;

	if (token) {
		await auth
			.verifyIdToken(token)
			.then((decodedToken) => {
				isLoggedIn = true;
				uid = decodedToken.uid;
			})
			.catch((error) => {
				console.log(error);
			});
	}

	if (isLoggedIn) {
		console.log("redirecting to dashboard");
		redirect(302, "/");
	}

	return { uid };
};
