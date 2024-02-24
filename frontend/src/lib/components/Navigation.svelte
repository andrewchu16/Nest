<script lang="ts">
	import { page } from '$app/stores';
    import nestLogo from "$lib/icons/nest-logo-upscaled.png";

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
</script>

<nav class="bg-accent3 w-full h-full flex flex-col items-center justify-between py-12">
    <img src={nestLogo} class="max-w-40 w-[60%] cursor-pointer" alt="Nest Logo" draggable="false"/>
	<div class="flex flex-col gap-3 text-light">
		{#each navLinks as link}
			<a href={link.href} class:font-bold={link.href === $page.url.pathname} class="">{link.name}</a>
		{/each}
	</div>
    <button class="text-light">Sign Out</button>
</nav>
