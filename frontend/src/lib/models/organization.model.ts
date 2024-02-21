import type { userID } from "./userData.model";

export default interface Organization {
    name: string;
    owners: userID[];
    members: userID[];
    thumbnailPath: string | undefined;
    description: string | undefined;
    websiteUrl: string | undefined;
    resources: Resource[];
    city: string;
    country: string;
    contact: userID;
}

export type orgID = string;

export interface Resource {
    name: string;
    description: string | undefined;
    thumbanilPath: string | undefined;
}