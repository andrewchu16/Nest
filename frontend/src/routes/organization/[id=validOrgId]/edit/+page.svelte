<script lang="ts">
	import Body from '$lib/components/Body.svelte';
	import { userStore } from '$lib/stores/userStore';
	import type { OrgType } from '$lib/models/organization.model';
	import { doc, setDoc } from 'firebase/firestore';
	import { auth, db } from '$lib/firebase';
	import { goto } from '$app/navigation';
	import type { LayoutData } from '../$types';
	import { redirect } from '@sveltejs/kit';
	import { onMount } from 'svelte';

	const orgTypes: OrgType[] = ['Business', 'School', 'Charity'];

	export let data: LayoutData;

	let organizationData = data.organization;

	onMount(() => {
		const unsubscribe = auth.onAuthStateChanged(async (user) => {
			const uid = $userStore.data?.uid;
			// Check to make sure the user is allowed to edit this organization.
			if (!uid || !organizationData.owners.includes(uid)) {
				console.log('not allowed to edit this page.');
				goto('/');
			}
		});

		return unsubscribe;
	});

	const submitForm = async () => {
		if (organizationData.type === 'School') {
			organizationData.industry = 'Education';
		} else if (organizationData.type === 'Charity') {
			organizationData.industry = 'Charity';
		}

		// Make sure the current user is an editor
		const uid = $userStore.data?.uid;
		if (uid && !organizationData.owners.includes(uid)) {
			organizationData.owners = [...organizationData.owners, uid];
		}

		const orgDocRef = doc(db, 'organizations', data.orgId);
		await setDoc(orgDocRef, organizationData);

		goto(`/organizations/${data.orgId}`);
	};
</script>

<svelte:head>
	<title>New Organization</title>
</svelte:head>
<Body>
	<svelte:fragment slot="page-content">
		<div class="w-full h-full flex flex-col items-center px-8 py-12">
			<form
				on:submit|preventDefault={submitForm}
				class="w-1/3 min-w-[420px] max-w-[900px] flex flex-col gap-7 mb-8"
			>
				<h1 class="font-display font-bold text-3xl text-dark text-center">New Organization</h1>
				<div class="w-full flex flex-col gap-2">
					<label for="organization-name" class="font-display text-dark font-bold"
						>Organization Name</label
					>
					<input
						id="organization-name"
						type="text"
						name="organization-name"
						class="text-field flex-grow"
						required
						placeholder="e.g. Walmart"
						bind:value={organizationData.name}
					/>
				</div>
				<div class="w-full flex flex-col gap-2">
					<h2 class="font-display text-dark font-bold">Organization Type</h2>
					{#each orgTypes as orgType, index}
						<div class="flex items-center">
							<input
								id="organization-type-{index}"
								type="radio"
								name="organization-type"
								class="text-dark w-4 h-4"
								required
								value={orgType}
								bind:group={organizationData.type}
							/>
							<label for="organization-type-{index}" class="ml-2 text-dark-600"> {orgType}</label>
						</div>
					{/each}
				</div>
				{#if organizationData.type === 'Business'}
					<div class="w-full flex flex-col gap-2">
						<label for="organization-industry" class="font-display text-dark font-bold"
							>Industry</label
						>
						<input
							type="text"
							name="organization-industry"
							class="text-field flex-grow"
							placeholder="Retail"
							required
							bind:value={organizationData.industry}
						/>
					</div>
				{/if}
				<div class="w-full flex flex-col gap-2">
					<label for="organization-description" class="font-display text-dark font-bold"
						>Description</label
					>
					<textarea
						rows="3"
						name="organization-description"
						class="text-field flex-grow text-md"
						placeholder="Walmart Inc. is an American multinational retail corporation that operates a chain of hypermarkets, discount department stores, and grocery stores in the United States, headquartered in Bentonville, Arkansas."
						required
						bind:value={organizationData.description}
					/>
				</div>
				<div class="w-full flex flex-col gap-2">
					<label for="organization-website" class="font-display text-dark font-bold"
						>Website URL</label
					>
					<input
						type="url"
						name="organization-website"
						class="text-field flex-grow"
						placeholder="www.walmart.ca"
						required
						bind:value={organizationData.websiteUrl}
					/>
				</div>
				<div class="w-full flex flex-col gap-2">
					<label for="organization-contact" class="font-display text-dark font-bold">Contact</label>
					<input
						type="email"
						name="organization-contact"
						class="text-field flex-grow"
						placeholder="manager@walmart.ca"
						required
						bind:value={organizationData.contactEmail}
					/>
				</div>
				<div class="w-full flex flex-col gap-2">
					<label for="organization-thumbnail" class="font-display text-dark font-bold"
						>Thumbnail URL</label
					>
					<input
						type="url"
						name="organization-thumbnail"
						class="text-field flex-grow"
						placeholder="https://www.walmartcanada.ca/content/dam/walmart-canada/images/site-images/Store_front.png"
						required
						bind:value={organizationData.thumbnailUrl}
					/>
				</div>
				{#if organizationData.type === 'Business'}
					<!-- Sponsor Requirements -->
					<div class="w-full flex flex-col">
						<h2 class="font-display text-dark font-bold">Sponsor Requirements</h2>
						<div class="flex flex-col gap-4">
							{#each organizationData.sponsorRequirements as { }, index}
								<div class="flex flex-col gap-2">
									<label for="organization-requirement-{index}">Requirement #{index + 1}</label>
									<input
										id="organization-requirement-{index}"
										type="text"
										name="organization-requirement-{index}"
										class="text-field flex-grow"
										placeholder="Alignment with Brand Values"
										bind:value={organizationData.sponsorRequirements[index]}
									/>
									<button
										class="bg-light-600 border-light-500 border-2 rounded-lg px-7 py-1 text-light-100 drop-shadow-lg hover:text-light-200 hover:bg-light-500"
										type="button"
										on:click={() => {
											organizationData.sponsorRequirements.splice(index, 1);
											organizationData.sponsorRequirements = organizationData.sponsorRequirements;
										}}>Delete Requirement</button
									>
								</div>
							{/each}
						</div>
						<button
							class="hover:text-accent2-400 border-dashed border-accent2 hover:border-accent2-400 border-2 box-border transition-colors text-accent2 flex items-center justify-center duration-200 rounded-xl px-7 py-2 mt-4"
							type="button"
							on:click={() => {
								organizationData.sponsorRequirements = [
									...organizationData.sponsorRequirements,
									''
								];
							}}>Add Requirement</button
						>
					</div>
				{/if}
				<button
					type="submit"
					class="login-btn bg-accent1 text-light-900 drop-shadow-sm hover:drop-shadow-lg hover:text-accent2-800"
				>
					{#if data.exists}
						Edit Organization
					{:else}
						Create Organization
					{/if}
				</button>
			</form>
		</div>
	</svelte:fragment>
</Body>
