<script lang="ts">
	import { applyAction, enhance } from '$app/forms';
	import { goto } from '$app/navigation';
	import { userManager } from '$lib/userManager';
	import type { PageServerData } from './$types';
	let buttonHeight: number;

	let errorMessage: string;

	export let data: PageServerData;

	if (data.token) {
		userManager.loginWithToken(data.token).then((creds) => {
			console.log("redirect to dashboard");
			goto("/");
		}).catch((e) => {
			console.log(e);
		});
	}
</script>

<svelte:head>
	<title>Login</title>
</svelte:head>

<div class="w-screen h-screen flex items-center justify-center bg-light-900">
	<form
		class="flex flex-col gap-6"
		method="POST"
		use:enhance={async ({ formData }) => {
			const email = formData.get('email')?.toString();
			const password = formData.get('password')?.toString();

			console.log(`submitting form email=${email} pwd=${password}`);
			try {
				if (email && password) {
					let cred = await userManager.login(email, password);
					const uid = cred.user.uid;
					formData.set("uid", uid);
				}
			} catch (e) {
				console.log('e');
			}

			return async ({ result }) => {
				if (result.type === 'redirect') {
					goto(result.location);
				} else {
					await applyAction(result);
				}
			}
		}}
	>
		<h1 class="font-display text-5xl font-bold text-dark drop-shadow-lg">Login</h1>
		<div class="flex flex-col gap-4 mb-3">
			<input placeholder="Email" class="text-field w-[400px]" required type="email" name="email" />
			<input
				placeholder="Password"
				class="text-field w-[400px]"
				required
				type="password"
				name="password"
			/>
		</div>
		<div class="flex gap-6">
			<button
				class="login-btn bg-accent1 text-light-900 drop-shadow-sm hover:drop-shadow-lg hover:text-accent2-800 leading-none"
				bind:clientHeight={buttonHeight}
				type="submit">Sign in</button
			>
			<a
				href="/signup"
				style="height: {buttonHeight}px;"
				class="login-btn hover:text-accent2-400 border-dashed border-accent2 hover:border-accent2-400 border-2 box-border transition-colors text-accent2 flex items-center justify-center duration-200"
				>Sign up</a
			>
		</div>
		{#if errorMessage}
			<div class="bg-red-300 border-red-600 border-2 rounded-lg">
				Error: {errorMessage}
			</div>
		{/if}
	</form>
</div>
