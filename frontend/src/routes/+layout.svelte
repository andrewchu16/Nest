<script lang="ts">
	import { onMount } from 'svelte';
	import '../app.css';
	import { auth } from '$lib/firebase';
	import { page } from '$app/stores';
	import { goto } from '$app/navigation';

	const nonProtectedRoutes = ["/landing", "login", "signup"];

	onMount(() => {
		console.log("layout page mounting");
		const unsubscribe = auth.onAuthStateChanged(async (user) => {
			const pagePath = $page.url.pathname;

			// redirect to login if trying to access unauthorized pages.
			if (!user && !nonProtectedRoutes.includes(pagePath)) {
				goto("/login");
				return;
			}

			if (user && pagePath === "/") {
				goto("/dashboard");
				return;
			}
		});

		return unsubscribe;
	})
</script>
<slot />
