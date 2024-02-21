import type { orgID } from "./organization.model";
import type { userID } from "./userData.model";

export interface Project {
    name: string;
    owner: userID;
    orgs: orgID[];
    thumbnailPath: string | undefined;
}

export type ProjectID = string;