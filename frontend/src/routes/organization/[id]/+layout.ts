import type { OrgID } from '$lib/models/organization.model';
import type Organization from '$lib/models/organization.model';
import { doc, getDoc } from 'firebase/firestore';
import type { LayoutLoad } from './$types';
import { db } from '$lib/firebase';
import type { UserID } from '$lib/models/userData.model';

export const load: LayoutLoad = async ({ params }) => {
	const orgId = params.id as OrgID;
	let organizationExists = false;

	const orgDocRef = doc(db, `/organizations/${orgId}`);
	const orgDocSnap = await getDoc(orgDocRef);

	const organizationData: Organization = {
		name: '',
		owners: [],
		members: [],
		thumbnailUrl: null,
		description: null,
		websiteUrl: null,
		contactEmail: '',
		industry: '',
		sponsorRequirements: [''],
		type: 'Business'
	};

	if (orgDocSnap.exists()) {
		const orgDocData = orgDocSnap.data();
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
		organizationExists = true;
	}

	return {
		organization: organizationData,
		orgId: orgId,
		exists: organizationExists
	};
};
