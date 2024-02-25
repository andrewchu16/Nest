import type { OrgID } from './organization.model';
import type { UserID } from './userData.model';

export interface Project {
	name: string;
	owner: UserID;
	ownerOrg: OrgID | null;
	orgs: OrgID[];
	thumbnailPath: string | null;
}

export type ProjectID = string;
