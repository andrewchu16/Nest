<script lang="ts">
	import type Organization from '$lib/models/organization.model';

	export let organization: Organization;

	// Function to get initials from organization name
	function getInitials(name: string) {
		return name
			.split(' ')
			.map((word) => word.charAt(0))
			.join('')
			.toUpperCase();
	}
</script>

<div class="bg-white shadow-lg rounded-lg overflow-hidden">
	<div class="px-4 py-2">
		<div class="flex items-center">
			<div
				class="flex-shrink-0 h-12 w-12 bg-gray-200 text-gray-600 flex items-center justify-center rounded-full"
			>
				{#if organization.thumbnailUrl}
					<img
						src={organization.thumbnailUrl}
						alt={organization.name}
						class="h-10 w-10 rounded-full object-cover"
					/>
				{:else}
					<span class="text-lg font-bold">{getInitials(organization.name)}</span>
				{/if}
			</div>
			<div class="ml-4">
				<h2 class="text-xl font-bold text-gray-800">{organization.name}</h2>
				{#if organization.description}
					<p class="text-gray-600">{organization.description}</p>
				{/if}
				{#if organization.websiteUrl}
					<p>
						<a href={organization.websiteUrl} class="text-blue-500 hover:underline"
							>{organization.websiteUrl}</a
						>
					</p>
				{/if}
				<p class="text-gray-600">Industry: {organization.industry}</p>
			</div>
		</div>
	</div>
</div>
