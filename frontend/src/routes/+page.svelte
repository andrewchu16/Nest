<!-- dashboard -->
<script lang="ts">
	import { goto } from "$app/navigation";
	import { userManager } from "$lib/userManager";
	import { redirect } from "@sveltejs/kit";
	import type { PageServerData } from "./$types";

    export let data: PageServerData;

    if (data.token) {
        userManager.loginWithToken(data.token).then((creds) => {
			console.log("logged in on dashboard " + creds.user.uid);
		}).catch((e) => {
            console.log("redirecting dashboard to login");
			redirect(302, "/login");
		});
    }
</script>

dashboard!