// receive a uid through a post request then set the cookie token.

import type { RequestHandler } from "./$types";

export const POST: RequestHandler = ({ request}) => {
    const formData = request.formData();
}