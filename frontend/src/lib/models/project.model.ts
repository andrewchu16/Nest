import type { OrgID } from './organization.model';
import type { UserID } from './userData.model';

export interface Project {
	name: string;
	owner: UserID;
	orgs: OrgID[];
	thumbnailPath: string | undefined;
}

export type ProjectID = string;
