<script lang="ts">
	import { onMount } from 'svelte';
	import Body from '$lib/components/Body.svelte';
	import type Organization from '$lib/models/organization.model';
	import OrganizationCard from '$lib/components/OrganizationCard.svelte';
	import { auth, db } from '$lib/firebase';
	import { userStore } from '$lib/stores/userStore';
	import { goto } from '$app/navigation';
	import { collection, getDocs, query, where } from 'firebase/firestore';
	import type { UserID } from '$lib/models/userData.model';

	let organizations: Organization[] = [];

	userStore.subscribe(async (curr) => {
		const orgsRef = collection(db, 'organizations');
		const uid = curr.user?.uid ? curr.user?.uid : 'no';
		const orgsQuery = query(orgsRef, where('owners', 'array-contains', uid));

		const orgsQuerySnap = await getDocs(orgsQuery);
		orgsQuerySnap.forEach((doc) => {
			const orgDocData = doc.data();
			console.log(orgDocData);

			const organizationData: Organization = {
				name: '',
				owners: [],
				members: [],
				thumbnailUrl: null,
				description: null,
				websiteUrl: null,
				contactEmail: '',
				industry: '',
				sponsorRequirements: [],
				type: null
			};

			organizationData.name = orgDocData['name'];
			organizationData.owners = orgDocData['owners'] as UserID[];
			organizationData.members = orgDocData['members'] as UserID[];
			organizationData.thumbnailUrl = orgDocData['thumbnailUrl'];
			organizationData.description = orgDocData['description'];
			organizationData.websiteUrl = orgDocData['websiteUrl'];
			organizationData.contactEmail = orgDocData['contactEmail'];
			organizationData.industry = orgDocData['industry'];
			organizationData.sponsorRequirements = orgDocData['sponsorRequirements'];
			organizationData.type = orgDocData['type'];

			if (!organizations.includes(organizationData)) {

				organizations = [...organizations, organizationData];
			}
		});
	});

	const generateId = () => {
		let result = '';
		const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_';
		for (let i = 0; i < 20; i++) {
			result += characters.charAt(Math.floor(Math.random() * characters.length));
		}
		return result;
	};

	const redirectCreateNewOrganization = () => {
		const orgId = generateId();

		goto(`/organization/${orgId}/edit`);
	};
</script>

<svelte:head>
	<title>My Organizations</title>
</svelte:head>
<Body>
	<svelte:fragment slot="page-content">
		<div class="w-full h-full overflow-y-scroll flex flex-col items-center px-8 py-12">
			<h1 class="font-display font-bold text-3xl text-dark text-center">My Organizations</h1>
			<div class="w-5/6 max-w-[1200px] h-full flex flex-col gap-4">
				{#each organizations as organization}
					<OrganizationCard {organization} />
				{/each}
			</div>
			<button
				on:click={redirectCreateNewOrganization}
				class="btn bg-accent1 text-light-900 drop-shadow-sm hover:drop-shadow-lg hover:text-accent2-800"
				>Create New Organization</button
			>
		</div>
	</svelte:fragment>
</Body>
