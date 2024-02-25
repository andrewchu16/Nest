import type { UserID } from "./userData.model";

export default interface Organization {
    name: string;
    owners: UserID[];
    members: UserID[];
    thumbnailUrl: string | null;
    description: string | null;
    websiteUrl: string | null;
    contactEmail: string;
    industry: string;
    sponsorRequirements: string[];
    type: OrgType;
}

export type OrgID = string;
export type OrgType = "Business" | "School" | "Charity" | null;

export interface Resource {
    name: string;
    description: string | null;
}