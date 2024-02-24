<script lang="ts">
	import { onMount } from 'svelte';
	import '../app.css';
	import { auth, db } from '$lib/firebase';
	import { page } from '$app/stores';
	import { goto } from '$app/navigation';
	import { userStore } from '../stores/userStore';
	import { doc, getDoc } from 'firebase/firestore';
	import type UserData from '$lib/models/userData.model';
	import type { UserID } from '$lib/models/userData.model';

	const nonProtectedRoutes = ['/landing', '/login', '/signup'];

	let isMounted = false;

	onMount(() => {
		console.log('layout page mounting');

		// Get cached session data
		const sessionData = window.sessionStorage.getItem('userData');
		if (sessionData) {
			console.log('loading session: ', sessionData);
			$userStore = JSON.parse(sessionData);
		}
		isMounted = true;

		const unsubscribe = auth.onAuthStateChanged(async (user) => {
			const pagePath = $page.url.pathname;

			// Redirect to login if trying to access unauthorized pages.
			if (!user && !nonProtectedRoutes.includes(pagePath)) {
				goto('/login');
				return;
			}

			// Allow if not logged in but on an unprotected page (don't need to load user data)
			if (!user) {
				return;
			}

			// Logged in, get user credentials
			const docRef = doc(db, 'users', user.uid);
			const docSnap = await getDoc(docRef);

			if (!docSnap.exists()) {
				console.log('Error: should have an account');
				return;
			}

			const docData = docSnap.data();
			console.log(docData);
			const userData: UserData = {
				username: docData['username']?.toString(),
				firstName: docData['firstName']?.toString(),
				lastName: docData['lastName']?.toString(),
				email: docData['email']?.toString(),
				profileImagePath: docData['profielImagePath']?.toString(),
				bio: docData['bio']?.toString(),
				uid: user.uid as UserID,
				position: []
			};

			// set the user store
			userStore.set({
				user: user,
				data: userData
			});
		});

		return () => {
			// Unsubscribe from auth state change.
			unsubscribe();

			// Cache session data.
			if ($userStore) {
				window.sessionStorage.setItem('userData', JSON.stringify($userStore));
			}
		};
	});

	$: if (isMounted && $userStore) {
		window.sessionStorage.setItem("userData", JSON.stringify($userStore));
	}

</script>

<slot />
