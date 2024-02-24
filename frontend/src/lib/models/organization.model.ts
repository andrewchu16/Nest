import type { UserID } from "./userData.model";

export default interface Organization {
    name: string;
    owners: UserID[];
    members: UserID[];
    thumbnailPath: string | undefined;
    description: string | undefined;
    websiteUrl: string | undefined;
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
    description: string | undefined;
    thumbanilPath: string | undefined;
}