<script lang="ts">
	import { page } from '$app/stores';
	import nestLogo from '$lib/icons/nest-logo-upscaled.png';
	import { signOut } from 'firebase/auth';
	import { auth } from '$lib/firebase';
	import { goto } from '$app/navigation';

	interface NavLinkInfo {
		href: string;
		name: string;
	}

	const navLinks: NavLinkInfo[] = [
		{
			href: '/',
			name: 'Dashboard'
		},
		{
			href: '/search',
			name: 'Search'
		},
		{
			href: '/my-projects',
			name: 'My Projects'
		},
		{
			href: '/my-organizations',
			name: 'My Organizations'
		},
		{
			href: '/settings',
			name: 'Settings'
		}
	];

	const signOutHandler = async () => {
        console.log("Signing out");
		try {
			await signOut(auth);
            goto("/login");
		} catch (error) {
			console.log('Error when signing out ' + error);
		}
	};
</script>

<nav class="bg-accent3 w-full h-full flex flex-col items-center justify-between py-12">
	<img src={nestLogo} class="max-w-40 w-[60%] cursor-pointer" alt="Nest Logo" draggable="false" />
	<div class="flex flex-col gap-3 text-light">
		{#each navLinks as link}
			<a href={link.href} class:font-bold={link.href === $page.url.pathname} class="hover:text-accent3-700">{link.name}</a
			>
		{/each}
	</div>
	<button class="text-light hover:text-accent3-700" on:click={signOutHandler}>Sign Out</button>
</nav>
