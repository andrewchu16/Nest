import type { UserID } from "./userData.model";

export default interface Organization {
    name: string;
    owners: UserID[];
    members: UserID[];
    thumbnailPath: string | null;
    description: string | null;
    websiteUrl: string | null;
    resources: Resource[];
    city: string;
    country: string;
    contact: UserID;
    industry: string;
    sponsorRequirements: string[];
}

export type OrgID = string;

export interface Resource {
    name: string;
    description: string | null;
    thumbanilPath: string | null;
}